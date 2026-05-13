package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ProItemBom;

/**
 * BOM版本Service接口
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
public interface IProItemBomService 
{
    /**
     * 查询BOM版本
     * 
     * @param id BOM版本主键
     * @return BOM版本
     */
    public ProItemBom selectProItemBomById(Long id);

    /**
     * 查询BOM版本列表
     * 
     * @param proItemBom BOM版本
     * @return BOM版本集合
     */
    public List<ProItemBom> selectProItemBomList(ProItemBom proItemBom);

    /**
     * 新增BOM版本
     * 
     * @param proItemBom BOM版本
     * @return 结果
     */
    public int insertProItemBom(ProItemBom proItemBom);

    /**
     * 修改BOM版本
     * 
     * @param proItemBom BOM版本
     * @return 结果
     */
    public int updateProItemBom(ProItemBom proItemBom);

    /**
     * 批量删除BOM版本
     * 
     * @param ids 需要删除的BOM版本主键集合
     * @return 结果
     */
    public int deleteProItemBomByIds(Long[] ids);

    /**
     * 删除BOM版本信息
     * 
     * @param id BOM版本主键
     * @return 结果
     */
    public int deleteProItemBomById(Long id);
}
