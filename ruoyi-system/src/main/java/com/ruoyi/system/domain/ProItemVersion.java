package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料版本历史对象 pro_item_version
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
public class ProItemVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联物料ID */
    private Long itemId;

    /** 物料编码 */
    @Excel(name = "物料编码")
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

    /** 发布状态 */
    @Excel(name = "发布状态")
    private String publishStatus;

    /** 备注 */
    @Excel(name = "备注")
    private String remake;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("itemVersion", getItemVersion())
            .append("specification", getSpecification())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
