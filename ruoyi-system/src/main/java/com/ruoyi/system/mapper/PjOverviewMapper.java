package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PjOverview;

/**
 * 项目概述Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
public interface PjOverviewMapper 
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
     * 删除项目概述
     * 
     * @param id 项目概述主键
     * @return 结果
     */
    public int deletePjOverviewById(Long id);

    /**
     * 批量删除项目概述
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePjOverviewByIds(Long[] ids);
}
