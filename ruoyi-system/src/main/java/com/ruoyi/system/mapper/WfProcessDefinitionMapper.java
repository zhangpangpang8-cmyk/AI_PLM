package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WfProcessDefinition;

/**
 * 流程定义Mapper接口
 *
 * @author ruoyi
 */
public interface WfProcessDefinitionMapper
{
    /**
     * 查询流程定义
     *
     * @param id 流程定义主键
     * @return 流程定义
     */
    public WfProcessDefinition selectWfProcessDefinitionById(Long id);

    /**
     * 根据流程标识查询流程定义
     *
     * @param processKey 流程标识
     * @return 流程定义
     */
    public WfProcessDefinition selectWfProcessDefinitionByKey(String processKey);

    /**
     * 查询流程定义列表
     *
     * @param wfProcessDefinition 流程定义
     * @return 流程定义集合
     */
    public List<WfProcessDefinition> selectWfProcessDefinitionList(WfProcessDefinition wfProcessDefinition);

    /**
     * 新增流程定义
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    public int insertWfProcessDefinition(WfProcessDefinition wfProcessDefinition);

    /**
     * 修改流程定义
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    public int updateWfProcessDefinition(WfProcessDefinition wfProcessDefinition);

    /**
     * 删除流程定义
     *
     * @param id 流程定义主键
     * @return 结果
     */
    public int deleteWfProcessDefinitionById(Long id);

    /**
     * 批量删除流程定义
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfProcessDefinitionByIds(Long[] ids);
}
