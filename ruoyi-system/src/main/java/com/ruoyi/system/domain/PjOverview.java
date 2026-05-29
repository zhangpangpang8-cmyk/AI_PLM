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
 * @date 2026-05-28
 */
public class PjOverview extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目编码
     */
    @Excel(name = "项目编码")
    private String projectCode;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 项目分类id
     */
    @Excel(name = "项目分类id")
    private Long classificationId;

    /**
     * 项目分类名称
     */
    @Excel(name = "项目分类名称")
    private String classificationName;

    /**
     * 项目经理id
     */
    @Excel(name = "项目经理id")
    private Long userId;

    /**
     * 项目经理名称
     */
    @Excel(name = "项目经理名称")
    private String nickName;

    /**
     * 项目状态
     */
    @Excel(name = "项目状态")
    private String status;

    /**
     * 审核状态（0待审核 1审核通过 2审核驳回）
     */
    @Excel(name = "审核状态", readConverterExp = "0=待审核,1=审核通过,2=审核驳回")
    private String auditStatus;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String auditBy;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     * 审核备注
     */
    @Excel(name = "审核备注")
    private String auditRemark;
    /**
     * 项目开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 项目结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 项目进度
     */
    @Excel(name = "项目进度")
    private BigDecimal projectProgress;

    /**
     * 项目描述
     */
    @Excel(name = "项目描述")
    private String projectInfo;

    /**
     * 申请流程
     */
    @Excel(name = "申请流程")
    private String flowKey;

    /**
     * 流程实例id
     */
    @Excel(name = "流程实例id")
    private String flowInsId;

    /**
     * 单据类型
     */
    @Excel(name = "单据类型")
    private String billType;

    /**
     * 优先级（0紧急 1高 2中 3低）
     */
    @Excel(name = "优先级", readConverterExp = "0=紧急,1=高,2=中,3=低")
    private String priority;

    /**
     * 项目类型（new_product=新产品, improvement=改进, research=研发）
     */
    @Excel(name = "项目类型", readConverterExp = "n=ew_product=新产品,,i=mprovement=改进,,r=esearch=研发")
    private String projectType;

    /**
     * 计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planStartTime;

    /**
     * 计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndTime;

    /**
     * 实际开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualStartTime;

    /**
     * 实际结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualEndTime;

    /**
     * 项目预算（元）
     */
    @Excel(name = "项目预算", readConverterExp = "元=")
    private BigDecimal budget;

    /**
     * 实际成本（元）
     */
    @Excel(name = "实际成本", readConverterExp = "元=")
    private BigDecimal actualCost;

    /**
     * 完成率（%）
     */
    @Excel(name = "完成率", readConverterExp = "%=")
    private BigDecimal completionRate;

    /**
     * 健康度（0正常 1预警 2危险）
     */
    @Excel(name = "健康度", readConverterExp = "0=正常,1=预警,2=危险")
    private String healthStatus;

    /**
     * 结项时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结项时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date closeTime;

    /**
     * 结项说明
     */
    @Excel(name = "结项说明")
    private String closeReason;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setProjectProgress(BigDecimal projectProgress) {
        this.projectProgress = projectProgress;
    }

    public BigDecimal getProjectProgress() {
        return projectProgress;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getProjectInfo() {
        return projectInfo;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowInsId(String flowInsId) {
        this.flowInsId = flowInsId;
    }

    public String getFlowInsId() {
        return flowInsId;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillType() {
        return billType;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public BigDecimal getActualCost() {
        return actualCost;
    }

    public void setCompletionRate(BigDecimal completionRate) {
        this.completionRate = completionRate;
    }

    public BigDecimal getCompletionRate() {
        return completionRate;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
                .append("priority", getPriority())
                .append("projectType", getProjectType())
                .append("planStartTime", getPlanStartTime())
                .append("planEndTime", getPlanEndTime())
                .append("actualStartTime", getActualStartTime())
                .append("actualEndTime", getActualEndTime())
                .append("budget", getBudget())
                .append("actualCost", getActualCost())
                .append("completionRate", getCompletionRate())
                .append("healthStatus", getHealthStatus())
                .append("closeTime", getCloseTime())
                .append("closeReason", getCloseReason())
                .append("userId", getUserId())
                .append("nickName", getNickName())
                .append("status", getStatus())
                .append("auditStatus", getAuditStatus())
                .append("auditBy", getAuditBy())
                .append("auditTime", getAuditTime())
                .append("auditRemark", getAuditRemark())
                .append("startTime", getStartTime())
                .toString();
    }
}
