package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * BOM版本对象 pro_item_bom
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
public class ProItemBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** BOM编码 */
    @Excel(name = "BOM编码")
    private String bomCode;

    /** BOM名称 */
    @Excel(name = "BOM名称")
    private String bomName;

    /** BOM版本 */
    @Excel(name = "BOM版本")
    private String bomVersion;

    /** 是否为基础BOM，默认为0 */
    @Excel(name = "是否为基础BOM，默认为0")
    private String basicsBom;

    /** 物料id */
    @Excel(name = "物料id")
    private Long itemId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String itemName;

    /** 物料版本 */
    @Excel(name = "物料版本")
    private String itemVersion;

    /** 层级 */
    @Excel(name = "层级")
    private Long hierarchy;

    /** 父节点物料编码 */
    @Excel(name = "父节点物料编码")
    private String parentNode;

    /** 产品类型id */
    @Excel(name = "产品类型id")
    private Long productId;

    /** 产品类型编码 */
    @Excel(name = "产品类型编码")
    private String productCode;

    /** 产品类型名称 */
    @Excel(name = "产品类型名称")
    private String productName;

    /** 节点路径 */
    @Excel(name = "节点路径")
    private String nodePath;

    /** 序号 */
    @Excel(name = "序号")
    private Long orderNum;

    /** 用量 */
    @Excel(name = "用量")
    private BigDecimal dosage;

    /** 是否为最新版本 */
    @Excel(name = "是否为最新版本")
    private String latestVersion;

    /** 申请流程 */
    @Excel(name = "申请流程")
    private String flowKey;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String flowInsId;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billtype;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 版本简述 */
    @Excel(name = "版本简述")
    private String versionBriefly;

    /** 版本详情 */
    @Excel(name = "版本详情")
    private String versionDetails;

    /** 启用状态 */
    @Excel(name = "启用状态")
    private String enable;

    /** BOM的uuid */
    @Excel(name = "BOM的uuid")
    private String bomUuid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBomCode(String bomCode) 
    {
        this.bomCode = bomCode;
    }

    public String getBomCode() 
    {
        return bomCode;
    }

    public void setBomName(String bomName) 
    {
        this.bomName = bomName;
    }

    public String getBomName() 
    {
        return bomName;
    }

    public void setBomVersion(String bomVersion) 
    {
        this.bomVersion = bomVersion;
    }

    public String getBomVersion() 
    {
        return bomVersion;
    }

    public void setBasicsBom(String basicsBom) 
    {
        this.basicsBom = basicsBom;
    }

    public String getBasicsBom() 
    {
        return basicsBom;
    }

    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }

    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }

    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }

    public void setItemVersion(String itemVersion) 
    {
        this.itemVersion = itemVersion;
    }

    public String getItemVersion() 
    {
        return itemVersion;
    }

    public void setHierarchy(Long hierarchy) 
    {
        this.hierarchy = hierarchy;
    }

    public Long getHierarchy() 
    {
        return hierarchy;
    }

    public void setParentNode(String parentNode) 
    {
        this.parentNode = parentNode;
    }

    public String getParentNode() 
    {
        return parentNode;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setNodePath(String nodePath) 
    {
        this.nodePath = nodePath;
    }

    public String getNodePath() 
    {
        return nodePath;
    }

    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }

    public void setDosage(BigDecimal dosage) 
    {
        this.dosage = dosage;
    }

    public BigDecimal getDosage() 
    {
        return dosage;
    }

    public void setLatestVersion(String latestVersion) 
    {
        this.latestVersion = latestVersion;
    }

    public String getLatestVersion() 
    {
        return latestVersion;
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

    public void setBilltype(String billtype) 
    {
        this.billtype = billtype;
    }

    public String getBilltype() 
    {
        return billtype;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setVersionBriefly(String versionBriefly) 
    {
        this.versionBriefly = versionBriefly;
    }

    public String getVersionBriefly() 
    {
        return versionBriefly;
    }

    public void setVersionDetails(String versionDetails) 
    {
        this.versionDetails = versionDetails;
    }

    public String getVersionDetails() 
    {
        return versionDetails;
    }

    public void setEnable(String enable) 
    {
        this.enable = enable;
    }

    public String getEnable() 
    {
        return enable;
    }

    public void setBomUuid(String bomUuid) 
    {
        this.bomUuid = bomUuid;
    }

    public String getBomUuid() 
    {
        return bomUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bomCode", getBomCode())
            .append("bomName", getBomName())
            .append("bomVersion", getBomVersion())
            .append("basicsBom", getBasicsBom())
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("itemVersion", getItemVersion())
            .append("hierarchy", getHierarchy())
            .append("parentNode", getParentNode())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("nodePath", getNodePath())
            .append("orderNum", getOrderNum())
            .append("dosage", getDosage())
            .append("latestVersion", getLatestVersion())
            .append("flowKey", getFlowKey())
            .append("flowInsId", getFlowInsId())
            .append("billtype", getBilltype())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("versionBriefly", getVersionBriefly())
            .append("versionDetails", getVersionDetails())
            .append("enable", getEnable())
            .append("bomUuid", getBomUuid())
            .toString();
    }
}
