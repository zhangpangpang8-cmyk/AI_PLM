package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmDrawing;

/**
 * 图纸管理Mapper接口
 * 
 * @author ruoyi
 */
public interface DmDrawingMapper 
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
     * 删除图纸管理
     * 
     * @param id 图纸管理主键
     * @return 结果
     */
    public int deleteDmDrawingById(Long id);

    /**
     * 批量删除图纸管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmDrawingByIds(Long[] ids);
}
