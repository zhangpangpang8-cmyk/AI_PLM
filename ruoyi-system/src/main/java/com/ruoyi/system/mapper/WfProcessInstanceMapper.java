package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WfProcessInstance;

/**
 * 流程实例Mapper接口
 *
 * @author ruoyi
 */
public interface WfProcessInstanceMapper
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
     * 新增流程实例
     *
     * @param wfProcessInstance 流程实例
     * @return 结果
     */
    public int insertWfProcessInstance(WfProcessInstance wfProcessInstance);

    /**
     * 修改流程实例
     *
     * @param wfProcessInstance 流程实例
     * @return 结果
     */
    public int updateWfProcessInstance(WfProcessInstance wfProcessInstance);

    /**
     * 删除流程实例
     *
     * @param id 流程实例主键
     * @return 结果
     */
    public int deleteWfProcessInstanceById(Long id);
}
