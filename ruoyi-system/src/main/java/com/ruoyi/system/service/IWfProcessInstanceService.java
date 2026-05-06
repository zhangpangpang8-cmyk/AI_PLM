package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.WfProcessInstance;

/**
 * 流程实例Service接口
 *
 * @author ruoyi
 */
public interface IWfProcessInstanceService
{
    /**
     * 查询流程实例
     *
     * @param id 流程实例主键
     * @return 流程实例
     */
    public WfProcessInstance selectWfProcessInstanceById(Long id);

    /**
     * 查询流程实例列表
     *
     * @param wfProcessInstance 流程实例
     * @return 流程实例集合
     */
    public List<WfProcessInstance> selectWfProcessInstanceList(WfProcessInstance wfProcessInstance);

    /**
     * 根据业务ID查询流程实例
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 流程实例
     */
    public WfProcessInstance selectWfProcessInstanceByBusinessId(String businessType, Long businessId);

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
    public WfProcessInstance startProcessInstance(String processKey, String businessType, Long businessId, String businessNo, String title, String initiator);

    /**
     * 删除流程实例信息
     *
     * @param id 流程实例主键
     * @return 结果
     */
    public int deleteWfProcessInstanceById(Long id);
}
