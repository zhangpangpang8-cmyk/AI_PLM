package com.ruoyi.system.workflow;

/**
 * 审批完成后的业务状态回写扩展点。
 */
public interface WorkflowBusinessStatusHandler
{
    String getBusinessType();

    void updateStatus(Long businessId, boolean approved, String opinion, String approver);
}
