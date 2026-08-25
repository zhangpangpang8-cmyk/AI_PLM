package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目阶段对象 pj_phase
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
public class PjPhase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 所属项目ID */
    @Excel(name = "所属项目ID")
    private Long projectId;

    /** 阶段编码 */
    @Excel(name = "阶段编码")
    private String phaseCode;

    /** 阶段名称 */
    @Excel(name = "阶段名称")
    private String phaseName;

    /** 阶段类型（initiation=立项, planning=计划, execution=执行, monitoring=监控, closure=结项） */
    @Excel(name = "阶段类型", readConverterExp = "initiation=立项,planning=计划,execution=执行,monitoring=监控,closure=结项")
    private String phaseType;

    /** 计划开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planStartDate;

    /** 计划结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndDate;

    /** 实际开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualStartDate;

    /** 实际结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualEndDate;

    /** 阶段状态（not_started=未开始, in_progress=进行中, completed=已完成） */
    @Excel(name = "阶段状态", readConverterExp = "not_started=未开始,in_progress=进行中,completed=已完成")
    private String phaseStatus;

    /** 阶段交付物 */
    @Excel(name = "阶段交付物")
    private String deliverables;

    /** 进入条件 */
    @Excel(name = "进入条件")
    private String entryCriteria;

    /** 退出条件 */
    @Excel(name = "退出条件")
    private String exitCriteria;

    /** 阶段排序 */
    @Excel(name = "阶段排序")
    private Long sortOrder;

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

    public void setPhaseCode(String phaseCode) 
    {
        this.phaseCode = phaseCode;
    }

    public String getPhaseCode() 
    {
        return phaseCode;
    }

    public void setPhaseName(String phaseName) 
    {
        this.phaseName = phaseName;
    }

    public String getPhaseName() 
    {
        return phaseName;
    }

    public void setPhaseType(String phaseType) 
    {
        this.phaseType = phaseType;
    }

    public String getPhaseType() 
    {
        return phaseType;
    }

    public void setPlanStartDate(Date planStartDate) 
    {
        this.planStartDate = planStartDate;
    }

    public Date getPlanStartDate() 
    {
        return planStartDate;
    }

    public void setPlanEndDate(Date planEndDate) 
    {
        this.planEndDate = planEndDate;
    }

    public Date getPlanEndDate() 
    {
        return planEndDate;
    }

    public void setActualStartDate(Date actualStartDate) 
    {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualStartDate() 
    {
        return actualStartDate;
    }

    public void setActualEndDate(Date actualEndDate) 
    {
        this.actualEndDate = actualEndDate;
    }

    public Date getActualEndDate() 
    {
        return actualEndDate;
    }

    public void setPhaseStatus(String phaseStatus) 
    {
        this.phaseStatus = phaseStatus;
    }

    public String getPhaseStatus() 
    {
        return phaseStatus;
    }

    public void setDeliverables(String deliverables) 
    {
        this.deliverables = deliverables;
    }

    public String getDeliverables() 
    {
        return deliverables;
    }

    public void setEntryCriteria(String entryCriteria) 
    {
        this.entryCriteria = entryCriteria;
    }

    public String getEntryCriteria() 
    {
        return entryCriteria;
    }

    public void setExitCriteria(String exitCriteria) 
    {
        this.exitCriteria = exitCriteria;
    }

    public String getExitCriteria() 
    {
        return exitCriteria;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("phaseCode", getPhaseCode())
            .append("phaseName", getPhaseName())
            .append("phaseType", getPhaseType())
            .append("planStartDate", getPlanStartDate())
            .append("planEndDate", getPlanEndDate())
            .append("actualStartDate", getActualStartDate())
            .append("actualEndDate", getActualEndDate())
            .append("phaseStatus", getPhaseStatus())
            .append("deliverables", getDeliverables())
            .append("entryCriteria", getEntryCriteria())
            .append("exitCriteria", getExitCriteria())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
