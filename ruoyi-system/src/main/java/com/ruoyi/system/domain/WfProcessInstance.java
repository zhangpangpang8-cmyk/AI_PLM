package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程实例对象 wf_process_instance
 *
 * @author ruoyi
 */
public class WfProcessInstance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 流程定义ID */
    @Excel(name = "流程定义ID")
    private Long processDefinitionId;

    /** 业务类型(drawing=图纸,document=文档) */
    @Excel(name = "业务类型", readConverterExp = "drawing=图纸,document=文档")
    private String businessType;

    /** 业务ID */
    @Excel(name = "业务ID")
    private Long businessId;

    /** 业务编号 */
    @Excel(name = "业务编号")
    private String businessNo;

    /** 流程标题 */
    @Excel(name = "流程标题")
    private String title;

    /** 发起人 */
    @Excel(name = "发起人")
    private String initiator;

    /** 当前节点 */
    @Excel(name = "当前节点")
    private String currentNode;

    /** 状态(running=运行中,approved=已通过,rejected=已驳回,cancelled=已取消) */
    @Excel(name = "状态", readConverterExp = "running=运行中,approved=已通过,rejected=已驳回,cancelled=已取消")
    private String status;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 耗时(秒) */
    @Excel(name = "耗时(秒)")
    private Long duration;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setProcessDefinitionId(Long processDefinitionId)
    {
        this.processDefinitionId = processDefinitionId;
    }

    public Long getProcessDefinitionId()
    {
        return processDefinitionId;
    }

    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }

    public String getBusinessType()
    {
        return businessType;
    }

    public void setBusinessId(Long businessId)
    {
        this.businessId = businessId;
    }

    public Long getBusinessId()
    {
        return businessId;
    }

    public void setBusinessNo(String businessNo)
    {
        this.businessNo = businessNo;
    }

    public String getBusinessNo()
    {
        return businessNo;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setInitiator(String initiator)
    {
        this.initiator = initiator;
    }

    public String getInitiator()
    {
        return initiator;
    }

    public void setCurrentNode(String currentNode)
    {
        this.currentNode = currentNode;
    }

    public String getCurrentNode()
    {
        return currentNode;
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

    public void setDuration(Long duration)
    {
        this.duration = duration;
    }

    public Long getDuration()
    {
        return duration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processDefinitionId", getProcessDefinitionId())
            .append("businessType", getBusinessType())
            .append("businessId", getBusinessId())
            .append("businessNo", getBusinessNo())
            .append("title", getTitle())
            .append("initiator", getInitiator())
            .append("currentNode", getCurrentNode())
            .append("status", getStatus())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("duration", getDuration())
            .append("createTime", getCreateTime())
            .toString();
    }
}
