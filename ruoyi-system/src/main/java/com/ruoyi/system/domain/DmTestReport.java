package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测试报告对象 dm_test_report
 * 
 * @author ruoyi
 * @date 2026-05-21
 */
public class DmTestReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 测试编码 */
    @Excel(name = "测试编码")
    private String testCode;

    /** bomId */
    @Excel(name = "bomId")
    private Long bomId;

    /** bom名称 */
    @Excel(name = "bom名称")
    private String bomName;

    /** bom编码 */
    @Excel(name = "bom编码")
    private String bomCode;

    /** 测试类型 */
    @Excel(name = "测试类型")
    private String testType;

    /** 测试日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "测试日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date testDate;

    /** 测试人员 */
    @Excel(name = "测试人员")
    private String testers;

    /** 测试环境 */
    @Excel(name = "测试环境")
    private String testEnvironment;

    /** 测试目的 */
    @Excel(name = "测试目的")
    private String testPurpose;

    /** 硬件 */
    @Excel(name = "硬件")
    private String hardware;

    /** 软件 */
    @Excel(name = "软件")
    private String software;

    /** 测试工具 */
    @Excel(name = "测试工具")
    private String testTools;

    /** 结论与建议 */
    @Excel(name = "结论与建议")
    private String inConclusion;

    /** 测试结果 */
    @Excel(name = "测试结果")
    private String actualResults;

    /** 申请流程 */
    @Excel(name = "申请流程")
    private String flowKey;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String flowInsId;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billType;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTestCode(String testCode) 
    {
        this.testCode = testCode;
    }

    public String getTestCode() 
    {
        return testCode;
    }

    public void setBomId(Long bomId) 
    {
        this.bomId = bomId;
    }

    public Long getBomId() 
    {
        return bomId;
    }

    public void setBomName(String bomName) 
    {
        this.bomName = bomName;
    }

    public String getBomName() 
    {
        return bomName;
    }

    public void setBomCode(String bomCode) 
    {
        this.bomCode = bomCode;
    }

    public String getBomCode() 
    {
        return bomCode;
    }

    public void setTestType(String testType) 
    {
        this.testType = testType;
    }

    public String getTestType() 
    {
        return testType;
    }

    public void setTestDate(Date testDate) 
    {
        this.testDate = testDate;
    }

    public Date getTestDate() 
    {
        return testDate;
    }

    public void setTesters(String testers) 
    {
        this.testers = testers;
    }

    public String getTesters() 
    {
        return testers;
    }

    public void setTestEnvironment(String testEnvironment) 
    {
        this.testEnvironment = testEnvironment;
    }

    public String getTestEnvironment() 
    {
        return testEnvironment;
    }

    public void setTestPurpose(String testPurpose) 
    {
        this.testPurpose = testPurpose;
    }

    public String getTestPurpose() 
    {
        return testPurpose;
    }

    public void setHardware(String hardware) 
    {
        this.hardware = hardware;
    }

    public String getHardware() 
    {
        return hardware;
    }

    public void setSoftware(String software) 
    {
        this.software = software;
    }

    public String getSoftware() 
    {
        return software;
    }

    public void setTestTools(String testTools) 
    {
        this.testTools = testTools;
    }

    public String getTestTools() 
    {
        return testTools;
    }

    public void setInConclusion(String inConclusion) 
    {
        this.inConclusion = inConclusion;
    }

    public String getInConclusion() 
    {
        return inConclusion;
    }

    public void setActualResults(String actualResults) 
    {
        this.actualResults = actualResults;
    }

    public String getActualResults() 
    {
        return actualResults;
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
            .append("testCode", getTestCode())
            .append("bomId", getBomId())
            .append("bomName", getBomName())
            .append("bomCode", getBomCode())
            .append("testType", getTestType())
            .append("testDate", getTestDate())
            .append("testers", getTesters())
            .append("testEnvironment", getTestEnvironment())
            .append("testPurpose", getTestPurpose())
            .append("hardware", getHardware())
            .append("software", getSoftware())
            .append("testTools", getTestTools())
            .append("inConclusion", getInConclusion())
            .append("actualResults", getActualResults())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("flowKey", getFlowKey())
            .append("flowInsId", getFlowInsId())
            .append("billType", getBillType())
            .append("status", getStatus())
            .toString();
    }
}
