package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PjPhaseMapper;
import com.ruoyi.system.domain.PjPhase;
import com.ruoyi.system.service.IPjPhaseService;

/**
 * 项目阶段Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
@Service
public class PjPhaseServiceImpl implements IPjPhaseService 
{
    @Autowired
    private PjPhaseMapper pjPhaseMapper;

    /**
     * 查询项目阶段
     * 
     * @param id 项目阶段主键
     * @return 项目阶段
     */
    @Override
    public PjPhase selectPjPhaseById(Long id)
    {
        return pjPhaseMapper.selectPjPhaseById(id);
    }

    /**
     * 查询项目阶段列表
     * 
     * @param pjPhase 项目阶段
     * @return 项目阶段
     */
    @Override
    public List<PjPhase> selectPjPhaseList(PjPhase pjPhase)
    {
        return pjPhaseMapper.selectPjPhaseList(pjPhase);
    }

    /**
     * 新增项目阶段
     * 
     * @param pjPhase 项目阶段
     * @return 结果
     */
    @Override
    public int insertPjPhase(PjPhase pjPhase)
    {
        pjPhase.setCreateTime(DateUtils.getNowDate());
        return pjPhaseMapper.insertPjPhase(pjPhase);
    }

    /**
     * 修改项目阶段
     * 
     * @param pjPhase 项目阶段
     * @return 结果
     */
    @Override
    public int updatePjPhase(PjPhase pjPhase)
    {
        pjPhase.setUpdateTime(DateUtils.getNowDate());
        return pjPhaseMapper.updatePjPhase(pjPhase);
    }

    /**
     * 批量删除项目阶段
     * 
     * @param ids 需要删除的项目阶段主键
     * @return 结果
     */
    @Override
    public int deletePjPhaseByIds(Long[] ids)
    {
        return pjPhaseMapper.deletePjPhaseByIds(ids);
    }

    /**
     * 删除项目阶段信息
     * 
     * @param id 项目阶段主键
     * @return 结果
     */
    @Override
    public int deletePjPhaseById(Long id)
    {
        return pjPhaseMapper.deletePjPhaseById(id);
    }
}
