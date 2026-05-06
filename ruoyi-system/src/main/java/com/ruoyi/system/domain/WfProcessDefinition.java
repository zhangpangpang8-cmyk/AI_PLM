package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

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

    /** 流程类型(drawing=图纸,document=文档) */
    @Excel(name = "流程类型", readConverterExp = "drawing=图纸,document=文档")
    private String processType;

    /** 流程描述 */
    @Excel(name = "流程描述")
    private String description;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

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

    public void setProcessType(String processType)
    {
        this.processType = processType;
    }

    public String getProcessType()
    {
        return processType;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processKey", getProcessKey())
            .append("processName", getProcessName())
            .append("processType", getProcessType())
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
