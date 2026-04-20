package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmDrawing;

/**
 * 图纸管理Service接口
 * 
 * @author ruoyi
 */
public interface IDmDrawingService 
{
    /**
     * 查询图纸管理
     * 
     * @param id 图纸管理主键
     * @return 图纸管理
     */
    public DmDrawing selectDmDrawingById(Long id);

    /**
     * 查询图纸管理列表
     * 
     * @param dmDrawing 图纸管理
     * @return 图纸管理集合
     */
    public List<DmDrawing> selectDmDrawingList(DmDrawing dmDrawing);

    /**
     * 新增图纸管理
     * 
     * @param dmDrawing 图纸管理
     * @return 结果
     */
    public int insertDmDrawing(DmDrawing dmDrawing);

    /**
     * 修改图纸管理
     * 
     * @param dmDrawing 图纸管理
     * @return 结果
     */
    public int updateDmDrawing(DmDrawing dmDrawing);

    /**
     * 批量删除图纸管理
     * 
     * @param ids 需要删除的图纸管理主键集合
     * @return 结果
     */
    public int deleteDmDrawingByIds(Long[] ids);

    /**
     * 删除图纸管理信息
     * 
     * @param id 图纸管理主键
     * @return 结果
     */
    public int deleteDmDrawingById(Long id);
}
