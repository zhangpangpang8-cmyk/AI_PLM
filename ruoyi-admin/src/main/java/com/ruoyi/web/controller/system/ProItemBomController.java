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
import com.ruoyi.system.domain.ProItemBom;
import com.ruoyi.system.service.IProItemBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * BOM版本Controller
 * 
 * @author ruoyi
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/system/bom")
public class ProItemBomController extends BaseController
{
    @Autowired
    private IProItemBomService proItemBomService;

    /**
     * 查询BOM版本列表
     */
    @PreAuthorize("@ss.hasPermi('system:bom:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProItemBom proItemBom)
    {
        startPage();
        List<ProItemBom> list = proItemBomService.selectProItemBomList(proItemBom);
        return getDataTable(list);
    }

    /**
     * 导出BOM版本列表
     */
    @PreAuthorize("@ss.hasPermi('system:bom:export')")
    @Log(title = "BOM版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProItemBom proItemBom)
    {
        List<ProItemBom> list = proItemBomService.selectProItemBomList(proItemBom);
        ExcelUtil<ProItemBom> util = new ExcelUtil<ProItemBom>(ProItemBom.class);
        util.exportExcel(response, list, "BOM版本数据");
    }

    /**
     * 获取BOM版本详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(proItemBomService.selectProItemBomById(id));
    }

    /**
     * 新增BOM版本
     */
    @PreAuthorize("@ss.hasPermi('system:bom:add')")
    @Log(title = "BOM版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProItemBom proItemBom)
    {
        return toAjax(proItemBomService.insertProItemBom(proItemBom));
    }

    /**
     * 修改BOM版本
     */
    @PreAuthorize("@ss.hasPermi('system:bom:edit')")
    @Log(title = "BOM版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProItemBom proItemBom)
    {
        return toAjax(proItemBomService.updateProItemBom(proItemBom));
    }

    /**
     * 删除BOM版本
     */
    @PreAuthorize("@ss.hasPermi('system:bom:remove')")
    @Log(title = "BOM版本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(proItemBomService.deleteProItemBomByIds(ids));
    }
}
