package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WfProcessTask;

/**
 * 流程任务Mapper接口
 *
 * @author ruoyi
 */
public interface WfProcessTaskMapper
{
    /**
     * 查询流程任务
     *
     * @param id 流程任务主键
     * @return 流程任务
     */
    public WfProcessTask selectWfProcessTaskById(Long id);

    /**
     * 查询流程任务列表
     *
     * @param wfProcessTask 流程任务
     * @return 流程任务集合
     */
    public List<WfProcessTask> selectWfProcessTaskList(WfProcessTask wfProcessTask);

    /**
     * 查询用户的待办任务
     *
     * @param userId 用户ID
     * @return 流程任务集合
     */
    public List<WfProcessTask> selectPendingTasksByUserId(String userId);

    /**
     * 查询用户的已办任务
     *
     * @param userId 用户ID
     * @return 流程任务集合
     */
    public List<WfProcessTask> selectCompletedTasksByUserId(String userId);

    /**
     * 新增流程任务
     *
     * @param wfProcessTask 流程任务
     * @return 结果
     */
    public int insertWfProcessTask(WfProcessTask wfProcessTask);

    /**
     * 修改流程任务
     *
     * @param wfProcessTask 流程任务
     * @return 结果
     */
    public int updateWfProcessTask(WfProcessTask wfProcessTask);

    /**
     * 删除流程任务
     *
     * @param id 流程任务主键
     * @return 结果
     */
    public int deleteWfProcessTaskById(Long id);
}
