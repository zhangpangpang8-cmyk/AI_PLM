package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.PjOverview;

/**
 * 项目概述Service接口
 * 
 * @author ruoyi
 * @date 2026-05-28
 */
public interface IPjOverviewService 
{
    /**
     * 查询项目概述
     * 
     * @param id 项目概述主键
     * @return 项目概述
     */
    public PjOverview selectPjOverviewById(Long id);

    /**
     * 查询项目概述列表
     * 
     * @param pjOverview 项目概述
     * @return 项目概述集合
     */
    public List<PjOverview> selectPjOverviewList(PjOverview pjOverview);

    /**
     * 新增项目概述
     * 
     * @param pjOverview 项目概述
     * @return 结果
     */
    public int insertPjOverview(PjOverview pjOverview);

    /**
     * 修改项目概述
     * 
     * @param pjOverview 项目概述
     * @return 结果
     */
    public int updatePjOverview(PjOverview pjOverview);

    /**
     * 批量删除项目概述
     * 
     * @param ids 需要删除的项目概述主键集合
     * @return 结果
     */
    public int deletePjOverviewByIds(Long[] ids);


    /**
     * 删除项目概述信息
     * 
     * @param id 项目概述主键
     * @return 结果
     */
    public int deletePjOverviewById(Long id);

    /**
     * 查询项目统计数据
     *
     * @return 项目统计数据
     */
    public Map<String, Object> selectProjectStatistics();

}
