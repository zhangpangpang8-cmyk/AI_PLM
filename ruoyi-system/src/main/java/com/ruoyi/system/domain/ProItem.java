package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料管理（仅存储激活版本）对象 pro_item
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
public class ProItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 物料编码（唯一标识） */
    @Excel(name = "物料编码", readConverterExp = "唯=一标识")
    private String itemCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String itemName;

    /** 物料类型ID */
    @Excel(name = "物料类型ID")
    private Long itemTypeId;

    /** 物料类型编码 */
    @Excel(name = "物料类型编码")
    private String itemTypeCode;

    /** 物料类型名称 */
    @Excel(name = "物料类型名称")
    private String itemTypeName;

    /** 物料版本号 */
    @Excel(name = "物料版本号")
    private String itemVersion;

    /** 物料分类ids */
    @Excel(name = "物料分类ids")
    private String materialClassifyIds;

    /** 物料分类名称 */
    @Excel(name = "物料分类名称")
    private String materialClassifyName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String enable;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long vendorId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String vendorName;

    /** 材质 */
    @Excel(name = "材质")
    private String material;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 描述 */
    @Excel(name = "描述")
    private String Info;

    /** 技术参数数组 */
    @Excel(name = "技术参数数组")
    private String parametersValues;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String url;

    /** 是否最新版本（0=否，1=是） */
    @Excel(name = "是否最新版本", readConverterExp = "0==否，1=是")
    private String latestVersion;

    /** 发布状态 */
    @Excel(name = "发布状态")
    private String publishStatus;

    /** 备注 */
    @Excel(name = "备注")
    private String remake;

    /** 申请流程 */
    @Excel(name = "申请流程")
    private String flowKey;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String flowInsId;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billType;

    /** 能否作为Bom的父件 */
    @Excel(name = "能否作为Bom的父件")
    private String usedAsParent;

    /** 能否进行变更（0=不允许，1=允许） */
    @Excel(name = "能否进行变更", readConverterExp = "0==不允许，1=允许")
    private String allowChange;

    /** 物料同步UUID */
    @Excel(name = "物料同步UUID")
    private String itemUuid;

    /** 物料同步MESID */
    @Excel(name = "物料同步MESID")
    private String mesSyncId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setItemTypeId(Long itemTypeId) 
    {
        this.itemTypeId = itemTypeId;
    }

    public Long getItemTypeId() 
    {
        return itemTypeId;
    }

    public void setItemTypeCode(String itemTypeCode) 
    {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeCode() 
    {
        return itemTypeCode;
    }

    public void setItemTypeName(String itemTypeName) 
    {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeName() 
    {
        return itemTypeName;
    }

    public void setItemVersion(String itemVersion) 
    {
        this.itemVersion = itemVersion;
    }

    public String getItemVersion() 
    {
        return itemVersion;
    }

    public void setMaterialClassifyIds(String materialClassifyIds) 
    {
        this.materialClassifyIds = materialClassifyIds;
    }

    public String getMaterialClassifyIds() 
    {
        return materialClassifyIds;
    }

    public void setMaterialClassifyName(String materialClassifyName) 
    {
        this.materialClassifyName = materialClassifyName;
    }

    public String getMaterialClassifyName() 
    {
        return materialClassifyName;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setEnable(String enable) 
    {
        this.enable = enable;
    }

    public String getEnable() 
    {
        return enable;
    }

    public void setVendorId(Long vendorId) 
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId() 
    {
        return vendorId;
    }

    public void setVendorName(String vendorName) 
    {
        this.vendorName = vendorName;
    }

    public String getVendorName() 
    {
        return vendorName;
    }

    public void setMaterial(String material) 
    {
        this.material = material;
    }

    public String getMaterial() 
    {
        return material;
    }

    public void setColor(String color) 
    {
        this.color = color;
    }

    public String getColor() 
    {
        return color;
    }

    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }

    public void setInfo(String Info) 
    {
        this.Info = Info;
    }

    public String getInfo() 
    {
        return Info;
    }

    public void setParametersValues(String parametersValues) 
    {
        this.parametersValues = parametersValues;
    }

    public String getParametersValues() 
    {
        return parametersValues;
    }

    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    public void setLatestVersion(String latestVersion) 
    {
        this.latestVersion = latestVersion;
    }

    public String getLatestVersion() 
    {
        return latestVersion;
    }

    public void setPublishStatus(String publishStatus) 
    {
        this.publishStatus = publishStatus;
    }

    public String getPublishStatus() 
    {
        return publishStatus;
    }

    public void setRemake(String remake) 
    {
        this.remake = remake;
    }

    public String getRemake() 
    {
        return remake;
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

    public void setUsedAsParent(String usedAsParent) 
    {
        this.usedAsParent = usedAsParent;
    }

    public String getUsedAsParent() 
    {
        return usedAsParent;
    }

    public void setAllowChange(String allowChange) 
    {
        this.allowChange = allowChange;
    }

    public String getAllowChange() 
    {
        return allowChange;
    }

    public void setItemUuid(String itemUuid) 
    {
        this.itemUuid = itemUuid;
    }

    public String getItemUuid() 
    {
        return itemUuid;
    }

    public void setMesSyncId(String mesSyncId) 
    {
        this.mesSyncId = mesSyncId;
    }

    public String getMesSyncId() 
    {
        return mesSyncId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeCode", getItemTypeCode())
            .append("itemTypeName", getItemTypeName())
            .append("itemVersion", getItemVersion())
            .append("materialClassifyIds", getMaterialClassifyIds())
            .append("materialClassifyName", getMaterialClassifyName())
            .append("specification", getSpecification())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .append("status", getStatus())
            .append("enable", getEnable())
            .append("vendorId", getVendorId())
            .append("vendorName", getVendorName())
            .append("material", getMaterial())
            .append("color", getColor())
            .append("weight", getWeight())
            .append("Info", getInfo())
            .append("parametersValues", getParametersValues())
            .append("url", getUrl())
            .append("latestVersion", getLatestVersion())
            .append("publishStatus", getPublishStatus())
            .append("remake", getRemake())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("flowKey", getFlowKey())
            .append("flowInsId", getFlowInsId())
            .append("billType", getBillType())
            .append("usedAsParent", getUsedAsParent())
            .append("allowChange", getAllowChange())
            .append("itemUuid", getItemUuid())
            .append("mesSyncId", getMesSyncId())
            .toString();
    }
}
