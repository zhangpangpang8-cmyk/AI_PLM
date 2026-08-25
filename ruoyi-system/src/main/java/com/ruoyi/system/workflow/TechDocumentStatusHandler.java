package com.ruoyi.system.workflow;

import org.springframework.stereotype.Component;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DmTech;
import com.ruoyi.system.mapper.DmTechMapper;

@Component
public class TechDocumentStatusHandler implements WorkflowBusinessStatusHandler
{
    private final DmTechMapper dmTechMapper;

    public TechDocumentStatusHandler(DmTechMapper dmTechMapper)
    {
        this.dmTechMapper = dmTechMapper;
    }

    @Override
    public String getBusinessType()
    {
        return "tech_doc";
    }

    @Override
    public void updateStatus(Long businessId, boolean approved, String opinion, String approver)
    {
        if (dmTechMapper.selectDmTechById(businessId) == null)
        {
            throw new ServiceException("技术文档不存在: " + businessId);
        }
        DmTech update = new DmTech();
        update.setId(businessId);
        update.setPublishStatus(approved ? "approved" : "rejected");
        update.setStatus(approved ? "2" : "0");
        update.setUpdateBy(approver);
        update.setUpdateTime(DateUtils.getNowDate());
        dmTechMapper.updateDmTech(update);
    }
}
