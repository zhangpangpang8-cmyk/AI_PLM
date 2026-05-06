package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.WfProcessInstanceMapper;
import com.ruoyi.system.mapper.WfProcessTaskMapper;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.domain.WfProcessTask;
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
        // 创建流程实例
        WfProcessInstance instance = new WfProcessInstance();
        instance.setProcessDefinitionId(1L); // 默认流程定义ID，实际应该根据processKey查询
        instance.setBusinessType(businessType);
        instance.setBusinessId(businessId);
        instance.setBusinessNo(businessNo);
        instance.setTitle(title);
        instance.setInitiator(initiator);
        instance.setCurrentNode("开始");
        instance.setStatus("running");
        instance.setStartTime(new Date());

        wfProcessInstanceMapper.insertWfProcessInstance(instance);

        // 创建第一个任务（这里简化处理，实际应该根据流程定义创建）
        WfProcessTask task = new WfProcessTask();
        task.setInstanceId(instance.getId());
        task.setNodeName("部门经理审批");
        task.setAssignee("manager"); // 实际应该根据流程配置分配
        task.setTaskStatus("pending");
        task.setStartTime(new Date());

        wfProcessTaskMapper.insertWfProcessTask(task);

        return instance;
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
