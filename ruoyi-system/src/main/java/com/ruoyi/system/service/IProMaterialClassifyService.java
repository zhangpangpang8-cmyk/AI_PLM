package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ProMaterialClassify;

/**
 * 物料分类（支持多级分类）Service接口
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
public interface IProMaterialClassifyService 
{
    /**
     * 查询物料分类（支持多级分类）
     * 
     * @param id 物料分类（支持多级分类）主键
     * @return 物料分类（支持多级分类）
     */
    public ProMaterialClassify selectProMaterialClassifyById(Long id);

    /**
     * 查询物料分类（支持多级分类）列表
     * 
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 物料分类（支持多级分类）集合
     */
    public List<ProMaterialClassify> selectProMaterialClassifyList(ProMaterialClassify proMaterialClassify);

    /**
     * 新增物料分类（支持多级分类）
     * 
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 结果
     */
    public int insertProMaterialClassify(ProMaterialClassify proMaterialClassify);

    /**
     * 修改物料分类（支持多级分类）
     * 
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 结果
     */
    public int updateProMaterialClassify(ProMaterialClassify proMaterialClassify);

    /**
     * 批量删除物料分类（支持多级分类）
     * 
     * @param ids 需要删除的物料分类（支持多级分类）主键集合
     * @return 结果
     */
    public int deleteProMaterialClassifyByIds(Long[] ids);

    /**
     * 删除物料分类（支持多级分类）信息
     * 
     * @param id 物料分类（支持多级分类）主键
     * @return 结果
     */
    public int deleteProMaterialClassifyById(Long id);
}
