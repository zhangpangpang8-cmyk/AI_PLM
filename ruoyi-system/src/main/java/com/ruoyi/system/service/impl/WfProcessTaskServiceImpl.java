package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.DmDrawing;
import com.ruoyi.system.domain.DmTech;
import com.ruoyi.system.domain.PjOverview;
import com.ruoyi.system.domain.ProItem;
import com.ruoyi.system.domain.ProItemVersion;
import com.ruoyi.system.service.IDmDrawingService;
import com.ruoyi.system.service.IProItemVersionService;
import com.ruoyi.system.service.IDmTechService;
import com.ruoyi.system.service.IPjOverviewService;
import com.ruoyi.system.service.IProItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.WfProcessTaskMapper;
import com.ruoyi.system.mapper.WfProcessInstanceMapper;
import com.ruoyi.system.mapper.ProItemMapper;
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

    @Autowired
    private IDmDrawingService dmDrawingService;

    @Autowired
    private IProItemService proItemService;

    @Autowired
    private ProItemMapper proItemMapper;

    @Autowired
    private IProItemVersionService proItemVersionService;
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
            } else if ("item".equals(businessType)) {
                // 物料审批状态更新
                ProItem item = proItemService.selectProItemById(businessId);
                if (item != null) {
                    item.setAuditStatus(approved ? "1" : "2");
                    item.setAuditBy(SecurityUtils.getUsername());
                    item.setAuditTime(new Date());
                    item.setAuditRemark(approved ? "审批通过" : "审批驳回");

                    if (approved) {
                        // 审批通过前：先保存当前版本快照到版本历史表
                        saveVersionSnapshot(item);
                        // 审批通过：版本号自动迭代 (V1.0 → V1.1 → V1.2)
                        String currentVersion = item.getItemVersion();
                        if (currentVersion != null && !currentVersion.isEmpty()) {
                            String newVersion = incrementVersion(currentVersion);
                            item.setItemVersion(newVersion);
                            System.out.println("版本迭代: " + currentVersion + " → " + newVersion);
                        }
                        item.setPublishStatus("published");
                        item.setLatestVersion("1");
                        item.setStatus("0");
                    } else {
                        item.setPublishStatus("rejected");
                        item.setStatus("0");
                    }

                    System.out.println("更新物料状态: id=" + businessId + ", auditStatus=" + item.getAuditStatus() + ", version=" + item.getItemVersion());

                    // 直接用 mapper 更新，避免触发 service 层的审批流程
                    int updateResult = proItemMapper.updateProItem(item);
                    System.out.println("更新结果: " + updateResult);
                } else {
                    System.out.println("未找到物料，businessId: " + businessId);
                }
            } else if ("drawing".equals(businessType)) {
                // 图纸审批状态更新
                DmDrawing drawing = dmDrawingService.selectDmDrawingById(businessId);
                if (drawing != null) {
                    if (approved) {
                        drawing.setPublishStatus("published");
                        drawing.setStatus("0");
                    } else {
                        drawing.setPublishStatus("rejected");
                        drawing.setStatus("0");
                    }
                    drawing.setUpdateTime(new Date());

                    System.out.println("更新图纸状态: id=" + businessId + ", publishStatus=" + drawing.getPublishStatus());

                    int updateResult = dmDrawingService.updateDmDrawing(drawing);
                    System.out.println("更新结果: " + updateResult);
                } else {
                    System.out.println("未找到图纸，businessId: " + businessId);
                }
            } else {
                System.out.println("businessType 不匹配: " + businessType);
            }
        } catch (Exception e) {
            System.err.println("更新业务状态失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新业务状态失败: " + e.getMessage(), e);
        }
    }

    /**
     * 保存当前版本快照到版本历史表
     */
    private void saveVersionSnapshot(ProItem item) {
        try {
            ProItemVersion snapshot = new ProItemVersion();
            snapshot.setItemId(item.getId());
            snapshot.setItemCode(item.getItemCode());
            snapshot.setItemName(item.getItemName());
            snapshot.setItemTypeId(item.getItemTypeId());
            snapshot.setItemTypeCode(item.getItemTypeCode());
            snapshot.setItemTypeName(item.getItemTypeName());
            snapshot.setItemVersion(item.getItemVersion());
            snapshot.setMaterialClassifyIds(item.getMaterialClassifyIds());
            snapshot.setMaterialClassifyName(item.getMaterialClassifyName());
            snapshot.setSpecification(item.getSpecification());
            snapshot.setUnitId(item.getUnitId());
            snapshot.setUnitName(item.getUnitName());
            snapshot.setStatus(item.getStatus());
            snapshot.setEnable(item.getEnable());
            snapshot.setVendorId(item.getVendorId());
            snapshot.setVendorName(item.getVendorName());
            snapshot.setMaterial(item.getMaterial());
            snapshot.setColor(item.getColor());
            snapshot.setWeight(item.getWeight());
            snapshot.setInfo(item.getInfo());
            snapshot.setParametersValues(item.getParametersValues());
            snapshot.setUrl(item.getUrl());
            snapshot.setPublishStatus("published");
            snapshot.setRemake(item.getRemake());
            snapshot.setCreateBy(item.getUpdateBy() != null ? item.getUpdateBy() : item.getCreateBy());

            proItemVersionService.insertProItemVersion(snapshot);
            System.out.println("版本快照已保存: itemCode=" + item.getItemCode() + ", version=" + item.getItemVersion());
        } catch (Exception e) {
            System.err.println("保存版本快照失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 版本号迭代：V1.0 → V1.1 → V1.2 → ... → V1.10 → V1.11
     */
    private String incrementVersion(String version) {
        try {
            // 去除前缀 V/v
            String numPart = version.replaceFirst("^[Vv]", "");
            String[] parts = numPart.split("\\.");
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts.length > 1 ? parts[1] : "0");
            minor++;
            return "V" + major + "." + minor;
        } catch (Exception e) {
            System.err.println("版本号解析失败: " + version + ", 默认返回 V1.0");
            return "V1.0";
        }
    }
}

