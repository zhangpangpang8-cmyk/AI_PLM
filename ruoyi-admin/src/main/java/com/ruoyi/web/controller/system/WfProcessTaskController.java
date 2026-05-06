package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.WfProcessTask;
import com.ruoyi.system.service.IWfProcessTaskService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 流程任务Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/process/task")
public class WfProcessTaskController extends BaseController
{
    @Autowired
    private IWfProcessTaskService wfProcessTaskService;

    /**
     * 查询我的待办任务列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:task:list')")
    @GetMapping("/pending")
    public TableDataInfo pendingList()
    {
        startPage();
        String userId = SecurityUtils.getUsername();
        List<WfProcessTask> list = wfProcessTaskService.selectPendingTasksByUserId(userId);
        return getDataTable(list);
    }

    /**
     * 查询我的已办任务列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:task:list')")
    @GetMapping("/completed")
    public TableDataInfo completedList()
    {
        startPage();
        String userId = SecurityUtils.getUsername();
        List<WfProcessTask> list = wfProcessTaskService.selectCompletedTasksByUserId(userId);
        return getDataTable(list);
    }

    /**
     * 审批任务
     */
    @PreAuthorize("@ss.hasPermi('system:process:task:approve')")
    @Log(title = "流程任务审批", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestParam Long taskId,
                              @RequestParam Boolean approved,
                              @RequestParam(required = false) String opinion)
    {
        String approver = SecurityUtils.getUsername();
        return toAjax(wfProcessTaskService.approveTask(taskId, approved, opinion, approver));
    }
}
