package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.WfProcessTask;

/**
 * 流程任务Service接口
 *
 * @author ruoyi
 */
public interface IWfProcessTaskService
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
     * 审批任务
     *
     * @param taskId 任务ID
     * @param approved 是否通过
     * @param opinion 审批意见
     * @param approver 审批人
     * @return 结果
     */
    public int approveTask(Long taskId, boolean approved, String opinion, String approver);
}
