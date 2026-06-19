package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PjTaskLogMapper;
import com.ruoyi.system.domain.PjTaskLog;
import com.ruoyi.system.service.IPjTaskLogService;

/**
 * 任务进度日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
@Service
public class PjTaskLogServiceImpl implements IPjTaskLogService 
{
    @Autowired
    private PjTaskLogMapper pjTaskLogMapper;

    /**
     * 查询任务进度日志
     * 
     * @param id 任务进度日志主键
     * @return 任务进度日志
     */
    @Override
    public PjTaskLog selectPjTaskLogById(Long id)
    {
        return pjTaskLogMapper.selectPjTaskLogById(id);
    }

    /**
     * 查询任务进度日志列表
     * 
     * @param pjTaskLog 任务进度日志
     * @return 任务进度日志
     */
    @Override
    public List<PjTaskLog> selectPjTaskLogList(PjTaskLog pjTaskLog)
    {
        return pjTaskLogMapper.selectPjTaskLogList(pjTaskLog);
    }

    /**
     * 新增任务进度日志
     * 
     * @param pjTaskLog 任务进度日志
     * @return 结果
     */
    @Override
    public int insertPjTaskLog(PjTaskLog pjTaskLog)
    {
        pjTaskLog.setCreateTime(DateUtils.getNowDate());
        return pjTaskLogMapper.insertPjTaskLog(pjTaskLog);
    }

    /**
     * 修改任务进度日志
     * 
     * @param pjTaskLog 任务进度日志
     * @return 结果
     */
    @Override
    public int updatePjTaskLog(PjTaskLog pjTaskLog)
    {
        pjTaskLog.setUpdateTime(DateUtils.getNowDate());
        return pjTaskLogMapper.updatePjTaskLog(pjTaskLog);
    }

    /**
     * 批量删除任务进度日志
     * 
     * @param ids 需要删除的任务进度日志主键
     * @return 结果
     */
    @Override
    public int deletePjTaskLogByIds(Long[] ids)
    {
        return pjTaskLogMapper.deletePjTaskLogByIds(ids);
    }

    /**
     * 删除任务进度日志信息
     * 
     * @param id 任务进度日志主键
     * @return 结果
     */
    @Override
    public int deletePjTaskLogById(Long id)
    {
        return pjTaskLogMapper.deletePjTaskLogById(id);
    }
}
