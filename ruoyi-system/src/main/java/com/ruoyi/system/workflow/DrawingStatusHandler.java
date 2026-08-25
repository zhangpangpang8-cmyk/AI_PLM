package com.ruoyi.system.workflow;

import org.springframework.stereotype.Component;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DmDrawing;
import com.ruoyi.system.mapper.DmDrawingMapper;

@Component
public class DrawingStatusHandler implements WorkflowBusinessStatusHandler
{
    private final DmDrawingMapper dmDrawingMapper;

    public DrawingStatusHandler(DmDrawingMapper dmDrawingMapper)
    {
        this.dmDrawingMapper = dmDrawingMapper;
    }

    @Override
    public String getBusinessType()
    {
        return "drawing";
    }

    @Override
    public void updateStatus(Long businessId, boolean approved, String opinion, String approver)
    {
        if (dmDrawingMapper.selectDmDrawingById(businessId) == null)
        {
            throw new ServiceException("图纸不存在: " + businessId);
        }
        DmDrawing update = new DmDrawing();
        update.setId(businessId);
        update.setPublishStatus(approved ? "published" : "rejected");
        update.setStatus("0");
        update.setUpdateBy(approver);
        update.setUpdateTime(DateUtils.getNowDate());
        dmDrawingMapper.updateDmDrawing(update);
    }
}
