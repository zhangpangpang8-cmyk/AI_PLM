package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProMaterialClassifyMapper;
import com.ruoyi.system.domain.ProMaterialClassify;
import com.ruoyi.system.service.IProMaterialClassifyService;

/**
 * 物料分类（支持多级分类）Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
@Service
public class ProMaterialClassifyServiceImpl implements IProMaterialClassifyService 
{
    @Autowired
    private ProMaterialClassifyMapper proMaterialClassifyMapper;

    /**
     * 查询物料分类（支持多级分类）
     * 
     * @param id 物料分类（支持多级分类）主键
     * @return 物料分类（支持多级分类）
     */
    @Override
    public ProMaterialClassify selectProMaterialClassifyById(Long id)
    {
        return proMaterialClassifyMapper.selectProMaterialClassifyById(id);
    }

    /**
     * 查询物料分类（支持多级分类）列表
     * 
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 物料分类（支持多级分类）
     */
    @Override
    public List<ProMaterialClassify> selectProMaterialClassifyList(ProMaterialClassify proMaterialClassify)
    {
        return proMaterialClassifyMapper.selectProMaterialClassifyList(proMaterialClassify);
    }

    /**
     * 新增物料分类（支持多级分类）
     * 
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 结果
     */
    @Override
    public int insertProMaterialClassify(ProMaterialClassify proMaterialClassify)
    {
        proMaterialClassify.setCreateTime(DateUtils.getNowDate());
        return proMaterialClassifyMapper.insertProMaterialClassify(proMaterialClassify);
    }

    /**
     * 修改物料分类（支持多级分类）
     * 
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 结果
     */
    @Override
    public int updateProMaterialClassify(ProMaterialClassify proMaterialClassify)
    {
        proMaterialClassify.setUpdateTime(DateUtils.getNowDate());
        return proMaterialClassifyMapper.updateProMaterialClassify(proMaterialClassify);
    }

    /**
     * 批量删除物料分类（支持多级分类）
     * 
     * @param ids 需要删除的物料分类（支持多级分类）主键
     * @return 结果
     */
    @Override
    public int deleteProMaterialClassifyByIds(Long[] ids)
    {
        return proMaterialClassifyMapper.deleteProMaterialClassifyByIds(ids);
    }

    /**
     * 删除物料分类（支持多级分类）信息
     * 
     * @param id 物料分类（支持多级分类）主键
     * @return 结果
     */
    @Override
    public int deleteProMaterialClassifyById(Long id)
    {
        return proMaterialClassifyMapper.deleteProMaterialClassifyById(id);
    }
}
