package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.WfProcessEdge;

/**
 * 流程连线Mapper接口
 *
 * @author ruoyi
 */
public interface WfProcessEdgeMapper {
    /**
     * 查询流程连线
     *
     * @param id 流程连线主键
     * @return 流程连线
     */
    public WfProcessEdge selectWfProcessEdgeById(Long id);

    /**
     * 查询流程连线列表
     *
     * @param wfProcessEdge 流程连线
     * @return 流程连线集合
     */
    public List<WfProcessEdge> selectWfProcessEdgeList(WfProcessEdge wfProcessEdge);

    /**
     * 根据流程定义ID查询连线列表
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程连线集合
     */
    public List<WfProcessEdge> selectEdgesByProcessDefinitionId(Long processDefinitionId);

    /**
     * 新增流程连线
     *
     * @param wfProcessEdge 流程连线
     * @return 结果
     */
    public int insertWfProcessEdge(WfProcessEdge wfProcessEdge);

    /**
     * 修改流程连线
     *
     * @param wfProcessEdge 流程连线
     * @return 结果
     */
    public int updateWfProcessEdge(WfProcessEdge wfProcessEdge);

    /**
     * 删除流程连线
     *
     * @param id 流程连线主键
     * @return 结果
     */
    public int deleteWfProcessEdgeById(Long id);

    /**
     * 批量删除流程连线
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfProcessEdgeByIds(Long[] ids);

    /**
     * 根据流程定义ID删除连线
     *
     * @param processDefinitionId 流程定义ID
     * @return 结果
     */
    public int deleteEdgesByProcessDefinitionId(Long processDefinitionId);

    /**
     * 根据源节点ID查询连线
     *
     * @param sourceNodeId 源节点ID
     * @return 流程连线
     */
    public WfProcessEdge selectEdgeBySourceNodeId(Long sourceNodeId);

    /** 查询源节点的全部出边，用于安全推进流程。 */
    public List<WfProcessEdge> selectEdgesBySourceNodeId(Long sourceNodeId);
}
