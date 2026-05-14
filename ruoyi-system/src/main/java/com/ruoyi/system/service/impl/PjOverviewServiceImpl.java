package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PjOverviewMapper;
import com.ruoyi.system.domain.PjOverview;
import com.ruoyi.system.service.IPjOverviewService;

/**
 * 项目概述Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
@Service
public class PjOverviewServiceImpl implements IPjOverviewService 
{
    @Autowired
    private PjOverviewMapper pjOverviewMapper;

    /**
     * 查询项目概述
     * 
     * @param id 项目概述主键
     * @return 项目概述
     */
    @Override
    public PjOverview selectPjOverviewById(Long id)
    {
        return pjOverviewMapper.selectPjOverviewById(id);
    }

    /**
     * 查询项目概述列表
     * 
     * @param pjOverview 项目概述
     * @return 项目概述
     */
    @Override
    public List<PjOverview> selectPjOverviewList(PjOverview pjOverview)
    {
        return pjOverviewMapper.selectPjOverviewList(pjOverview);
    }

    /**
     * 新增项目概述
     * 
     * @param pjOverview 项目概述
     * @return 结果
     */
    @Override
    public int insertPjOverview(PjOverview pjOverview)
    {
        pjOverview.setCreateTime(DateUtils.getNowDate());
        return pjOverviewMapper.insertPjOverview(pjOverview);
    }

    /**
     * 修改项目概述
     * 
     * @param pjOverview 项目概述
     * @return 结果
     */
    @Override
    public int updatePjOverview(PjOverview pjOverview)
    {
        pjOverview.setUpdateTime(DateUtils.getNowDate());
        return pjOverviewMapper.updatePjOverview(pjOverview);
    }

    /**
     * 批量删除项目概述
     * 
     * @param ids 需要删除的项目概述主键
     * @return 结果
     */
    @Override
    public int deletePjOverviewByIds(Long[] ids)
    {
        return pjOverviewMapper.deletePjOverviewByIds(ids);
    }

    /**
     * 删除项目概述信息
     * 
     * @param id 项目概述主键
     * @return 结果
     */
    @Override
    public int deletePjOverviewById(Long id)
    {
        return pjOverviewMapper.deletePjOverviewById(id);
    }
}
