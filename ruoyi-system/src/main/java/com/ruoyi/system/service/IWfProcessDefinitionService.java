package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.WfProcessDefinition;
import com.ruoyi.system.domain.WfProcessNode;
import com.ruoyi.system.domain.WfProcessEdge;

/**
 * 流程定义Service接口（增强版）
 *
 * @author ruoyi
 */
public interface IWfProcessDefinitionService
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
     * 新增流程定义（包含节点和连线）
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    public int insertWfProcessDefinition(WfProcessDefinition wfProcessDefinition);

    /**
     * 修改流程定义（包含节点和连线）
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    public int updateWfProcessDefinition(WfProcessDefinition wfProcessDefinition);

    /**
     * 批量删除流程定义
     *
     * @param ids 需要删除的流程定义主键集合
     * @return 结果
     */
    public int deleteWfProcessDefinitionByIds(Long[] ids);

    /**
     * 删除流程定义信息
     *
     * @param id 流程定义主键
     * @return 结果
     */
    public int deleteWfProcessDefinitionById(Long id);

    /**
     * 保存流程设计（包含节点和连线）
     *
     * @param wfProcessDefinition 流程定义
     * @param nodes 节点列表
     * @param edges 连线列表
     * @return 结果
     */
    public int saveProcessDesign(WfProcessDefinition wfProcessDefinition, List<WfProcessNode> nodes, List<WfProcessEdge> edges);

    /**
     * 查询流程定义详情（包含节点和连线）
     *
     * @param id 流程定义ID
     * @return 流程定义
     */
    public WfProcessDefinition selectProcessDefinitionDetail(Long id);
}
