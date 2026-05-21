package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.DmTestReport;
import com.ruoyi.system.service.IDmTestReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测试报告Controller
 * 
 * @author ruoyi
 * @date 2026-05-21
 */
@RestController
@RequestMapping("/system/report")
public class DmTestReportController extends BaseController
{
    @Autowired
    private IDmTestReportService dmTestReportService;

    /**
     * 查询测试报告列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmTestReport dmTestReport)
    {
        startPage();
        List<DmTestReport> list = dmTestReportService.selectDmTestReportList(dmTestReport);
        return getDataTable(list);
    }

    /**
     * 导出测试报告列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:export')")
    @Log(title = "测试报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmTestReport dmTestReport)
    {
        List<DmTestReport> list = dmTestReportService.selectDmTestReportList(dmTestReport);
        ExcelUtil<DmTestReport> util = new ExcelUtil<DmTestReport>(DmTestReport.class);
        util.exportExcel(response, list, "测试报告数据");
    }

    /**
     * 获取测试报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dmTestReportService.selectDmTestReportById(id));
    }

    /**
     * 新增测试报告
     */
    @PreAuthorize("@ss.hasPermi('system:report:add')")
    @Log(title = "测试报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmTestReport dmTestReport)
    {
        return toAjax(dmTestReportService.insertDmTestReport(dmTestReport));
    }

    /**
     * 修改测试报告
     */
    @PreAuthorize("@ss.hasPermi('system:report:edit')")
    @Log(title = "测试报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmTestReport dmTestReport)
    {
        return toAjax(dmTestReportService.updateDmTestReport(dmTestReport));
    }

    /**
     * 删除测试报告
     */
    @PreAuthorize("@ss.hasPermi('system:report:remove')")
    @Log(title = "测试报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dmTestReportService.deleteDmTestReportByIds(ids));
    }
}
