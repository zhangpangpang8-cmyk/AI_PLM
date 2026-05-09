
package com.ruoyi.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 流程连线对象 wf_process_edge
 *
 * @author ruoyi
 */
public class WfProcessEdge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 流程定义ID
     */
    @Excel(name = "流程定义ID")
    private Long processDefinitionId;

    /**
     * 连线标识
     */
    @Excel(name = "连线标识")
    private String edgeKey;

    /**
     * 源节点ID
     */
    @Excel(name = "源节点ID")
    private Long sourceNodeId;

    /**
     * 目标节点ID
     */
    @Excel(name = "目标节点ID")
    private Long targetNodeId;

    /**
     * 条件表达式
     */
    @Excel(name = "条件表达式")
    private String conditionExpr;

    /**
     * 条件文本
     */
    @Excel(name = "条件文本")
    private String conditionText;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProcessDefinitionId(Long processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public Long getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setEdgeKey(String edgeKey) {
        this.edgeKey = edgeKey;
    }

    public String getEdgeKey() {
        return edgeKey;
    }

    public void setSourceNodeId(Long sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public Long getSourceNodeId() {
        return sourceNodeId;
    }

    public void setTargetNodeId(Long targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public Long getTargetNodeId() {
        return targetNodeId;
    }

    public void setConditionExpr(String conditionExpr) {
        this.conditionExpr = conditionExpr;
    }

    public String getConditionExpr() {
        return conditionExpr;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("processDefinitionId", getProcessDefinitionId())
                .append("edgeKey", getEdgeKey())
                .append("sourceNodeId", getSourceNodeId())
                .append("targetNodeId", getTargetNodeId())
                .append("conditionExpr", getConditionExpr())
                .append("conditionText", getConditionText())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .toString();
    }
}
