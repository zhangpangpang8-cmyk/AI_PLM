package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料分类（支持多级分类）对象 pro_material_classify
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
public class ProMaterialClassify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 物料类型编码（用于生成物料编码前缀） */
    @Excel(name = "物料类型编码", readConverterExp = "用=于生成物料编码前缀")
    private String materialClassifyCode;

    /** 物料类型名称 */
    @Excel(name = "物料类型名称")
    private String materialClassifyName;

    /** 父类型ID（顶级分类设为0） */
    @Excel(name = "父类型ID", readConverterExp = "顶=级分类设为0")
    private Long parentClassifyId;

    /** 所有层级父节点ID，用逗号分隔（如：0,1,2） */
    @Excel(name = "所有层级父节点ID，用逗号分隔", readConverterExp = "如=：0,1,2")
    private String ancestors;

    /** 所有层级父节点名称用/分隔 */
    @Excel(name = "所有层级父节点名称用/分隔")
    private String ancestorsName;

    /** 显示顺序（越小越靠前） */
    @Excel(name = "显示顺序", readConverterExp = "越=小越靠前")
    private Long orderNum;

    /** 是否启用（N=禁用，Y=启用） */
    @Excel(name = "是否启用", readConverterExp = "N==禁用，Y=启用")
    private String enable;

    /** MES同步id */
    @Excel(name = "MES同步id")
    private String mesSyncId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMaterialClassifyCode(String materialClassifyCode) 
    {
        this.materialClassifyCode = materialClassifyCode;
    }

    public String getMaterialClassifyCode() 
    {
        return materialClassifyCode;
    }

    public void setMaterialClassifyName(String materialClassifyName) 
    {
        this.materialClassifyName = materialClassifyName;
    }

    public String getMaterialClassifyName() 
    {
        return materialClassifyName;
    }

    public void setParentClassifyId(Long parentClassifyId) 
    {
        this.parentClassifyId = parentClassifyId;
    }

    public Long getParentClassifyId() 
    {
        return parentClassifyId;
    }

    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }

    public void setAncestorsName(String ancestorsName) 
    {
        this.ancestorsName = ancestorsName;
    }

    public String getAncestorsName() 
    {
        return ancestorsName;
    }

    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }

    public void setEnable(String enable) 
    {
        this.enable = enable;
    }

    public String getEnable() 
    {
        return enable;
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
            .append("materialClassifyCode", getMaterialClassifyCode())
            .append("materialClassifyName", getMaterialClassifyName())
            .append("parentClassifyId", getParentClassifyId())
            .append("ancestors", getAncestors())
            .append("ancestorsName", getAncestorsName())
            .append("orderNum", getOrderNum())
            .append("enable", getEnable())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("mesSyncId", getMesSyncId())
            .toString();
    }
}
