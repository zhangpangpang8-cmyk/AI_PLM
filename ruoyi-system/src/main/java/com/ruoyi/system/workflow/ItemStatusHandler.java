package com.ruoyi.system.workflow;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.ProItem;
import com.ruoyi.system.domain.ProItemVersion;
import com.ruoyi.system.mapper.ProItemMapper;
import com.ruoyi.system.service.IProItemVersionService;

@Component
public class ItemStatusHandler implements WorkflowBusinessStatusHandler
{
    private final ProItemMapper proItemMapper;
    private final IProItemVersionService proItemVersionService;

    public ItemStatusHandler(ProItemMapper proItemMapper, IProItemVersionService proItemVersionService)
    {
        this.proItemMapper = proItemMapper;
        this.proItemVersionService = proItemVersionService;
    }

    @Override
    public String getBusinessType()
    {
        return "item";
    }

    @Override
    public void updateStatus(Long businessId, boolean approved, String opinion, String approver)
    {
        ProItem item = proItemMapper.selectProItemById(businessId);
        if (item == null)
        {
            throw new ServiceException("物料不存在: " + businessId);
        }

        item.setAuditStatus(approved ? "1" : "2");
        item.setAuditBy(approver);
        item.setAuditTime(new Date());
        item.setAuditRemark(opinion);
        item.setUpdateBy(approver);
        item.setUpdateTime(new Date());

        if (approved)
        {
            saveVersionSnapshot(item, approver);
            item.setItemVersion(incrementVersion(item.getItemVersion()));
            item.setPublishStatus("published");
            item.setLatestVersion("1");
        }
        else
        {
            item.setPublishStatus("rejected");
        }
        item.setStatus("0");
        proItemMapper.updateProItem(item);
    }

    private void saveVersionSnapshot(ProItem item, String approver)
    {
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
        snapshot.setCreateBy(approver);
        proItemVersionService.insertProItemVersion(snapshot);
    }

    private String incrementVersion(String version)
    {
        if (version == null || version.trim().isEmpty())
        {
            return "V1.0";
        }
        try
        {
            String number = version.replaceFirst("^[Vv]", "");
            String[] parts = number.split("\\.");
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts.length > 1 ? parts[1] : "0") + 1;
            return "V" + major + "." + minor;
        }
        catch (NumberFormatException ex)
        {
            throw new ServiceException("无法识别物料版本号: " + version);
        }
    }
}
