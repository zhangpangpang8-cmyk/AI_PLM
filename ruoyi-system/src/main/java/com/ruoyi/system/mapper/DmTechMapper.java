package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmTech;

/**
 * 技术文档Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface DmTechMapper 
{
    /**
     * 查询技术文档
     * 
     * @param id 技术文档主键
     * @return 技术文档
     */
    public DmTech selectDmTechById(Long id);

    /**
     * 查询技术文档列表
     * 
     * @param dmTech 技术文档
     * @return 技术文档集合
     */
    public List<DmTech> selectDmTechList(DmTech dmTech);

    /**
     * 新增技术文档
     * 
     * @param dmTech 技术文档
     * @return 结果
     */
    public int insertDmTech(DmTech dmTech);

    /**
     * 修改技术文档
     * 
     * @param dmTech 技术文档
     * @return 结果
     */
    public int updateDmTech(DmTech dmTech);

    /**
     * 删除技术文档
     * 
     * @param id 技术文档主键
     * @return 结果
     */
    public int deleteDmTechById(Long id);

    /**
     * 批量删除技术文档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmTechByIds(Long[] ids);
}
