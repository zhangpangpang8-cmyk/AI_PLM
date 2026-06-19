package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PjTaskLog;

/**
 * 任务进度日志Service接口
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
public interface IPjTaskLogService 
{
    /**
     * 查询任务进度日志
     * 
     * @param id 任务进度日志主键
     * @return 任务进度日志
     */
    public PjTaskLog selectPjTaskLogById(Long id);

    /**
     * 查询任务进度日志列表
     * 
     * @param pjTaskLog 任务进度日志
     * @return 任务进度日志集合
     */
    public List<PjTaskLog> selectPjTaskLogList(PjTaskLog pjTaskLog);

    /**
     * 新增任务进度日志
     * 
     * @param pjTaskLog 任务进度日志
     * @return 结果
     */
    public int insertPjTaskLog(PjTaskLog pjTaskLog);

    /**
     * 修改任务进度日志
     * 
     * @param pjTaskLog 任务进度日志
     * @return 结果
     */
    public int updatePjTaskLog(PjTaskLog pjTaskLog);

    /**
     * 批量删除任务进度日志
     * 
     * @param ids 需要删除的任务进度日志主键集合
     * @return 结果
     */
    public int deletePjTaskLogByIds(Long[] ids);

    /**
     * 删除任务进度日志信息
     * 
     * @param id 任务进度日志主键
     * @return 结果
     */
    public int deletePjTaskLogById(Long id);
}
