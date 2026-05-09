package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ProItem;

/**
 * 物料管理（仅存储激活版本）Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
public interface ProItemMapper 
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
     * 删除物料管理（仅存储激活版本）
     * 
     * @param id 物料管理（仅存储激活版本）主键
     * @return 结果
     */
    public int deleteProItemById(Long id);

    /**
     * 批量删除物料管理（仅存储激活版本）
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProItemByIds(Long[] ids);
}
