package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PjPhase;

/**
 * 项目阶段Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
public interface PjPhaseMapper 
{
    /**
     * 查询项目阶段
     * 
     * @param id 项目阶段主键
     * @return 项目阶段
     */
    public PjPhase selectPjPhaseById(Long id);

    /**
     * 查询项目阶段列表
     * 
     * @param pjPhase 项目阶段
     * @return 项目阶段集合
     */
    public List<PjPhase> selectPjPhaseList(PjPhase pjPhase);

    /**
     * 新增项目阶段
     * 
     * @param pjPhase 项目阶段
     * @return 结果
     */
    public int insertPjPhase(PjPhase pjPhase);

    /**
     * 修改项目阶段
     * 
     * @param pjPhase 项目阶段
     * @return 结果
     */
    public int updatePjPhase(PjPhase pjPhase);

    /**
     * 删除项目阶段
     * 
     * @param id 项目阶段主键
     * @return 结果
     */
    public int deletePjPhaseById(Long id);

    /**
     * 批量删除项目阶段
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePjPhaseByIds(Long[] ids);
}
