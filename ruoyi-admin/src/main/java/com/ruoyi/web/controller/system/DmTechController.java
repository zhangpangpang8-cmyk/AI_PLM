package com.ruoyi.web.controller.system;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DmTech;
import com.ruoyi.system.service.IDmTechService;
import com.ruoyi.system.service.IMinioService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;


/**
 * 技术文档Controller
 *
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/system/tech")
public class DmTechController extends BaseController {
    @Autowired
    private IDmTechService dmTechService;

    @Autowired
    private IMinioService minioService;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 查询技术文档列表
     */
    @PreAuthorize("@ss.hasPermi('system:tech:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmTech dmTech) {
        startPage();
        List<DmTech> list = dmTechService.selectDmTechList(dmTech);
        return getDataTable(list);
    }

    /**
     * 导出技术文档列表
     */
    @PreAuthorize("@ss.hasPermi('system:tech:export')")
    @Log(title = "技术文档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmTech dmTech) {
        List<DmTech> list = dmTechService.selectDmTechList(dmTech);
        ExcelUtil<DmTech> util = new ExcelUtil<DmTech>(DmTech.class);
        util.exportExcel(response, list, "技术文档数据");
    }

    /**
     * 获取技术文档详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tech:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(dmTechService.selectDmTechById(id));
    }

    /**
     * 新增技术文档
     */
    @PreAuthorize("@ss.hasPermi('system:tech:add')")
    @Log(title = "技术文档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestParam("file") MultipartFile file, @ModelAttribute DmTech dmTech) throws Exception {
        // 1. 上传文件到MinIO
        String fileUrl = minioService.uploadFile(file, bucketName);
        System.out.println("=== 文件上传到MinIO成功 ===");
        System.out.println("文件URL: " + fileUrl);

        // 2. 将MinIO返回的URL保存到数据库
        dmTech.setFileName(file.getOriginalFilename());
        dmTech.setFilePath(fileUrl);
        dmTech.setSize(file.getSize());

        // 3. 自动设置文件后缀
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            dmTech.setFileSuffix(originalFilename.substring(originalFilename.lastIndexOf('.') + 1));
        }

        // 4. 自动格式化文件大小
        long size = file.getSize();
        dmTech.setFileSize(size < 1024 * 1024
                ? String.format("%.1fKB", size / 1024.0)
                : String.format("%.2fMB", size / 1024.0 / 1024.0));

        // 5. 设置默认版本号
        if (dmTech.getTechVersion() == null || dmTech.getTechVersion().isEmpty()) {
            dmTech.setTechVersion("1.0");
        }

        // 6. 保存到数据库
        return toAjax(dmTechService.insertDmTech(dmTech));
    }


    /**
     * 修改技术文档
     */
    @PreAuthorize("@ss.hasPermi('system:tech:edit')")
    @Log(title = "技术文档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestParam(value = "file", required = false) MultipartFile file, DmTech dmTech) throws Exception
    {
        // 如果有新文件，上传到MinIO并更新数据库
        if (file != null && !file.isEmpty()) {
            String fileUrl = minioService.uploadFile(file, bucketName);
            System.out.println("=== 文件更新上传到MinIO成功 ===");
            System.out.println("文件URL: " + fileUrl);

            dmTech.setFileName(file.getOriginalFilename());
            dmTech.setFilePath(fileUrl);
            dmTech.setSize(file.getSize());

            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && originalFilename.contains(".")) {
                dmTech.setFileSuffix(originalFilename.substring(originalFilename.lastIndexOf('.') + 1));
            }

            long size = file.getSize();
            dmTech.setFileSize(size < 1024 * 1024
                    ? String.format("%.1fKB", size / 1024.0)
                    : String.format("%.2fMB", size / 1024.0 / 1024.0));
        }

        // Service层会自动递增版本号
        return toAjax(dmTechService.updateDmTech(dmTech));
    }

    /**
     * 删除技术文档
     */
    @PreAuthorize("@ss.hasPermi('system:tech:remove')")
    @Log(title = "技术文档", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dmTechService.deleteDmTechByIds(ids));
    }
}