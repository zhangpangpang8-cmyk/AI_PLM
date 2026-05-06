package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmTechMapper;
import com.ruoyi.system.domain.DmTech;
import com.ruoyi.system.service.IDmTechService;

/**
 * 技术文档Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class DmTechServiceImpl implements IDmTechService 
{
    @Autowired
    private DmTechMapper dmTechMapper;

    /**
     * 查询技术文档
     * 
     * @param id 技术文档主键
     * @return 技术文档
     */
    @Override
    public DmTech selectDmTechById(Long id)
    {
        return dmTechMapper.selectDmTechById(id);
    }

    /**
     * 查询技术文档列表
     * 
     * @param dmTech 技术文档
     * @return 技术文档
     */
    @Override
    public List<DmTech> selectDmTechList(DmTech dmTech)
    {
        return dmTechMapper.selectDmTechList(dmTech);
    }

    /**
     * 新增技术文档
     * 
     * @param dmTech 技术文档
     * @return 结果
     */
    @Override
    public int insertDmTech(DmTech dmTech)
    {
        dmTech.setCreateTime(DateUtils.getNowDate());
        return dmTechMapper.insertDmTech(dmTech);
    }

    /**
     * 修改技术文档
     * 
     * @param dmTech 技术文档
     * @return 结果
     */
    @Override
    public int updateDmTech(DmTech dmTech)
    {
        dmTech.setUpdateTime(DateUtils.getNowDate());
        return dmTechMapper.updateDmTech(dmTech);
    }

    /**
     * 批量删除技术文档
     * 
     * @param ids 需要删除的技术文档主键
     * @return 结果
     */
    @Override
    public int deleteDmTechByIds(Long[] ids)
    {
        return dmTechMapper.deleteDmTechByIds(ids);
    }

    /**
     * 删除技术文档信息
     * 
     * @param id 技术文档主键
     * @return 结果
     */
    @Override
    public int deleteDmTechById(Long id)
    {
        return dmTechMapper.deleteDmTechById(id);
    }
}
