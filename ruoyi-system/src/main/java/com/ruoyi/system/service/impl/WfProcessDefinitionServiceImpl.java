package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.WfProcessDefinitionMapper;
import com.ruoyi.system.mapper.WfProcessNodeMapper;
import com.ruoyi.system.mapper.WfProcessEdgeMapper;
import com.ruoyi.system.domain.WfProcessDefinition;
import com.ruoyi.system.domain.WfProcessNode;
import com.ruoyi.system.domain.WfProcessEdge;
import com.ruoyi.system.service.IWfProcessDefinitionService;

/**
 * 流程定义Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class WfProcessDefinitionServiceImpl implements IWfProcessDefinitionService
{
    @Autowired
    private WfProcessDefinitionMapper wfProcessDefinitionMapper;

    @Autowired
    private WfProcessNodeMapper wfProcessNodeMapper;

    @Autowired
    private WfProcessEdgeMapper wfProcessEdgeMapper;

    /**
     * 查询流程定义
     *
     * @param id 流程定义主键
     * @return 流程定义
     */
    @Override
    public WfProcessDefinition selectWfProcessDefinitionById(Long id)
    {
        return wfProcessDefinitionMapper.selectWfProcessDefinitionById(id);
    }

    /**
     * 根据流程标识查询流程定义
     *
     * @param processKey 流程标识
     * @return 流程定义
     */
    @Override
    public WfProcessDefinition selectWfProcessDefinitionByKey(String processKey)
    {
        return wfProcessDefinitionMapper.selectWfProcessDefinitionByKey(processKey);
    }

    /**
     * 查询流程定义列表
     *
     * @param wfProcessDefinition 流程定义
     * @return 流程定义
     */
    @Override
    public List<WfProcessDefinition> selectWfProcessDefinitionList(WfProcessDefinition wfProcessDefinition)
    {
        return wfProcessDefinitionMapper.selectWfProcessDefinitionList(wfProcessDefinition);
    }

    /**
     * 新增流程定义
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    @Override
    public int insertWfProcessDefinition(WfProcessDefinition wfProcessDefinition)
    {
        return wfProcessDefinitionMapper.insertWfProcessDefinition(wfProcessDefinition);
    }

    /**
     * 修改流程定义
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    @Override
    public int updateWfProcessDefinition(WfProcessDefinition wfProcessDefinition)
    {
        return wfProcessDefinitionMapper.updateWfProcessDefinition(wfProcessDefinition);
    }

    /**
     * 批量删除流程定义
     *
     * @param ids 需要删除的流程定义主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWfProcessDefinitionByIds(Long[] ids)
    {
        for (Long id : ids)
        {
            deleteWfProcessDefinitionById(id);
        }
        return ids.length;
    }

    /**
     * 删除流程定义信息
     *
     * @param id 流程定义主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWfProcessDefinitionById(Long id)
    {
        wfProcessNodeMapper.deleteNodesByProcessDefinitionId(id);
        wfProcessEdgeMapper.deleteEdgesByProcessDefinitionId(id);
        return wfProcessDefinitionMapper.deleteWfProcessDefinitionById(id);
    }

    /**
     * 保存流程设计（包含节点和连线）
     *
     * @param wfProcessDefinition 流程定义
     * @param nodes 节点列表
     * @param edges 连线列表
     * @return 结果
     */
    @Override
    @Transactional
    public int saveProcessDesign(WfProcessDefinition wfProcessDefinition, List<WfProcessNode> nodes, List<WfProcessEdge> edges)
    {
        if (wfProcessDefinition.getStatus() == null || wfProcessDefinition.getStatus().isEmpty()) {
            wfProcessDefinition.setStatus("0");
        }

        if (wfProcessDefinition.getId() == null)
        {
            wfProcessDefinitionMapper.insertWfProcessDefinition(wfProcessDefinition);
        }
        else
        {
            wfProcessDefinitionMapper.updateWfProcessDefinition(wfProcessDefinition);
            wfProcessNodeMapper.deleteNodesByProcessDefinitionId(wfProcessDefinition.getId());
            wfProcessEdgeMapper.deleteEdgesByProcessDefinitionId(wfProcessDefinition.getId());
        }

        Long processDefinitionId = wfProcessDefinition.getId();

        if (processDefinitionId == null) {
            throw new RuntimeException("流程定义保存失败，未获取到主键ID");
        }

        java.util.Map<String, Long> nodeKeyToIdMap = new java.util.HashMap<>();

        if (nodes != null && !nodes.isEmpty())
        {
            for (WfProcessNode node : nodes)
            {
                node.setProcessDefinitionId(processDefinitionId);
                node.setId(null);
                wfProcessNodeMapper.insertWfProcessNode(node);
                if (node.getNodeKey() != null && node.getId() != null) {
                    nodeKeyToIdMap.put(node.getNodeKey(), node.getId());
                }
            }
        }

        if (edges != null && !edges.isEmpty())
        {
            for (WfProcessEdge edge : edges)
            {
                edge.setProcessDefinitionId(processDefinitionId);
                edge.setId(null);

                if (edge.getSourceNodeId() == null && edge.getSourceNodeKey() != null) {
                    edge.setSourceNodeId(nodeKeyToIdMap.get(edge.getSourceNodeKey()));
                }
                if (edge.getTargetNodeId() == null && edge.getTargetNodeKey() != null) {
                    edge.setTargetNodeId(nodeKeyToIdMap.get(edge.getTargetNodeKey()));
                }

                wfProcessEdgeMapper.insertWfProcessEdge(edge);
            }
        }

        return 1;

    }

    /**
     * 查询流程定义详情（包含节点和连线）
     *
     * @param id 流程定义ID
     * @return 流程定义
     */
    @Override
    public WfProcessDefinition selectProcessDefinitionDetail(Long id)
    {
        WfProcessDefinition definition = wfProcessDefinitionMapper.selectWfProcessDefinitionById(id);
        if (definition != null)
        {
            List<WfProcessNode> nodes = wfProcessNodeMapper.selectNodesByProcessDefinitionId(id);
            List<WfProcessEdge> edges = wfProcessEdgeMapper.selectEdgesByProcessDefinitionId(id);
            definition.setNodes(nodes);
            definition.setEdges(edges);
        }
        return definition;
    }
}
