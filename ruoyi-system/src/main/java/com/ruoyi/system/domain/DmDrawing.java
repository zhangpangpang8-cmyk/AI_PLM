package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图纸管理对象 dm_drawing
 *
 * @author ruoyi
 */
public class DmDrawing extends BaseEntity implements com.ruoyi.system.domain.support.DocumentFileMetadata
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 图纸编号 */
    @Excel(name = "图纸编号")
    private String drawingCode;

    /** 图纸名称 */
    @Excel(name = "图纸名称")
    private String drawingName;

    /** 图纸类型 */
    @Excel(name = "图纸类型")
    private String drawingType;

    /** 图纸版本 */
    @Excel(name = "图纸版本")
    private String drawingVersion;

    /** 是否最新版本 */
    @Excel(name = "是否最新版本")
    private String latestVersion;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目编码 */
    @Excel(name = "项目编码")
    private String projectCode;

    /** 项目任务id */
    @Excel(name = "项目任务id")
    private Long projectTaskId;

    /** 项目任务编码 */
    @Excel(name = "项目任务编码")
    private String projectTaskCode;

    /** 项目任务名称 */
    @Excel(name = "项目任务名称")
    private String projectTaskName;

    /** 变更id */
    @Excel(name = "变更id")
    private Long ecoId;

    /** 变更标题 */
    @Excel(name = "变更标题")
    private String ecoTitle;

    /** 变更编码 */
    @Excel(name = "变更编码")
    private String ecoCode;

    /** 变更说明 */
    @Excel(name = "变更说明")
    private String ecoDescription;

    /** 发布状态 */
    @Excel(name = "发布状态")
    private String publishStatus;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件后缀 */
    @Excel(name = "文件后缀")
    private String fileSuffix;

    /** 文件大小（字符串格式） */
    @Excel(name = "文件大小")
    private String fileSize;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件大小（字节） */
    @Excel(name = "文件大小(字节)")
    private Long size;

    /** 申请流程 */
    @Excel(name = "申请流程")
    private String flowKey;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String flowInsId;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billType;

    /** 能否进行变更（0=不允许，1=允许） */
    @Excel(name = "能否进行变更", readConverterExp = "0=不允许,1=允许")
    private String allowChange;

    /** 审批状态（0=草稿,1=审批中,2=已通过,3=已驳回,4=已取消） */
    @Excel(name = "审批状态", readConverterExp = "0=草稿,1=审批中,2=已通过,3=已驳回,4=已取消")
    private String approvalStatus;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setDrawingCode(String drawingCode)
    {
        this.drawingCode = drawingCode;
    }

    public String getDrawingCode()
    {
        return drawingCode;
    }

    public void setDrawingName(String drawingName)
    {
        this.drawingName = drawingName;
    }

    public String getDrawingName()
    {
        return drawingName;
    }

    public void setDrawingType(String drawingType)
    {
        this.drawingType = drawingType;
    }

    public String getDrawingType()
    {
        return drawingType;
    }

    public void setDrawingVersion(String drawingVersion)
    {
        this.drawingVersion = drawingVersion;
    }

    public String getDrawingVersion()
    {
        return drawingVersion;
    }

    public void setLatestVersion(String latestVersion)
    {
        this.latestVersion = latestVersion;
    }

    public String getLatestVersion()
    {
        return latestVersion;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectCode(String projectCode)
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode()
    {
        return projectCode;
    }

    public void setProjectTaskId(Long projectTaskId)
    {
        this.projectTaskId = projectTaskId;
    }

    public Long getProjectTaskId()
    {
        return projectTaskId;
    }

    public void setProjectTaskCode(String projectTaskCode)
    {
        this.projectTaskCode = projectTaskCode;
    }

    public String getProjectTaskCode()
    {
        return projectTaskCode;
    }

    public void setProjectTaskName(String projectTaskName)
    {
        this.projectTaskName = projectTaskName;
    }

    public String getProjectTaskName()
    {
        return projectTaskName;
    }

    public void setEcoId(Long ecoId)
    {
        this.ecoId = ecoId;
    }

    public Long getEcoId()
    {
        return ecoId;
    }

    public void setEcoTitle(String ecoTitle)
    {
        this.ecoTitle = ecoTitle;
    }

    public String getEcoTitle()
    {
        return ecoTitle;
    }

    public void setEcoCode(String ecoCode)
    {
        this.ecoCode = ecoCode;
    }

    public String getEcoCode()
    {
        return ecoCode;
    }

    public void setEcoDescription(String ecoDescription)
    {
        this.ecoDescription = ecoDescription;
    }

    public String getEcoDescription()
    {
        return ecoDescription;
    }

    public void setPublishStatus(String publishStatus)
    {
        this.publishStatus = publishStatus;
    }

    public String getPublishStatus()
    {
        return publishStatus;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileSuffix(String fileSuffix)
    {
        this.fileSuffix = fileSuffix;
    }

    public String getFileSuffix()
    {
        return fileSuffix;
    }

    public void setFileSize(String fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getFileSize()
    {
        return fileSize;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setSize(Long size)
    {
        this.size = size;
    }

    public Long getSize()
    {
        return size;
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

    public void setAllowChange(String allowChange)
    {
        this.allowChange = allowChange;
    }

    public String getAllowChange()
    {
        return allowChange;
    }

    public void setApprovalStatus(String approvalStatus)
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus()
    {
        return approvalStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("drawingCode", getDrawingCode())
                .append("drawingName", getDrawingName())
                .append("drawingType", getDrawingType())
                .append("drawingVersion", getDrawingVersion())
                .append("latestVersion", getLatestVersion())
                .append("status", getStatus())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("projectCode", getProjectCode())
                .append("projectTaskId", getProjectTaskId())
                .append("projectTaskCode", getProjectTaskCode())
                .append("projectTaskName", getProjectTaskName())
                .append("ecoId", getEcoId())
                .append("ecoTitle", getEcoTitle())
                .append("ecoCode", getEcoCode())
                .append("ecoDescription", getEcoDescription())
                .append("publishStatus", getPublishStatus())
                .append("fileName", getFileName())
                .append("fileSuffix", getFileSuffix())
                .append("fileSize", getFileSize())
                .append("filePath", getFilePath())
                .append("size", getSize())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("flowKey", getFlowKey())
                .append("flowInsId", getFlowInsId())
                .append("billType", getBillType())
                .append("allowChange", getAllowChange())
                .toString();
    }
}
