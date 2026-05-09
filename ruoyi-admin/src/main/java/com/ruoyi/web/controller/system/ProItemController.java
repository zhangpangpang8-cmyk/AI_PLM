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
import com.ruoyi.system.domain.ProItem;
import com.ruoyi.system.service.IProItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料管理（仅存储激活版本）Controller
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
@RestController
@RequestMapping("/system/item")
public class ProItemController extends BaseController
{
    @Autowired
    private IProItemService proItemService;

    /**
     * 查询物料管理（仅存储激活版本）列表
     */
    @PreAuthorize("@ss.hasPermi('system:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProItem proItem)
    {
        startPage();
        List<ProItem> list = proItemService.selectProItemList(proItem);
        return getDataTable(list);
    }

    /**
     * 导出物料管理（仅存储激活版本）列表
     */
    @PreAuthorize("@ss.hasPermi('system:item:export')")
    @Log(title = "物料管理（仅存储激活版本）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProItem proItem)
    {
        List<ProItem> list = proItemService.selectProItemList(proItem);
        ExcelUtil<ProItem> util = new ExcelUtil<ProItem>(ProItem.class);
        util.exportExcel(response, list, "物料管理（仅存储激活版本）数据");
    }

    /**
     * 获取物料管理（仅存储激活版本）详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:item:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(proItemService.selectProItemById(id));
    }

    /**
     * 新增物料管理（仅存储激活版本）
     */
    @PreAuthorize("@ss.hasPermi('system:item:add')")
    @Log(title = "物料管理（仅存储激活版本）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProItem proItem)
    {
        return toAjax(proItemService.insertProItem(proItem));
    }

    /**
     * 修改物料管理（仅存储激活版本）
     */
    @PreAuthorize("@ss.hasPermi('system:item:edit')")
    @Log(title = "物料管理（仅存储激活版本）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProItem proItem)
    {
        return toAjax(proItemService.updateProItem(proItem));
    }

    /**
     * 删除物料管理（仅存储激活版本）
     */
    @PreAuthorize("@ss.hasPermi('system:item:remove')")
    @Log(title = "物料管理（仅存储激活版本）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(proItemService.deleteProItemByIds(ids));
    }
}
