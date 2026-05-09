package com.ruoyi.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 流程节点对象 wf_process_node
 *
 * @author ruoyi
 */
public class WfProcessNode extends BaseEntity {
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
     * 节点标识
     */
    @Excel(name = "节点标识")
    private String nodeKey;

    /**
     * 节点名称
     */
    @Excel(name = "节点名称")
    private String nodeName;

    /**
     * 节点类型(startEvent=开始,endEvent=结束,userTask=用户任务,exclusiveGateway=排他网关)
     */
    @Excel(name = "节点类型", readConverterExp = "startEvent=开始,endEvent=结束,userTask=用户任务,exclusiveGateway=排他网关")
    private String nodeType;

    /**
     * 节点X坐标
     */
    private Integer nodeX;

    /**
     * 节点Y坐标
     */
    private Integer nodeY;

    /**
     * 审批人类型(user=指定用户,role=角色,dept=部门,leader=部门负责人)
     */
    @Excel(name = "审批人类型", readConverterExp = "user=指定用户,role=角色,dept=部门,leader=部门负责人")
    private String assigneeType;

    /**
     * 审批人值
     */
    @Excel(name = "审批人值")
    private String assigneeValue;

    /**
     * 表单标识
     */
    @Excel(name = "表单标识")
    private String formKey;

    /**
     * 条件表达式
     */
    @Excel(name = "条件表达式")
    private String conditionExpr;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

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

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeX(Integer nodeX) {
        this.nodeX = nodeX;
    }

    public Integer getNodeX() {
        return nodeX;
    }

    public void setNodeY(Integer nodeY) {
        this.nodeY = nodeY;
    }

    public Integer getNodeY() {
        return nodeY;
    }

    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }

    public String getAssigneeType() {
        return assigneeType;
    }

    public void setAssigneeValue(String assigneeValue) {
        this.assigneeValue = assigneeValue;
    }

    public String getAssigneeValue() {
        return assigneeValue;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setConditionExpr(String conditionExpr) {
        this.conditionExpr = conditionExpr;
    }

    public String getConditionExpr() {
        return conditionExpr;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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
                .append("nodeKey", getNodeKey())
                .append("nodeName", getNodeName())
                .append("nodeType", getNodeType())
                .append("nodeX", getNodeX())
                .append("nodeY", getNodeY())
                .append("assigneeType", getAssigneeType())
                .append("assigneeValue", getAssigneeValue())
                .append("formKey", getFormKey())
                .append("conditionExpr", getConditionExpr())
                .append("sort", getSort())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .toString();
    }
}
