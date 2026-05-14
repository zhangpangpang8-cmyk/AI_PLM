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
import com.ruoyi.system.domain.PjOverview;
import com.ruoyi.system.service.IPjOverviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目概述Controller
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/system/overview")
public class PjOverviewController extends BaseController
{
    @Autowired
    private IPjOverviewService pjOverviewService;

    /**
     * 查询项目概述列表
     */
    @PreAuthorize("@ss.hasPermi('system:overview:list')")
    @GetMapping("/list")
    public TableDataInfo list(PjOverview pjOverview)
    {
        startPage();
        List<PjOverview> list = pjOverviewService.selectPjOverviewList(pjOverview);
        return getDataTable(list);
    }

    /**
     * 导出项目概述列表
     */
    @PreAuthorize("@ss.hasPermi('system:overview:export')")
    @Log(title = "项目概述", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PjOverview pjOverview)
    {
        List<PjOverview> list = pjOverviewService.selectPjOverviewList(pjOverview);
        ExcelUtil<PjOverview> util = new ExcelUtil<PjOverview>(PjOverview.class);
        util.exportExcel(response, list, "项目概述数据");
    }

    /**
     * 获取项目概述详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:overview:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(pjOverviewService.selectPjOverviewById(id));
    }

    /**
     * 新增项目概述
     */
    @PreAuthorize("@ss.hasPermi('system:overview:add')")
    @Log(title = "项目概述", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PjOverview pjOverview)
    {
        return toAjax(pjOverviewService.insertPjOverview(pjOverview));
    }

    /**
     * 修改项目概述
     */
    @PreAuthorize("@ss.hasPermi('system:overview:edit')")
    @Log(title = "项目概述", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PjOverview pjOverview)
    {
        return toAjax(pjOverviewService.updatePjOverview(pjOverview));
    }

    /**
     * 删除项目概述
     */
    @PreAuthorize("@ss.hasPermi('system:overview:remove')")
    @Log(title = "项目概述", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pjOverviewService.deletePjOverviewByIds(ids));
    }
}
