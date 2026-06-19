package com.ruoyi.web.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PjTaskLog;
import com.ruoyi.system.service.IPjTaskLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务进度日志Controller
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/system/log")
public class PjTaskLogController extends BaseController
{
    @Autowired
    private IPjTaskLogService pjTaskLogService;

    /**
     * 查询任务进度日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(PjTaskLog pjTaskLog)
    {
        startPage();
        List<PjTaskLog> list = pjTaskLogService.selectPjTaskLogList(pjTaskLog);
        return getDataTable(list);
    }

    /**
     * 导出任务进度日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "任务进度日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PjTaskLog pjTaskLog)
    {
        List<PjTaskLog> list = pjTaskLogService.selectPjTaskLogList(pjTaskLog);
        ExcelUtil<PjTaskLog> util = new ExcelUtil<PjTaskLog>(PjTaskLog.class);
        util.exportExcel(response, list, "任务进度日志数据");
    }

    /**
     * 获取任务进度日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(pjTaskLogService.selectPjTaskLogById(id));
    }

    /**
     * 新增任务进度日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "任务进度日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PjTaskLog pjTaskLog)
    {
        return toAjax(pjTaskLogService.insertPjTaskLog(pjTaskLog));
    }

    /**
     * 修改任务进度日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "任务进度日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PjTaskLog pjTaskLog)
    {
        return toAjax(pjTaskLogService.updatePjTaskLog(pjTaskLog));
    }

    /**
     * 删除任务进度日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "任务进度日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pjTaskLogService.deletePjTaskLogByIds(ids));
    }
}
