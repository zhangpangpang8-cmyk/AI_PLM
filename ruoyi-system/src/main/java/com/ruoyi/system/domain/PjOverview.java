package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目概述对象 pj_overview
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
public class PjOverview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 项目编码 */
    @Excel(name = "项目编码")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目分类id */
    @Excel(name = "项目分类id")
    private Long classificationId;

    /** 项目分类名称 */
    @Excel(name = "项目分类名称")
    private String classificationName;

    /** 项目经理id */
    @Excel(name = "项目经理id")
    private Long userId;

    /** 项目经理名称 */
    @Excel(name = "项目经理名称")
    private String nickName;

    /** 项目状态 */
    @Excel(name = "项目状态")
    private String status;

    /** 项目开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 项目结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 项目进度 */
    @Excel(name = "项目进度")
    private BigDecimal projectProgress;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String projectInfo;

    /** 申请流程 */
    @Excel(name = "申请流程")
    private String flowKey;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String flowInsId;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setProjectCode(String projectCode) 
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode() 
    {
        return projectCode;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setClassificationId(Long classificationId) 
    {
        this.classificationId = classificationId;
    }

    public Long getClassificationId() 
    {
        return classificationId;
    }

    public void setClassificationName(String classificationName) 
    {
        this.classificationName = classificationName;
    }

    public String getClassificationName() 
    {
        return classificationName;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    public void setProjectProgress(BigDecimal projectProgress) 
    {
        this.projectProgress = projectProgress;
    }

    public BigDecimal getProjectProgress() 
    {
        return projectProgress;
    }

    public void setProjectInfo(String projectInfo) 
    {
        this.projectInfo = projectInfo;
    }

    public String getProjectInfo() 
    {
        return projectInfo;
    }

    public void setFlowKey(String flowKey) 
    {
        this.flowKey = flowKey;
    }

    public String getFlowKey() 
    {
        return flowKey;
    }

    public void setFlowInsId(String flowInsId) 
    {
        this.flowInsId = flowInsId;
    }

    public String getFlowInsId() 
    {
        return flowInsId;
    }

    public void setBillType(String billType) 
    {
        this.billType = billType;
    }

    public String getBillType() 
    {
        return billType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectCode", getProjectCode())
            .append("projectName", getProjectName())
            .append("classificationId", getClassificationId())
            .append("classificationName", getClassificationName())
            .append("userId", getUserId())
            .append("nickName", getNickName())
            .append("status", getStatus())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("projectProgress", getProjectProgress())
            .append("projectInfo", getProjectInfo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("flowKey", getFlowKey())
            .append("flowInsId", getFlowInsId())
            .append("billType", getBillType())
            .toString();
    }
}
