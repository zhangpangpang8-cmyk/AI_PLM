package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务进度日志对象 pj_task_log
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
public class PjTaskLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 所属项目ID */
    @Excel(name = "所属项目ID")
    private Long projectId;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskId;

    /** 任务编码 */
    @Excel(name = "任务编码")
    private String taskCode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 日志日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日志日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logDate;

    /** 变更前进度（%） */
    @Excel(name = "变更前进度", readConverterExp = "%=")
    private BigDecimal progressBefore;

    /** 变更后进度（%） */
    @Excel(name = "变更后进度", readConverterExp = "%=")
    private BigDecimal progressAfter;

    /** 本次填报工时（小时） */
    @Excel(name = "本次填报工时", readConverterExp = "小=时")
    private BigDecimal workHours;

    /** 工作内容描述 */
    @Excel(name = "工作内容描述")
    private String workContent;

    /** 遇到的问题 */
    @Excel(name = "遇到的问题")
    private String issues;

    /** 操作人ID */
    @Excel(name = "操作人ID")
    private Long operatorId;

    /** 操作人姓名 */
    @Excel(name = "操作人姓名")
    private String operatorName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    public void setTaskCode(String taskCode) 
    {
        this.taskCode = taskCode;
    }

    public String getTaskCode() 
    {
        return taskCode;
    }

    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }

    public void setLogDate(Date logDate) 
    {
        this.logDate = logDate;
    }

    public Date getLogDate() 
    {
        return logDate;
    }

    public void setProgressBefore(BigDecimal progressBefore) 
    {
        this.progressBefore = progressBefore;
    }

    public BigDecimal getProgressBefore() 
    {
        return progressBefore;
    }

    public void setProgressAfter(BigDecimal progressAfter) 
    {
        this.progressAfter = progressAfter;
    }

    public BigDecimal getProgressAfter() 
    {
        return progressAfter;
    }

    public void setWorkHours(BigDecimal workHours) 
    {
        this.workHours = workHours;
    }

    public BigDecimal getWorkHours() 
    {
        return workHours;
    }

    public void setWorkContent(String workContent) 
    {
        this.workContent = workContent;
    }

    public String getWorkContent() 
    {
        return workContent;
    }

    public void setIssues(String issues) 
    {
        this.issues = issues;
    }

    public String getIssues() 
    {
        return issues;
    }

    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }

    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("taskId", getTaskId())
            .append("taskCode", getTaskCode())
            .append("taskName", getTaskName())
            .append("logDate", getLogDate())
            .append("progressBefore", getProgressBefore())
            .append("progressAfter", getProgressAfter())
            .append("workHours", getWorkHours())
            .append("workContent", getWorkContent())
            .append("issues", getIssues())
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
