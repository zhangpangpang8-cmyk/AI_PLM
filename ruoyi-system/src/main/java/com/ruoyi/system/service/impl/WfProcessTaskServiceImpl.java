package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.WfProcessTaskMapper;
import com.ruoyi.system.mapper.WfProcessInstanceMapper;
import com.ruoyi.system.domain.WfProcessTask;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.service.IWfProcessTaskService;

/**
 * 流程任务Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class WfProcessTaskServiceImpl implements IWfProcessTaskService
{
    @Autowired
    private WfProcessTaskMapper wfProcessTaskMapper;

    @Autowired
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    /**
     * 查询流程任务
     *
     * @param id 流程任务主键
     * @return 流程任务
     */
    @Override
    public WfProcessTask selectWfProcessTaskById(Long id)
    {
        return wfProcessTaskMapper.selectWfProcessTaskById(id);
    }

    /**
     * 查询流程任务列表
     *
     * @param wfProcessTask 流程任务
     * @return 流程任务
     */
    @Override
    public List<WfProcessTask> selectWfProcessTaskList(WfProcessTask wfProcessTask)
    {
        return wfProcessTaskMapper.selectWfProcessTaskList(wfProcessTask);
    }

    /**
     * 查询用户的待办任务
     *
     * @param userId 用户ID
     * @return 流程任务
     */
    @Override
    public List<WfProcessTask> selectPendingTasksByUserId(String userId)
    {
        return wfProcessTaskMapper.selectPendingTasksByUserId(userId);
    }

    /**
     * 查询用户的已办任务
     *
     * @param userId 用户ID
     * @return 流程任务
     */
    @Override
    public List<WfProcessTask> selectCompletedTasksByUserId(String userId)
    {
        return wfProcessTaskMapper.selectCompletedTasksByUserId(userId);
    }

    /**
     * 审批任务
     *
     * @param taskId 任务ID
     * @param approved 是否通过
     * @param opinion 审批意见
     * @param approver 审批人
     * @return 结果
     */
    @Override
    @Transactional
    public int approveTask(Long taskId, boolean approved, String opinion, String approver)
    {
        // 更新任务状态
        WfProcessTask task = wfProcessTaskMapper.selectWfProcessTaskById(taskId);
        if (task == null) {
            return 0;
        }

        task.setTaskStatus(approved ? "approved" : "rejected");
        task.setApprovalOpinion(opinion);
        task.setApprovalTime(new Date());
        task.setEndTime(new Date());

        int result = wfProcessTaskMapper.updateWfProcessTask(task);

        // 如果审批通过，创建下一个任务或结束流程
        if (approved) {
            WfProcessInstance instance = wfProcessInstanceMapper.selectWfProcessInstanceById(task.getInstanceId());
            if (instance != null) {
                // 这里简化处理，实际应该根据流程定义判断是否有下一个节点
                instance.setCurrentNode("已完成");
                instance.setStatus("approved");
                instance.setEndTime(new Date());
                wfProcessInstanceMapper.updateWfProcessInstance(instance);
            }
        } else {
            // 审批驳回，更新流程状态
            WfProcessInstance instance = wfProcessInstanceMapper.selectWfProcessInstanceById(task.getInstanceId());
            if (instance != null) {
                instance.setStatus("rejected");
                instance.setEndTime(new Date());
                wfProcessInstanceMapper.updateWfProcessInstance(instance);
            }
        }

        return result;
    }
}
