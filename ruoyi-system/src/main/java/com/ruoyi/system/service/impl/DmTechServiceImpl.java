package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.DmTechMapper;
import com.ruoyi.system.domain.DmTech;
import com.ruoyi.system.service.IDmTechService;
import com.ruoyi.system.service.IWfProcessInstanceService;
import com.ruoyi.system.service.IWfProcessDefinitionService;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.domain.WfProcessDefinition;

/**
 * 技术文档Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class DmTechServiceImpl implements IDmTechService 
{
    @Autowired
    private DmTechMapper dmTechMapper;

    @Autowired
    private IWfProcessInstanceService processInstanceService;

    @Autowired
    private IWfProcessDefinitionService processDefinitionService;

    private static final String DEFAULT_TECH_FLOW_KEY = "tech_approval";


    /**
     * 查询技术文档
     * 
     * @param id 技术文档主键
     * @return 技术文档
     */
    @Override
    public DmTech selectDmTechById(Long id)
    {
        return dmTechMapper.selectDmTechById(id);
    }

    /**
     * 查询技术文档列表
     * 
     * @param dmTech 技术文档
     * @return 技术文档
     */
    @Override
    public List<DmTech> selectDmTechList(DmTech dmTech)
    {
        return dmTechMapper.selectDmTechList(dmTech);
    }

    /**
     * 新增技术文档
     * 
     * @param dmTech 技术文档
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDmTech(DmTech dmTech)
    {
        dmTech.setCreateTime(DateUtils.getNowDate());

        if (dmTech.getStatus() == null) {
            dmTech.setStatus("0");
        }

        if (dmTech.getPublishStatus() == null) {
            dmTech.setPublishStatus("draft");
        }

        if (dmTech.getFlowKey() == null || dmTech.getFlowKey().isEmpty()) {
            dmTech.setFlowKey(DEFAULT_TECH_FLOW_KEY);
        }

        WfProcessDefinition processDef = processDefinitionService.selectWfProcessDefinitionByKey(dmTech.getFlowKey());

        if (processDef == null || !"0".equals(processDef.getStatus())) {
            throw new RuntimeException("流程未定义或已停用，请联系管理员配置流程标识：" + dmTech.getFlowKey());
        }

        int result = dmTechMapper.insertDmTech(dmTech);

        if (result > 0) {
            try {
                String currentUser = SecurityUtils.getUsername();

                WfProcessInstance instance = processInstanceService.startProcessInstance(
                        dmTech.getFlowKey(),
                        "tech_doc",
                        dmTech.getId(),
                        dmTech.getTechCode(),
                        "技术文档审批-" + dmTech.getTechName(),
                        currentUser
                );

                if (instance != null) {
                    dmTech.setFlowInsId(instance.getId().toString());
                    dmTech.setStatus("1");
                    dmTech.setUpdateTime(DateUtils.getNowDate());
                    dmTechMapper.updateDmTech(dmTech);
                }
            } catch (Exception e) {
                throw new RuntimeException("启动审批流程失败：" + e.getMessage());
            }
        }

        return result;
    }
    /**
     * 修改技术文档
     * 
     * @param dmTech 技术文档
     * @return 结果
     */
    @Override
    public int updateDmTech(DmTech dmTech)
    {
        dmTech.setUpdateTime(DateUtils.getNowDate());
        return dmTechMapper.updateDmTech(dmTech);
    }

    /**
     * 批量删除技术文档
     * 
     * @param ids 需要删除的技术文档主键
     * @return 结果
     */
    @Override
    public int deleteDmTechByIds(Long[] ids)
    {
        return dmTechMapper.deleteDmTechByIds(ids);
    }

    /**
     * 删除技术文档信息
     * 
     * @param id 技术文档主键
     * @return 结果
     */
    @Override
    public int deleteDmTechById(Long id)
    {
        return dmTechMapper.deleteDmTechById(id);
    }
}
