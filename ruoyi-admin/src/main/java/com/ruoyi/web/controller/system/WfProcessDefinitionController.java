package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
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
import com.ruoyi.system.domain.WfProcessDefinition;
import com.ruoyi.system.domain.WfProcessNode;
import com.ruoyi.system.domain.WfProcessEdge;
import com.ruoyi.system.service.IWfProcessDefinitionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.alibaba.fastjson2.JSON;

/**
 * 流程定义Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/process/definition")
public class WfProcessDefinitionController extends BaseController
{
    @Autowired
    private IWfProcessDefinitionService wfProcessDefinitionService;

    /**
     * 查询流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:list')")
    @GetMapping("/list")
    public TableDataInfo list(WfProcessDefinition wfProcessDefinition)
    {
        startPage();
        List<WfProcessDefinition> list = wfProcessDefinitionService.selectWfProcessDefinitionList(wfProcessDefinition);
        return getDataTable(list);
    }

    /**
     * 导出流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:export')")
    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WfProcessDefinition wfProcessDefinition)
    {
        List<WfProcessDefinition> list = wfProcessDefinitionService.selectWfProcessDefinitionList(wfProcessDefinition);
        ExcelUtil<WfProcessDefinition> util = new ExcelUtil<WfProcessDefinition>(WfProcessDefinition.class);
        util.exportExcel(response, list, "流程定义数据");
    }

    /**
     * 获取流程定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wfProcessDefinitionService.selectProcessDefinitionDetail(id));
    }

    /**
     * 新增流程定义
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:add')")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfProcessDefinition wfProcessDefinition)
    {
        return toAjax(wfProcessDefinitionService.insertWfProcessDefinition(wfProcessDefinition));
    }

    /**
     * 修改流程定义
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:edit')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfProcessDefinition wfProcessDefinition)
    {
        return toAjax(wfProcessDefinitionService.updateWfProcessDefinition(wfProcessDefinition));
    }

    /**
     * 删除流程定义
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:remove')")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wfProcessDefinitionService.deleteWfProcessDefinitionByIds(ids));
    }

    /**
     * 保存流程设计（包含节点和连线）
     */
    @PreAuthorize("@ss.hasPermi('system:process:definition:edit')")
    @Log(title = "流程设计", businessType = BusinessType.UPDATE)
    @PostMapping("/saveDesign")
    public AjaxResult saveDesign(@RequestBody Map<String, Object> params)
    {
        WfProcessDefinition definition = JSON.parseObject(JSON.toJSONString(params.get("definition")), WfProcessDefinition.class);
        List<WfProcessNode> nodes = JSON.parseArray(JSON.toJSONString(params.get("nodes")), WfProcessNode.class);
        List<WfProcessEdge> edges = JSON.parseArray(JSON.toJSONString(params.get("edges")), WfProcessEdge.class);

        return toAjax(wfProcessDefinitionService.saveProcessDesign(definition, nodes, edges));
    }
}
