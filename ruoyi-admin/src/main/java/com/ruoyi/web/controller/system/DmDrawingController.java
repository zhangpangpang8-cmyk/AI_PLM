package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DmDrawing;
import com.ruoyi.system.service.IDmDrawingService;
import com.ruoyi.system.service.IMinioService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 图纸管理Controller
 * 
 * @author ruoyi
 * @date 2026-04-20
 */
@RestController
@RequestMapping("/system/drawing")
public class DmDrawingController extends BaseController
{
    @Autowired
    private IDmDrawingService dmDrawingService;

    @Autowired
    private IMinioService minioService;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 查询图纸管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:drawing:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmDrawing dmDrawing)
    {
        startPage();
        List<DmDrawing> list = dmDrawingService.selectDmDrawingList(dmDrawing);
        return getDataTable(list);
    }

    /**
     * 导出图纸管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:drawing:export')")
    @Log(title = "图纸管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmDrawing dmDrawing)
    {
        List<DmDrawing> list = dmDrawingService.selectDmDrawingList(dmDrawing);
        ExcelUtil<DmDrawing> util = new ExcelUtil<DmDrawing>(DmDrawing.class);
        util.exportExcel(response, list, "图纸管理数据");
    }

    /**
     * 获取图纸管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:drawing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dmDrawingService.selectDmDrawingById(id));
    }

    /**
     * 新增图纸管理
     */
    @PreAuthorize("@ss.hasPermi('system:drawing:add')")
    @Log(title = "图纸管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestParam("file") MultipartFile file) throws Exception
    {
        // 上传文件到MinIO
        String fileUrl = minioService.uploadFile(file, bucketName);

        // 创建新的DmDrawing对象，只设置必要属性
        DmDrawing dmDrawing = new DmDrawing();
        dmDrawing.setFileName(file.getOriginalFilename());
        dmDrawing.setFilePath(fileUrl);

        return toAjax(dmDrawingService.insertDmDrawing(dmDrawing));
    }

    /**
     * 修改图纸管理
     */
    @PreAuthorize("@ss.hasPermi('system:drawing:edit')")
    @Log(title = "图纸管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmDrawing dmDrawing)
    {
        return toAjax(dmDrawingService.updateDmDrawing(dmDrawing));
    }

    /**
     * 删除图纸管理
     */
    @PreAuthorize("@ss.hasPermi('system:drawing:remove')")
    @Log(title = "图纸管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dmDrawingService.deleteDmDrawingByIds(ids));
    }
}
