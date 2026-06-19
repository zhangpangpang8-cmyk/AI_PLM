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
import com.ruoyi.system.domain.PjPhase;
import com.ruoyi.system.service.IPjPhaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目阶段Controller
 * 
 * @author ruoyi
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/system/phase")
public class PjPhaseController extends BaseController
{
    @Autowired
    private IPjPhaseService pjPhaseService;

    /**
     * 查询项目阶段列表
     */
    @PreAuthorize("@ss.hasPermi('system:phase:list')")
    @GetMapping("/list")
    public TableDataInfo list(PjPhase pjPhase)
    {
        startPage();
        List<PjPhase> list = pjPhaseService.selectPjPhaseList(pjPhase);
        return getDataTable(list);
    }

    /**
     * 导出项目阶段列表
     */
    @PreAuthorize("@ss.hasPermi('system:phase:export')")
    @Log(title = "项目阶段", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PjPhase pjPhase)
    {
        List<PjPhase> list = pjPhaseService.selectPjPhaseList(pjPhase);
        ExcelUtil<PjPhase> util = new ExcelUtil<PjPhase>(PjPhase.class);
        util.exportExcel(response, list, "项目阶段数据");
    }

    /**
     * 获取项目阶段详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:phase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(pjPhaseService.selectPjPhaseById(id));
    }

    /**
     * 新增项目阶段
     */
    @PreAuthorize("@ss.hasPermi('system:phase:add')")
    @Log(title = "项目阶段", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PjPhase pjPhase)
    {
        return toAjax(pjPhaseService.insertPjPhase(pjPhase));
    }

    /**
     * 修改项目阶段
     */
    @PreAuthorize("@ss.hasPermi('system:phase:edit')")
    @Log(title = "项目阶段", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PjPhase pjPhase)
    {
        return toAjax(pjPhaseService.updatePjPhase(pjPhase));
    }

    /**
     * 删除项目阶段
     */
    @PreAuthorize("@ss.hasPermi('system:phase:remove')")
    @Log(title = "项目阶段", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pjPhaseService.deletePjPhaseByIds(ids));
    }
}
