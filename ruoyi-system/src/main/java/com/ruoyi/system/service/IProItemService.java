package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ProItem;

/**
 * 物料管理（仅存储激活版本）Service接口
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
public interface IProItemService 
{
    /**
     * 查询物料管理（仅存储激活版本）
     * 
     * @param id 物料管理（仅存储激活版本）主键
     * @return 物料管理（仅存储激活版本）
     */
    public ProItem selectProItemById(Long id);

    /**
     * 查询物料管理（仅存储激活版本）列表
     * 
     * @param proItem 物料管理（仅存储激活版本）
     * @return 物料管理（仅存储激活版本）集合
     */
    public List<ProItem> selectProItemList(ProItem proItem);

    /**
     * 新增物料管理（仅存储激活版本）
     * 
     * @param proItem 物料管理（仅存储激活版本）
     * @return 结果
     */
    public int insertProItem(ProItem proItem);

    /**
     * 修改物料管理（仅存储激活版本）
     * 
     * @param proItem 物料管理（仅存储激活版本）
     * @return 结果
     */
    public int updateProItem(ProItem proItem);

    /**
     * 批量删除物料管理（仅存储激活版本）
     * 
     * @param ids 需要删除的物料管理（仅存储激活版本）主键集合
     * @return 结果
     */
    public int deleteProItemByIds(Long[] ids);

    /**
     * 删除物料管理（仅存储激活版本）信息
     * 
     * @param id 物料管理（仅存储激活版本）主键
     * @return 结果
     */
    public int deleteProItemById(Long id);

    /**
     * 按一级分类统计物料数量
     * 
     * @return 统计结果列表，每个元素包含：classifyId, classifyName, count
     */
    public java.util.List<java.util.Map<String, Object>> statisticsByFirstClassify();
}
