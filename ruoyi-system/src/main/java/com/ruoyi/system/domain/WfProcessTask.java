package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程任务对象 wf_process_task
 *
 * @author ruoyi
 */
public class WfProcessTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private Long instanceId;

    /** 节点名称 */
    @Excel(name = "节点名称")
    private String nodeName;

    /** 任务分配人 */
    @Excel(name = "任务分配人")
    private String assignee;

    /** 候选用户 */
    @Excel(name = "候选用户")
    private String candidateUsers;

    /** 任务状态(pending=待处理,approved=已通过,rejected=已驳回,skipped=已跳过) */
    @Excel(name = "任务状态", readConverterExp = "pending=待处理,approved=已通过,rejected=已驳回,skipped=已跳过")
    private String taskStatus;

    /** 审批意见 */
    @Excel(name = "审批意见")
    private String approvalOpinion;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date approvalTime;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setInstanceId(Long instanceId)
    {
        this.instanceId = instanceId;
    }

    public Long getInstanceId()
    {
        return instanceId;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }

    public String getAssignee()
    {
        return assignee;
    }

    public void setCandidateUsers(String candidateUsers)
    {
        this.candidateUsers = candidateUsers;
    }

    public String getCandidateUsers()
    {
        return candidateUsers;
    }

    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus()
    {
        return taskStatus;
    }

    public void setApprovalOpinion(String approvalOpinion)
    {
        this.approvalOpinion = approvalOpinion;
    }

    public String getApprovalOpinion()
    {
        return approvalOpinion;
    }

    public void setApprovalTime(Date approvalTime)
    {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime()
    {
        return approvalTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("instanceId", getInstanceId())
                .append("nodeName", getNodeName())
                .append("assignee", getAssignee())
                .append("candidateUsers", getCandidateUsers())
                .append("taskStatus", getTaskStatus())
                .append("approvalOpinion", getApprovalOpinion())
                .append("approvalTime", getApprovalTime())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("createTime", getCreateTime())
                .toString();
    }
}
