package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.service.IWfProcessInstanceService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 流程实例Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/process/instance")
public class WfProcessInstanceController extends BaseController
{
    @Autowired
    private IWfProcessInstanceService wfProcessInstanceService;

    /**
     * 查询我发起的流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:instance:list')")
    @GetMapping("/myList")
    public TableDataInfo myList()
    {
        startPage();
        String initiator = SecurityUtils.getUsername();
        WfProcessInstance query = new WfProcessInstance();
        query.setInitiator(initiator);
        List<WfProcessInstance> list = wfProcessInstanceService.selectWfProcessInstanceList(query);
        return getDataTable(list);
    }

    /**
     * 启动流程实例
     */
    @PreAuthorize("@ss.hasPermi('system:process:instance:start')")
    @Log(title = "启动流程", businessType = BusinessType.INSERT)
    @PostMapping("/start")
    public AjaxResult start(@RequestParam String processKey,
                            @RequestParam String businessType,
                            @RequestParam Long businessId,
                            @RequestParam(required = false) String businessNo,
                            @RequestParam String title)
    {
        String initiator = SecurityUtils.getUsername();
        WfProcessInstance instance = wfProcessInstanceService.startProcessInstance(
            processKey, businessType, businessId, businessNo, title, initiator);
        return success(instance);
    }

    /**
     * 获取流程实例详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:process:instance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wfProcessInstanceService.selectWfProcessInstanceById(id));
    }
}
