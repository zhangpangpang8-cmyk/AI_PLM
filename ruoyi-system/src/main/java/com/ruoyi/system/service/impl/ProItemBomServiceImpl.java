package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProItemBomMapper;
import com.ruoyi.system.domain.ProItemBom;
import com.ruoyi.system.service.IProItemBomService;

/**
 * BOM版本Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
@Service
public class ProItemBomServiceImpl implements IProItemBomService 
{
    @Autowired
    private ProItemBomMapper proItemBomMapper;

    /**
     * 查询BOM版本
     * 
     * @param id BOM版本主键
     * @return BOM版本
     */
    @Override
    public ProItemBom selectProItemBomById(Long id)
    {
        return proItemBomMapper.selectProItemBomById(id);
    }

    /**
     * 查询BOM版本列表
     * 
     * @param proItemBom BOM版本
     * @return BOM版本
     */
    @Override
    public List<ProItemBom> selectProItemBomList(ProItemBom proItemBom)
    {
        return proItemBomMapper.selectProItemBomList(proItemBom);
    }

    /**
     * 新增BOM版本
     * 
     * @param proItemBom BOM版本
     * @return 结果
     */
    @Override
    public int insertProItemBom(ProItemBom proItemBom)
    {
        proItemBom.setCreateTime(DateUtils.getNowDate());
        return proItemBomMapper.insertProItemBom(proItemBom);
    }

    /**
     * 修改BOM版本
     * 
     * @param proItemBom BOM版本
     * @return 结果
     */
    @Override
    public int updateProItemBom(ProItemBom proItemBom)
    {
        proItemBom.setUpdateTime(DateUtils.getNowDate());
        return proItemBomMapper.updateProItemBom(proItemBom);
    }

    /**
     * 批量删除BOM版本
     * 
     * @param ids 需要删除的BOM版本主键
     * @return 结果
     */
    @Override
    public int deleteProItemBomByIds(Long[] ids)
    {
        return proItemBomMapper.deleteProItemBomByIds(ids);
    }

    /**
     * 删除BOM版本信息
     * 
     * @param id BOM版本主键
     * @return 结果
     */
    @Override
    public int deleteProItemBomById(Long id)
    {
        return proItemBomMapper.deleteProItemBomById(id);
    }
}
