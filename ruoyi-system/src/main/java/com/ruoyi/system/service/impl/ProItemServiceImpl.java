package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProItemMapper;
import com.ruoyi.system.domain.ProItem;
import com.ruoyi.system.service.IProItemService;

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
        return proItemMapper.selectProItemList(proItem);
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
        proItem.setCreateTime(DateUtils.getNowDate());
        return proItemMapper.insertProItem(proItem);
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
        return proItemMapper.updateProItem(proItem);
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
}
