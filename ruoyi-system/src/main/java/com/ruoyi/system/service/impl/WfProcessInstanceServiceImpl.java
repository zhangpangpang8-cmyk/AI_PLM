package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.WfProcessInstanceMapper;
import com.ruoyi.system.mapper.WfProcessTaskMapper;
import com.ruoyi.system.mapper.WfProcessDefinitionMapper;
import com.ruoyi.system.mapper.WfProcessNodeMapper;
import com.ruoyi.system.mapper.WfProcessEdgeMapper;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.domain.WfProcessTask;
import com.ruoyi.system.domain.WfProcessDefinition;
import com.ruoyi.system.domain.WfProcessNode;
import com.ruoyi.system.domain.WfProcessEdge;
import com.ruoyi.system.service.IWfProcessInstanceService;

/**
 * 流程实例Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class WfProcessInstanceServiceImpl implements IWfProcessInstanceService
{
    @Autowired
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Autowired
    private WfProcessTaskMapper wfProcessTaskMapper;

    @Autowired
    private WfProcessDefinitionMapper wfProcessDefinitionMapper;

    @Autowired
    private WfProcessNodeMapper wfProcessNodeMapper;

    @Autowired
    private WfProcessEdgeMapper wfProcessEdgeMapper;

    /**
     * 查询流程实例
     *
     * @param id 流程实例主键
     * @return 流程实例
     */
    @Override
    public WfProcessInstance selectWfProcessInstanceById(Long id)
    {
        return wfProcessInstanceMapper.selectWfProcessInstanceById(id);
    }

    /**
     * 查询流程实例列表
     *
     * @param wfProcessInstance 流程实例
     * @return 流程实例
     */
    @Override
    public List<WfProcessInstance> selectWfProcessInstanceList(WfProcessInstance wfProcessInstance)
    {
        return wfProcessInstanceMapper.selectWfProcessInstanceList(wfProcessInstance);
    }

    /**
     * 根据业务ID查询流程实例
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 流程实例
     */
    @Override
    public WfProcessInstance selectWfProcessInstanceByBusinessId(String businessType, Long businessId)
    {
        return wfProcessInstanceMapper.selectWfProcessInstanceByBusinessId(businessType, businessId);
    }

    /**
     * 启动流程实例
     *
     * @param processKey 流程标识
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @param businessNo 业务编号
     * @param title 流程标题
     * @param initiator 发起人
     * @return 流程实例
     */
    @Override
    @Transactional
    public WfProcessInstance startProcessInstance(String processKey, String businessType, Long businessId, String businessNo, String title, String initiator)
    {
        WfProcessDefinition query = new WfProcessDefinition();
        query.setProcessKey(processKey);
        query.setStatus("0");
        List<WfProcessDefinition> definitions = wfProcessDefinitionMapper.selectWfProcessDefinitionList(query);

        if (definitions == null || definitions.isEmpty()) {
            throw new RuntimeException("未找到流程标识为 " + processKey + " 的有效流程定义");
        }

        WfProcessDefinition definition = definitions.get(0);

        WfProcessInstance instance = new WfProcessInstance();
        instance.setProcessDefinitionId(definition.getId());
        instance.setBusinessType(businessType);
        instance.setBusinessId(businessId);
        instance.setBusinessNo(businessNo);
        instance.setTitle(title);
        instance.setInitiator(initiator);
        instance.setCurrentNode("开始");
        instance.setStatus("running");
        instance.setStartTime(new Date());

        wfProcessInstanceMapper.insertWfProcessInstance(instance);

        createFirstTask(instance, definition);

        return instance;
    }

    private void createFirstTask(WfProcessInstance instance, WfProcessDefinition definition)
    {
        WfProcessNode startNode = wfProcessNodeMapper.selectStartNodeByDefinitionId(definition.getId());

        if (startNode == null) {
            throw new RuntimeException("流程定义中未找到开始节点");
        }

        WfProcessEdge edge = wfProcessEdgeMapper.selectEdgeBySourceNodeId(startNode.getId());

        if (edge == null) {
            throw new RuntimeException("流程定义中未配置从开始节点出发的连线");
        }

        WfProcessNode firstTaskNode = wfProcessNodeMapper.selectWfProcessNodeById(edge.getTargetNodeId());

        if (firstTaskNode == null) {
            throw new RuntimeException("流程定义中未找到第一个任务节点");
        }

        WfProcessTask task = new WfProcessTask();
        task.setInstanceId(instance.getId());
        task.setNodeName(firstTaskNode.getNodeName());

        if ("user".equals(firstTaskNode.getAssigneeType())) {
            task.setAssignee(firstTaskNode.getAssigneeValue());
        } else if ("role".equals(firstTaskNode.getAssigneeType()) || "dept".equals(firstTaskNode.getAssigneeType()) || "leader".equals(firstTaskNode.getAssigneeType())) {
            task.setCandidateUsers(firstTaskNode.getAssigneeType() + ":" + firstTaskNode.getAssigneeValue());
            task.setAssignee(firstTaskNode.getAssigneeType() + ":" + firstTaskNode.getAssigneeValue());
        }

        task.setTaskStatus("pending");
        task.setStartTime(new Date());

        wfProcessTaskMapper.insertWfProcessTask(task);

        instance.setCurrentNode(firstTaskNode.getNodeName());
        wfProcessInstanceMapper.updateWfProcessInstance(instance);
    }




    /**
     * 删除流程实例信息
     *
     * @param id 流程实例主键
     * @return 结果
     */
    @Override
    public int deleteWfProcessInstanceById(Long id)
    {
        return wfProcessInstanceMapper.deleteWfProcessInstanceById(id);
    }

}
