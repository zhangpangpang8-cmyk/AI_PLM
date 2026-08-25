package com.ruoyi.system.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;

/**
 * 按业务类型路由审批状态回写，新增业务审批时无需修改流程引擎。
 */
@Service
public class WorkflowBusinessStatusService
{
    private final Map<String, WorkflowBusinessStatusHandler> handlers = new HashMap<>();

    public WorkflowBusinessStatusService(List<WorkflowBusinessStatusHandler> handlerList)
    {
        for (WorkflowBusinessStatusHandler handler : handlerList)
        {
            WorkflowBusinessStatusHandler previous = handlers.put(handler.getBusinessType(), handler);
            if (previous != null)
            {
                throw new IllegalStateException("重复的工作流业务类型处理器: " + handler.getBusinessType());
            }
        }
    }

    public void updateStatus(String businessType, Long businessId, boolean approved, String opinion, String approver)
    {
        if (businessType == null || businessId == null)
        {
            throw new ServiceException("流程实例缺少业务类型或业务ID");
        }
        WorkflowBusinessStatusHandler handler = handlers.get(businessType);
        if (handler == null)
        {
            throw new ServiceException("未注册审批状态处理器: " + businessType);
        }
        handler.updateStatus(businessId, approved, opinion, approver);
    }
}
