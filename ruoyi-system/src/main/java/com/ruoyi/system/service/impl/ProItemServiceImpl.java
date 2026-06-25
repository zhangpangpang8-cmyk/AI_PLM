package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProItemMapper;
import com.ruoyi.system.domain.ProItem;
import com.ruoyi.system.domain.WfProcessInstance;
import com.ruoyi.system.service.IProItemService;
import com.ruoyi.system.service.IProMaterialClassifyService;
import com.ruoyi.system.domain.ProMaterialClassify;
import com.ruoyi.system.service.IWfProcessInstanceService;

/**
 * 物料管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
@Service
public class ProItemServiceImpl implements IProItemService 
{
    @Autowired
    private ProItemMapper proItemMapper;

    @Autowired
    private IProMaterialClassifyService proMaterialClassifyService;

    @Autowired
    private IWfProcessInstanceService wfProcessInstanceService;

    /**
     * 查询物料管理
     * 
     * @param id 物料管理主键
     * @return 物料管理
     */
    @Override
    public ProItem selectProItemById(Long id)
    {
        return proItemMapper.selectProItemById(id);
    }

    /**
     * 查询物料管理列表
     * 
     * @param proItem 物料管理
     * @return 物料管理
     */
    @Override
    public List<ProItem> selectProItemList(ProItem proItem)
    {
        List<ProItem> list = proItemMapper.selectProItemList(proItem);
        // 批量填充审批状态
        for (ProItem item : list) {
            computeApprovalStatus(item);
        }
        return list;
    }

    /**
     * 根据流程实例状态计算审批状态
     * 0=草稿, 1=审批中, 2=已通过, 3=已驳回, 4=已取消
     */
    private void computeApprovalStatus(ProItem item) {
        String flowInsId = item.getFlowInsId();
        if (flowInsId == null || flowInsId.isEmpty()) {
            item.setApprovalStatus("0"); // 草稿
            return;
        }
        try {
            WfProcessInstance instance = wfProcessInstanceService.selectWfProcessInstanceById(Long.valueOf(flowInsId));
            if (instance == null) {
                item.setApprovalStatus("1"); // 审批中（容错默认）
                return;
            }
            String insStatus = instance.getStatus();
            switch (insStatus) {
                case "running":
                    item.setApprovalStatus("1"); // 审批中
                    break;
                case "approved":
                    item.setApprovalStatus("2"); // 已通过
                    break;
                case "rejected":
                    item.setApprovalStatus("3"); // 已驳回
                    break;
                case "cancelled":
                    item.setApprovalStatus("4"); // 已取消
                    break;
                default:
                    item.setApprovalStatus("1"); // 审批中（未知状态容错）
                    break;
            }
        } catch (Exception e) {
            item.setApprovalStatus("1"); // 审批中（异常容错）
        }
    }

    /**
     * 新增物料管理
     * 
     * @param proItem 物料管理
     * @return 结果
     */
    @Override
    public int insertProItem(ProItem proItem)
    {
        proItem.setCreateBy(SecurityUtils.getUsername());
        proItem.setCreateTime(DateUtils.getNowDate());
        // 默认审核状态为待审核
        if (proItem.getAuditStatus() == null) {
            proItem.setAuditStatus("0");
        }

        int result = proItemMapper.insertProItem(proItem);

        // 创建成功后自动启动审批流程
        if (result > 0) {
            try {
                String title = "物料审批：" + proItem.getItemName();
                WfProcessInstance instance = wfProcessInstanceService.startProcessInstance(
                        "item_approval",       // 流程标识（需在流程定义中配置）
                        "item",                // 业务类型
                        proItem.getId(),       // 业务ID
                        proItem.getItemCode(), // 业务编号
                        title,                 // 流程标题
                        SecurityUtils.getUsername() // 发起人
                );

                if (instance != null) {
                    proItem.setFlowInsId(instance.getId().toString());
                    proItem.setStatus("1");
                    proItem.setUpdateTime(DateUtils.getNowDate());
                    proItemMapper.updateProItem(proItem);
                }
            } catch (Exception e) {
                // 流程启动失败不影响物料创建
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 修改物料管理
     * 
     * @param proItem 物料管理
     * @return 结果
     */
    @Override
    public int updateProItem(ProItem proItem)
    {
        proItem.setUpdateTime(DateUtils.getNowDate());
        int result = proItemMapper.updateProItem(proItem);

        // 修改后重新发起审批流程
        if (result > 0) {
            try {
                String title = "物料审批（修改）：" + proItem.getItemName();
                WfProcessInstance instance = wfProcessInstanceService.startProcessInstance(
                        "item_approval",       // 流程标识
                        "item",                // 业务类型
                        proItem.getId(),       // 业务ID
                        proItem.getItemCode(), // 业务编号
                        title,                 // 流程标题
                        SecurityUtils.getUsername() // 发起人
                );

                if (instance != null) {
                    proItem.setFlowInsId(instance.getId().toString());
                    proItem.setAuditStatus("0");      // 重置为待审核
                    proItem.setAuditBy(null);
                    proItem.setAuditTime(null);
                    proItem.setAuditRemark(null);
                    proItem.setStatus("1");            // 审批中
                    proItem.setUpdateTime(DateUtils.getNowDate());
                    proItemMapper.updateProItem(proItem);
                }
            } catch (Exception e) {
                // 流程启动失败不影响物料修改
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 批量删除物料管理
     * 
     * @param ids 需要删除的物料管理主键
     * @return 结果
     */
    @Override
    public int deleteProItemByIds(Long[] ids)
    {
        return proItemMapper.deleteProItemByIds(ids);
    }

    /**
     * 删除物料管理信息
     * 
     * @param id 物料管理主键
     * @return 结果
     */
    @Override
    public int deleteProItemById(Long id)
    {
        return proItemMapper.deleteProItemById(id);
    }

    /**
     * 按一级分类统计物料数量
     * 
     * @return 统计结果列表
     */
    @Override
    public List<Map<String, Object>> statisticsByFirstClassify()
    {
        // 获取所有启用的物料分类树
        ProMaterialClassify query = new ProMaterialClassify();
        query.setEnable("1");
        List<ProMaterialClassify> allClassifies = proMaterialClassifyService.selectProMaterialClassifyList(query);

        // 找出所有一级分类（parentClassifyId = 0）
        List<ProMaterialClassify> firstLevelClassifies = new ArrayList<>();
        for (ProMaterialClassify classify : allClassifies) {
            if (classify.getParentClassifyId() != null && classify.getParentClassifyId() == 0L) {
                firstLevelClassifies.add(classify);
            }
        }

        // 为每个一级分类统计物料数量
        List<Map<String, Object>> statistics = new ArrayList<>();
        long totalCount = 0;

        for (ProMaterialClassify firstClassify : firstLevelClassifies) {
            Long classifyId = firstClassify.getId();
            String classifyName = firstClassify.getMaterialClassifyName();

            // 查询该分类下的物料数量（包括子分类）
            ProItem itemQuery = new ProItem();
            itemQuery.setMaterialClassifyIds(String.valueOf(classifyId));
            List<ProItem> items = proItemMapper.selectProItemList(itemQuery);

            Map<String, Object> stat = new HashMap<>();
            stat.put("classifyId", classifyId);
            stat.put("classifyName", classifyName);
            stat.put("count", items.size());
            stat.put("materialClassifyCode", firstClassify.getMaterialClassifyCode());

            statistics.add(stat);
            totalCount += items.size();
        }

        // 添加总计
        Map<String, Object> totalStat = new HashMap<>();
        totalStat.put("classifyId", 0L);
        totalStat.put("classifyName", "物料总数");
        totalStat.put("count", totalCount);
        totalStat.put("materialClassifyCode", "TOTAL");
        statistics.add(0, totalStat); // 将总计放在最前面

        return statistics;
    }
}
