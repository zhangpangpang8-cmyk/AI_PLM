package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmDrawingMapper;
import com.ruoyi.system.domain.DmDrawing;
import com.ruoyi.system.service.IDmDrawingService;

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
        return dmDrawingMapper.selectDmDrawingList(dmDrawing);
    }

    /**
     * 新增图纸管理
     * 
     * @param dmDrawing 图纸管理
     * @return 结果
     */
    @Override
    public int insertDmDrawing(DmDrawing dmDrawing)
    {
        dmDrawing.setCreateTime(DateUtils.getNowDate());
        return dmDrawingMapper.insertDmDrawing(dmDrawing);
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
