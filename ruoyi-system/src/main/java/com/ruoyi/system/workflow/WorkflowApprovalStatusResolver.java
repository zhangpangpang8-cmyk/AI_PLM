package com.ruoyi.system.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.service.IWfProcessInstanceService;

/** 将工作流实例状态统一转换为 PLM 业务审批状态。 */
@Service
public class WorkflowApprovalStatusResolver
{
    public static final String DRAFT = "0";
    public static final String PENDING = "1";
    public static final String APPROVED = "2";
    public static final String REJECTED = "3";
    public static final String CANCELLED = "4";

    private static final Logger log = LoggerFactory.getLogger(WorkflowApprovalStatusResolver.class);

    @Autowired
    private IWfProcessInstanceService processInstanceService;

    public String resolve(String flowInstanceId)
    {
        if (flowInstanceId == null || flowInstanceId.isEmpty())
        {
            return DRAFT;
        }

        try
        {
            WfProcessInstance instance = processInstanceService.selectWfProcessInstanceById(Long.valueOf(flowInstanceId));
            return instance == null ? PENDING : mapStatus(instance.getStatus());
        }
        catch (RuntimeException exception)
        {
            log.warn("解析流程实例审批状态失败，flowInstanceId={}", flowInstanceId, exception);
            return PENDING;
        }
    }

    private String mapStatus(String instanceStatus)
    {
        if ("approved".equals(instanceStatus))
        {
            return APPROVED;
        }
        if ("rejected".equals(instanceStatus))
        {
            return REJECTED;
        }
        if ("cancelled".equals(instanceStatus))
        {
            return CANCELLED;
        }
        return PENDING;
    }
}
