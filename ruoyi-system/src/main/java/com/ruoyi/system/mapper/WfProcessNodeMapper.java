package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.WfProcessNode;

/**
 * 流程节点Mapper接口
 *
 * @author ruoyi
 */
public interface WfProcessNodeMapper {
    /**
     * 查询流程节点
     *
     * @param id 流程节点主键
     * @return 流程节点
     */
    public WfProcessNode selectWfProcessNodeById(Long id);

    /**
     * 查询流程节点列表
     *
     * @param wfProcessNode 流程节点
     * @return 流程节点集合
     */
    public List<WfProcessNode> selectWfProcessNodeList(WfProcessNode wfProcessNode);

    /**
     * 根据流程定义ID查询节点列表
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程节点集合
     */
    public List<WfProcessNode> selectNodesByProcessDefinitionId(Long processDefinitionId);

    /**
     * 新增流程节点
     *
     * @param wfProcessNode 流程节点
     * @return 结果
     */
    public int insertWfProcessNode(WfProcessNode wfProcessNode);

    /**
     * 修改流程节点
     *
     * @param wfProcessNode 流程节点
     * @return 结果
     */
    public int updateWfProcessNode(WfProcessNode wfProcessNode);

    /**
     * 删除流程节点
     *
     * @param id 流程节点主键
     * @return 结果
     */
    public int deleteWfProcessNodeById(Long id);

    /**
     * 批量删除流程节点
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfProcessNodeByIds(Long[] ids);

    /**
     * 根据流程定义ID删除节点
     *
     * @param processDefinitionId 流程定义ID
     * @return 结果
     */
    public int deleteNodesByProcessDefinitionId(Long processDefinitionId);

    /**
     * 根据流程定义ID查询开始节点
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程节点
     */
    public WfProcessNode selectStartNodeByDefinitionId(Long processDefinitionId);
}
