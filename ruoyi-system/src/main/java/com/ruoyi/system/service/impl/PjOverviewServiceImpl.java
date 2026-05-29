package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.IWfProcessInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PjOverviewMapper;
import com.ruoyi.system.domain.PjOverview;
import com.ruoyi.system.service.IPjOverviewService;

/**
 * 项目概述Service业务层处理
 * 
 * @author ruoyi
 * {@code @date} 2026-05-28
 */
@Service
public class PjOverviewServiceImpl implements IPjOverviewService 
{
    @Autowired
    private PjOverviewMapper pjOverviewMapper;

    @Autowired
    private IWfProcessInstanceService wfProcessInstanceService;

    /**
     * 查询项目概述
     * 
     * @param id 项目概述主键
     * @return 项目概述
     */
    @Override
    public PjOverview selectPjOverviewById(Long id)
    {
        return pjOverviewMapper.selectPjOverviewById(id);
    }

    /**
     * 查询项目概述列表
     * 
     * @param pjOverview 项目概述
     * @return 项目概述
     */
    @Override
    public List<PjOverview> selectPjOverviewList(PjOverview pjOverview)
    {
        return pjOverviewMapper.selectPjOverviewList(pjOverview);
    }

    /**
     * 新增项目概述
     * 
     * @param pjOverview 项目概述
     * @return 结果
     */
    @Override
    public int insertPjOverview(PjOverview pjOverview)
    {
        pjOverview.setCreateBy(SecurityUtils.getUsername());
        pjOverview.setCreateTime(DateUtils.getNowDate());
        // 默认状态为审核中
        if (pjOverview.getStatus() == null) {
            pjOverview.setStatus("2");
        }
        // 默认审核状态为待审核
        if (pjOverview.getAuditStatus() == null) {
            pjOverview.setAuditStatus("0");
        }

        int result = pjOverviewMapper.insertPjOverview(pjOverview);

        // 创建成功后自动启动审批流程
        if (result > 0) {
            try {
                String title = "项目审批：" + pjOverview.getProjectName();
                wfProcessInstanceService.startProcessInstance(
                        "project_approval",     // 流程标识（需在流程定义中配置）
                        "project",              // 业务类型
                        pjOverview.getId(),     // 业务ID
                        pjOverview.getProjectCode(), // 业务编号
                        title,                  // 流程标题
                        SecurityUtils.getUsername() // 发起人
                );
            } catch (Exception e) {
                // 流程启动失败不影响项目创建
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * 修改项目概述
     * 
     * @param pjOverview 项目概述
     * @return 结果
     */
    @Override
    public int updatePjOverview(PjOverview pjOverview)
    {
        pjOverview.setUpdateTime(DateUtils.getNowDate());
        return pjOverviewMapper.updatePjOverview(pjOverview);
    }

    /**
     * 批量删除项目概述
     * 
     * @param ids 需要删除的项目概述主键
     * @return 结果
     */
    @Override
    public int deletePjOverviewByIds(Long[] ids)
    {
        return pjOverviewMapper.deletePjOverviewByIds(ids);
    }

    /**
     * 删除项目概述信息
     * 
     * @param id 项目概述主键
     * @return 结果
     */
    @Override
    public int deletePjOverviewById(Long id)
    {
        return pjOverviewMapper.deletePjOverviewById(id);
    }

    @Override
    public Map<String, Object> selectProjectStatistics()
    {
        return pjOverviewMapper.selectProjectStatistics();
    }
}
