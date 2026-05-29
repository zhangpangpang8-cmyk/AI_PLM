package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.DmTech;
import com.ruoyi.system.domain.PjOverview;
import com.ruoyi.system.service.IDmTechService;
import com.ruoyi.system.service.IPjOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.WfProcessTaskMapper;
import com.ruoyi.system.mapper.WfProcessInstanceMapper;
import com.ruoyi.system.domain.WfProcessTask;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.service.IWfProcessTaskService;

/**
 * 流程任务Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class WfProcessTaskServiceImpl implements IWfProcessTaskService
{
    @Autowired
    private WfProcessTaskMapper wfProcessTaskMapper;

    @Autowired
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Autowired
    private IDmTechService dmTechService;

    @Autowired
    private IPjOverviewService pjOverviewService;
    /**
     * 查询流程任务
     *
     * @param id 流程任务主键
     * @return 流程任务
     */
    @Override
    public WfProcessTask selectWfProcessTaskById(Long id)
    {
        return wfProcessTaskMapper.selectWfProcessTaskById(id);
    }

    /**
     * 查询流程任务列表
     *
     * @param wfProcessTask 流程任务
     * @return 流程任务
     */
    @Override
    public List<WfProcessTask> selectWfProcessTaskList(WfProcessTask wfProcessTask)
    {
        return wfProcessTaskMapper.selectWfProcessTaskList(wfProcessTask);
    }

    /**
     * 查询用户的待办任务
     *
     * @param userId 用户ID
     * @return 流程任务
     */
    @Override
    public List<WfProcessTask> selectPendingTasksByUserId(String userId)
    {
        return wfProcessTaskMapper.selectPendingTasksByUserId(userId);
    }

    /**
     * 查询用户的已办任务
     *
     * @param userId 用户ID
     * @return 流程任务
     */
    @Override
    public List<WfProcessTask> selectCompletedTasksByUserId(String userId)
    {
        return wfProcessTaskMapper.selectCompletedTasksByUserId(userId);
    }

    /**
     * 审批任务
     *
     * @param taskId 任务ID
     * @param approved 是否通过
     * @param opinion 审批意见
     * @param approver 审批人
     * @return 结果
     */
    @Override
    @Transactional
    public int approveTask(Long taskId, boolean approved, String opinion, String approver)
    {
        WfProcessTask task = wfProcessTaskMapper.selectWfProcessTaskById(taskId);
        if (task == null) {
            return 0;
        }

        task.setTaskStatus(approved ? "approved" : "rejected");
        task.setApprovalOpinion(opinion);
        task.setApprovalTime(new Date());
        task.setEndTime(new Date());

        int result = wfProcessTaskMapper.updateWfProcessTask(task);

        WfProcessInstance instance = wfProcessInstanceMapper.selectWfProcessInstanceById(task.getInstanceId());
        if (instance != null) {
            if (approved) {
                instance.setCurrentNode("已完成");
                instance.setStatus("approved");
                instance.setEndTime(new Date());

                long durationSeconds = (instance.getEndTime().getTime() - instance.getStartTime().getTime()) / 1000;
                if (durationSeconds > 0) {
                    instance.setDuration(durationSeconds);
                } else {
                    instance.setDuration(60L); // 默认设置为60秒，避免负数或0的情况
                }

                wfProcessInstanceMapper.updateWfProcessInstance(instance);

                updateBusinessStatus(instance.getBusinessType(), instance.getBusinessId(), true);
            } else {
                instance.setStatus("rejected");
                instance.setEndTime(new Date());

                long durationSeconds = (instance.getEndTime().getTime() - instance.getStartTime().getTime()) / 1000;
                instance.setDuration(durationSeconds);

                wfProcessInstanceMapper.updateWfProcessInstance(instance);

                updateBusinessStatus(instance.getBusinessType(), instance.getBusinessId(), false);
            }
        }

        return result;
    }


    private void updateBusinessStatus(String businessType, Long businessId, boolean approved) {
        System.out.println("=== 更新业务状态 ===");
        System.out.println("businessType: " + businessType);
        System.out.println("businessId: " + businessId);
        System.out.println("approved: " + approved);

        if (businessId == null || businessType == null) {
            System.out.println("businessType 或 businessId 为空，跳过更新");
            return;
        }

        try {
            if ("tech_doc".equals(businessType)) {
                DmTech dmTech = dmTechService.selectDmTechById(businessId);
                if (dmTech != null) {
                    System.out.println("找到技术文档: id=" + dmTech.getId() + ", techCode=" + dmTech.getTechCode());
                    System.out.println("更新前: publishStatus=" + dmTech.getPublishStatus() + ", status=" + dmTech.getStatus());

                    if (approved) {
                        dmTech.setPublishStatus("approved");
                        dmTech.setStatus("2");
                    } else {
                        dmTech.setPublishStatus("rejected");
                        dmTech.setStatus("0");
                    }

                    System.out.println("更新后: publishStatus=" + dmTech.getPublishStatus() + ", status=" + dmTech.getStatus());

                    int updateResult = dmTechService.updateDmTech(dmTech);
                    System.out.println("更新结果: " + updateResult);
                } else {
                    System.out.println("未找到技术文档，businessId: " + businessId);
                }
            }  else if ("project".equals(businessType)) {
                // 项目审批状态更新
                PjOverview project = new PjOverview();
                project.setId(businessId);
                project.setAuditStatus(approved ? "1" : "2"); // 1通过，2驳回
                project.setAuditBy(SecurityUtils.getUsername());
                project.setAuditTime(new Date());
                project.setAuditRemark(approved ? "审批通过" : "审批驳回"); // 新增这一行
                project.setStatus(approved ? "1" : "4"); // 1进行中，4已暂停

                System.out.println("更新项目状态: id=" + businessId + ", auditStatus=" + project.getAuditStatus() + ", status=" + project.getStatus());

                int updateResult = pjOverviewService.updatePjOverview(project);
                System.out.println("更新结果: " + updateResult);
            } else {
                System.out.println("businessType 不匹配: " + businessType);
            }
        } catch (Exception e) {
            System.err.println("更新业务状态失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新业务状态失败: " + e.getMessage(), e);
        }
    }
}

