package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.DmDrawingMapper;
import com.ruoyi.system.domain.DmDrawing;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.service.IDmDrawingService;
import com.ruoyi.system.service.IWfProcessInstanceService;
import com.ruoyi.system.workflow.WorkflowApprovalStatusResolver;

/**
 * 图纸管理Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class DmDrawingServiceImpl implements IDmDrawingService 
{
    @Autowired
    private DmDrawingMapper dmDrawingMapper;

    @Autowired
    private IWfProcessInstanceService processInstanceService;

    @Autowired
    private WorkflowApprovalStatusResolver approvalStatusResolver;

    private static final String DEFAULT_DRAWING_FLOW_KEY = "drawing_approval";

    /**
     * 查询图纸管理
     * 
     * @param id 图纸管理主键
     * @return 图纸管理
     */
    @Override
    public DmDrawing selectDmDrawingById(Long id)
    {
        return dmDrawingMapper.selectDmDrawingById(id);
    }

    /**
     * 查询图纸管理列表
     * 
     * @param dmDrawing 图纸管理
     * @return 图纸管理
     */
    @Override
    public List<DmDrawing> selectDmDrawingList(DmDrawing dmDrawing)
    {
        List<DmDrawing> list = dmDrawingMapper.selectDmDrawingList(dmDrawing);
        // 批量填充审批状态
        for (DmDrawing drawing : list) {
            drawing.setApprovalStatus(approvalStatusResolver.resolve(drawing.getFlowInsId()));
        }
        return list;
    }

    /**
     * 新增图纸管理
     * 
     * @param dmDrawing 图纸管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDmDrawing(DmDrawing dmDrawing)
    {
        dmDrawing.setCreateTime(DateUtils.getNowDate());

        if (dmDrawing.getStatus() == null) {
            dmDrawing.setStatus("0");
        }

        if (dmDrawing.getPublishStatus() == null) {
            dmDrawing.setPublishStatus("draft");
        }

        if (dmDrawing.getFlowKey() == null || dmDrawing.getFlowKey().isEmpty()) {
            dmDrawing.setFlowKey(DEFAULT_DRAWING_FLOW_KEY);
        }

        int result = dmDrawingMapper.insertDmDrawing(dmDrawing);

        // 创建成功后自动启动审批流程
        if (result > 0) {
            try {
                String currentUser = SecurityUtils.getUsername();

                WfProcessInstance instance = processInstanceService.startProcessInstance(
                        dmDrawing.getFlowKey(),
                        "drawing",
                        dmDrawing.getId(),
                        dmDrawing.getDrawingCode(),
                        "图纸审批-" + dmDrawing.getDrawingName(),
                        currentUser
                );

                if (instance != null) {
                    dmDrawing.setFlowInsId(instance.getId().toString());
                    dmDrawing.setStatus("1");
                    dmDrawing.setUpdateTime(DateUtils.getNowDate());
                    dmDrawingMapper.updateDmDrawing(dmDrawing);
                }
            } catch (Exception e) {
                // 流程启动失败不影响图纸创建
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 修改图纸管理
     * 
     * @param dmDrawing 图纸管理
     * @return 结果
     */
    @Override
    public int updateDmDrawing(DmDrawing dmDrawing)
    {
        dmDrawing.setUpdateTime(DateUtils.getNowDate());
        return dmDrawingMapper.updateDmDrawing(dmDrawing);
    }

    /**
     * 批量删除图纸管理
     * 
     * @param ids 需要删除的图纸管理主键
     * @return 结果
     */
    @Override
    public int deleteDmDrawingByIds(Long[] ids)
    {
        return dmDrawingMapper.deleteDmDrawingByIds(ids);
    }

    /**
     * 删除图纸管理信息
     * 
     * @param id 图纸管理主键
     * @return 结果
     */
    @Override
    public int deleteDmDrawingById(Long id)
    {
        return dmDrawingMapper.deleteDmDrawingById(id);
    }
}
