package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.WfProcessEdge;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.domain.WfProcessNode;
import com.ruoyi.system.domain.WfProcessTask;
import com.ruoyi.system.mapper.WfProcessEdgeMapper;
import com.ruoyi.system.mapper.WfProcessInstanceMapper;
import com.ruoyi.system.mapper.WfProcessNodeMapper;
import com.ruoyi.system.mapper.WfProcessTaskMapper;
import com.ruoyi.system.service.IWfProcessTaskService;
import com.ruoyi.system.workflow.WorkflowBusinessStatusService;

/**
 * 流程任务业务处理。审批通过时沿流程连线推进，只有到达结束节点才回写业务状态。
 */
@Service
public class WfProcessTaskServiceImpl implements IWfProcessTaskService
{
    private final WfProcessTaskMapper taskMapper;
    private final WfProcessInstanceMapper instanceMapper;
    private final WfProcessNodeMapper nodeMapper;
    private final WfProcessEdgeMapper edgeMapper;
    private final WorkflowBusinessStatusService businessStatusService;

    public WfProcessTaskServiceImpl(WfProcessTaskMapper taskMapper,
                                    WfProcessInstanceMapper instanceMapper,
                                    WfProcessNodeMapper nodeMapper,
                                    WfProcessEdgeMapper edgeMapper,
                                    WorkflowBusinessStatusService businessStatusService)
    {
        this.taskMapper = taskMapper;
        this.instanceMapper = instanceMapper;
        this.nodeMapper = nodeMapper;
        this.edgeMapper = edgeMapper;
        this.businessStatusService = businessStatusService;
    }

    @Override
    public WfProcessTask selectWfProcessTaskById(Long id)
    {
        return taskMapper.selectWfProcessTaskById(id);
    }

    @Override
    public List<WfProcessTask> selectWfProcessTaskList(WfProcessTask task)
    {
        return taskMapper.selectWfProcessTaskList(task);
    }

    @Override
    public List<WfProcessTask> selectPendingTasksByUserId(String userId)
    {
        return taskMapper.selectPendingTasksByUserId(userId);
    }

    @Override
    public List<WfProcessTask> selectCompletedTasksByUserId(String userId)
    {
        return taskMapper.selectCompletedTasksByUserId(userId);
    }

    @Override
    @Transactional
    public int approveTask(Long taskId, boolean approved, String opinion, String approver)
    {
        WfProcessTask task = taskMapper.selectWfProcessTaskById(taskId);
        if (task == null)
        {
            throw new ServiceException("审批任务不存在: " + taskId);
        }
        if (!"pending".equals(task.getTaskStatus()))
        {
            throw new ServiceException("该任务已处理，请勿重复审批");
        }
        boolean canApprove = taskMapper.selectPendingTasksByUserId(approver).stream()
                .anyMatch(pendingTask -> taskId.equals(pendingTask.getId()));
        if (!canApprove)
        {
            throw new ServiceException("当前用户无权审批该任务");
        }

        WfProcessInstance instance = instanceMapper.selectWfProcessInstanceById(task.getInstanceId());
        if (instance == null || !"running".equals(instance.getStatus()))
        {
            throw new ServiceException("流程实例不存在或已结束");
        }

        Date now = new Date();
        task.setTaskStatus(approved ? "approved" : "rejected");
        task.setApprovalOpinion(opinion);
        task.setApprovalTime(now);
        task.setEndTime(now);
        int result = taskMapper.updateWfProcessTask(task);

        if (!approved)
        {
            finishInstance(instance, false, now);
            businessStatusService.updateStatus(instance.getBusinessType(), instance.getBusinessId(), false, opinion, approver);
            return result;
        }

        WfProcessNode currentNode = nodeMapper.selectNodeByDefinitionIdAndName(
                instance.getProcessDefinitionId(), task.getNodeName());
        if (currentNode == null)
        {
            throw new ServiceException("无法定位当前流程节点: " + task.getNodeName());
        }

        WfProcessNode nextNode = findNextActionNode(currentNode);
        if (nextNode == null || "endEvent".equals(nextNode.getNodeType()))
        {
            finishInstance(instance, true, now);
            businessStatusService.updateStatus(instance.getBusinessType(), instance.getBusinessId(), true, opinion, approver);
        }
        else
        {
            createTask(instance, nextNode, now);
            instance.setCurrentNode(nextNode.getNodeName());
            instanceMapper.updateWfProcessInstance(instance);
        }
        return result;
    }

    /**
     * 跳过开始/结束以外的路由节点，直到找到下一个人工任务。
     * 多出边代表条件分支；在条件引擎落地前拒绝猜测路线，避免错误结束审批。
     */
    private WfProcessNode findNextActionNode(WfProcessNode sourceNode)
    {
        Set<Long> visited = new HashSet<>();
        WfProcessNode current = sourceNode;
        while (current != null)
        {
            if (!visited.add(current.getId()))
            {
                throw new ServiceException("流程定义存在循环连线: " + current.getNodeName());
            }
            List<WfProcessEdge> edges = edgeMapper.selectEdgesBySourceNodeId(current.getId());
            if (edges == null || edges.isEmpty())
            {
                return null;
            }
            if (edges.size() > 1)
            {
                throw new ServiceException("节点“" + current.getNodeName() + "”存在多条条件分支，暂不能自动判定");
            }
            current = nodeMapper.selectWfProcessNodeById(edges.get(0).getTargetNodeId());
            if (current == null)
            {
                throw new ServiceException("流程连线指向了不存在的节点");
            }
            if ("userTask".equals(current.getNodeType()) || "endEvent".equals(current.getNodeType()))
            {
                return current;
            }
        }
        return null;
    }

    private void createTask(WfProcessInstance instance, WfProcessNode node, Date now)
    {
        WfProcessTask nextTask = new WfProcessTask();
        nextTask.setInstanceId(instance.getId());
        nextTask.setNodeName(node.getNodeName());
        String assigneeType = node.getAssigneeType();
        String assigneeValue = node.getAssigneeValue();
        if ("user".equals(assigneeType))
        {
            nextTask.setAssignee(assigneeValue);
        }
        else if ("role".equals(assigneeType) || "dept".equals(assigneeType) || "leader".equals(assigneeType))
        {
            String candidate = assigneeType + ":" + assigneeValue;
            nextTask.setCandidateUsers(candidate);
            nextTask.setAssignee(candidate);
        }
        else
        {
            throw new ServiceException("审批节点未配置有效审批人: " + node.getNodeName());
        }
        nextTask.setTaskStatus("pending");
        nextTask.setStartTime(now);
        taskMapper.insertWfProcessTask(nextTask);
    }

    private void finishInstance(WfProcessInstance instance, boolean approved, Date now)
    {
        instance.setCurrentNode(approved ? "已完成" : "已驳回");
        instance.setStatus(approved ? "approved" : "rejected");
        instance.setEndTime(now);
        if (instance.getStartTime() != null)
        {
            instance.setDuration(Math.max(0L, (now.getTime() - instance.getStartTime().getTime()) / 1000));
        }
        instanceMapper.updateWfProcessInstance(instance);
    }
}
