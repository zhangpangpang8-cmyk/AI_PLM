package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 技术文档对象 dm_tech
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public class DmTech extends BaseEntity implements com.ruoyi.system.domain.support.DocumentFileMetadata
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 技术文档编码 */
    @Excel(name = "技术文档编码")
    private String techCode;

    /** 技术文档名称 */
    @Excel(name = "技术文档名称")
    private String techName;

    /** 技术文档类型 */
    @Excel(name = "技术文档类型")
    private String techType;

    /** 技术文档语言类型 */
    @Excel(name = "技术文档语言类型")
    private String techLanguage;

    /** 版本 */
    @Excel(name = "版本")
    private String techVersion;

    /** 是否最新版本 */
    @Excel(name = "是否最新版本")
    private String latestVersion;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private Long size;

    /** 版本说明 */
    @Excel(name = "版本说明")
    private String versionDescription;

    /** 申请流程 */
    @Excel(name = "申请流程")
    private String flowKey;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String flowInsId;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billType;

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

    /** 是否允许变更（0=不允许，1=允许） */
    @Excel(name = "是否允许变更", readConverterExp = "0==不允许，1=允许")
    private String allowChange;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTechCode(String techCode) 
    {
        this.techCode = techCode;
    }

    public String getTechCode() 
    {
        return techCode;
    }

    public void setTechName(String techName) 
    {
        this.techName = techName;
    }

    public String getTechName() 
    {
        return techName;
    }

    public void setTechType(String techType) 
    {
        this.techType = techType;
    }

    public String getTechType() 
    {
        return techType;
    }

    public void setTechLanguage(String techLanguage) 
    {
        this.techLanguage = techLanguage;
    }

    public String getTechLanguage() 
    {
        return techLanguage;
    }

    public void setTechVersion(String techVersion) 
    {
        this.techVersion = techVersion;
    }

    public String getTechVersion() 
    {
        return techVersion;
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

    public void setVersionDescription(String versionDescription) 
    {
        this.versionDescription = versionDescription;
    }

    public String getVersionDescription() 
    {
        return versionDescription;
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

    public void setAllowChange(String allowChange) 
    {
        this.allowChange = allowChange;
    }

    public String getAllowChange() 
    {
        return allowChange;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("techCode", getTechCode())
            .append("techName", getTechName())
            .append("techType", getTechType())
            .append("techLanguage", getTechLanguage())
            .append("techVersion", getTechVersion())
            .append("latestVersion", getLatestVersion())
            .append("status", getStatus())
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
            .append("versionDescription", getVersionDescription())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("flowKey", getFlowKey())
            .append("flowInsId", getFlowInsId())
            .append("billType", getBillType())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("projectCode", getProjectCode())
            .append("projectTaskId", getProjectTaskId())
            .append("projectTaskCode", getProjectTaskCode())
            .append("projectTaskName", getProjectTaskName())
            .append("allowChange", getAllowChange())
            .toString();
    }
}
