package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.List;

/**
 * 流程定义对象 wf_process_definition
 *
 * @author ruoyi
 */
public class WfProcessDefinition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 流程标识 */
    @Excel(name = "流程标识")
    private String processKey;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String processName;

    /** 流程分类 */
    @Excel(name = "流程分类")
    private String processCategory;

    /** 流程类型(drawing=图纸,document=文档,ecn=变更通知) */
    @Excel(name = "流程类型", readConverterExp = "drawing=图纸,document=文档,ecn=变更通知")
    private String processType;

    /** BPMN XML内容 */
    private String bpmnXml;

    /** 流程描述 */
    @Excel(name = "流程描述")
    private String description;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 节点列表(非数据库字段) */
    private List<WfProcessNode> nodes;

    /** 连线列表(非数据库字段) */
    private List<WfProcessEdge> edges;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setProcessKey(String processKey)
    {
        this.processKey = processKey;
    }

    public String getProcessKey()
    {
        return processKey;
    }

    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String getProcessName()
    {
        return processName;
    }

    public void setProcessCategory(String processCategory)
    {
        this.processCategory = processCategory;
    }

    public String getProcessCategory()
    {
        return processCategory;
    }

    public void setProcessType(String processType)
    {
        this.processType = processType;
    }

    public String getProcessType()
    {
        return processType;
    }

    public void setBpmnXml(String bpmnXml)
    {
        this.bpmnXml = bpmnXml;
    }

    public String getBpmnXml()
    {
        return bpmnXml;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setNodes(List<WfProcessNode> nodes)
    {
        this.nodes = nodes;
    }

    public List<WfProcessNode> getNodes()
    {
        return nodes;
    }

    public void setEdges(List<WfProcessEdge> edges)
    {
        this.edges = edges;
    }

    public List<WfProcessEdge> getEdges()
    {
        return edges;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("processKey", getProcessKey())
                .append("processName", getProcessName())
                .append("processCategory", getProcessCategory())
                .append("processType", getProcessType())
                .append("bpmnXml", getBpmnXml())
                .append("description", getDescription())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
