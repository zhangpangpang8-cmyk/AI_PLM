package com.ruoyi.system.workflow;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.PjOverview;
import com.ruoyi.system.mapper.PjOverviewMapper;

@Component
public class ProjectStatusHandler implements WorkflowBusinessStatusHandler
{
    private final PjOverviewMapper pjOverviewMapper;

    public ProjectStatusHandler(PjOverviewMapper pjOverviewMapper)
    {
        this.pjOverviewMapper = pjOverviewMapper;
    }

    @Override
    public String getBusinessType()
    {
        return "project";
    }

    @Override
    public void updateStatus(Long businessId, boolean approved, String opinion, String approver)
    {
        if (pjOverviewMapper.selectPjOverviewById(businessId) == null)
        {
            throw new ServiceException("项目不存在: " + businessId);
        }
        PjOverview update = new PjOverview();
        update.setId(businessId);
        update.setAuditStatus(approved ? "1" : "2");
        update.setAuditBy(approver);
        update.setAuditTime(new Date());
        update.setAuditRemark(opinion);
        update.setStatus(approved ? "1" : "4");
        update.setUpdateBy(approver);
        update.setUpdateTime(new Date());
        pjOverviewMapper.updatePjOverview(update);
    }
}
