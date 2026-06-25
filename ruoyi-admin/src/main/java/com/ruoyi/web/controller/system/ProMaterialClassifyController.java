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
import com.ruoyi.system.domain.ProMaterialClassify;
import com.ruoyi.system.service.IProMaterialClassifyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料分类（支持多级分类）Controller
 *
 * @author ruoyi
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/system/classify")
public class ProMaterialClassifyController extends BaseController {
    @Autowired
    private IProMaterialClassifyService proMaterialClassifyService;

    /**
     * 查询物料分类（支持多级分类）列表
     */
    @PreAuthorize("@ss.hasPermi('system:classify:list')")
    @GetMapping("/list")
    public AjaxResult list(ProMaterialClassify proMaterialClassify) {
        List<ProMaterialClassify> list = proMaterialClassifyService.selectProMaterialClassifyList(proMaterialClassify);
        return success(list);
    }

    /**
     * 导出物料分类（支持多级分类）列表
     */
    @PreAuthorize("@ss.hasPermi('system:classify:export')")
    @Log(title = "物料分类（支持多级分类）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProMaterialClassify proMaterialClassify) {
        List<ProMaterialClassify> list = proMaterialClassifyService.selectProMaterialClassifyList(proMaterialClassify);
        ExcelUtil<ProMaterialClassify> util = new ExcelUtil<ProMaterialClassify>(ProMaterialClassify.class);
        util.exportExcel(response, list, "物料分类（支持多级分类）数据");
    }

    /**
     * 获取物料分类（支持多级分类）详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:classify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(proMaterialClassifyService.selectProMaterialClassifyById(id));
    }

    /**
     * 新增物料分类（支持多级分类）
     */
    @PreAuthorize("@ss.hasPermi('system:classify:add')")
    @Log(title = "物料分类（支持多级分类）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProMaterialClassify proMaterialClassify) {
        return toAjax(proMaterialClassifyService.insertProMaterialClassify(proMaterialClassify));
    }

    /**
     * 修改物料分类（支持多级分类）
     */
    @PreAuthorize("@ss.hasPermi('system:classify:edit')")
    @Log(title = "物料分类（支持多级分类）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProMaterialClassify proMaterialClassify) {
        return toAjax(proMaterialClassifyService.updateProMaterialClassify(proMaterialClassify));
    }

    /**
     * 删除物料分类（支持多级分类）
     */
    @PreAuthorize("@ss.hasPermi('system:classify:remove')")
    @Log(title = "物料分类（支持多级分类）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(proMaterialClassifyService.deleteProMaterialClassifyByIds(ids));
    }

    /**
     * 查询物料分类树
     */
    @PreAuthorize("@ss.hasPermi('system:classify:list')")
    @GetMapping("/tree")
    public AjaxResult tree(ProMaterialClassify proMaterialClassify) {
        List<ProMaterialClassify> list = proMaterialClassifyService.selectProMaterialClassifyTreeList(proMaterialClassify);
        return success(list);
    }
}
