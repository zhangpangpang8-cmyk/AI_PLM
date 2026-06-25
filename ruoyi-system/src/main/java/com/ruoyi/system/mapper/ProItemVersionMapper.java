package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ProItemVersion;

/**
 * 物料版本历史Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
public interface ProItemVersionMapper 
{
    /**
     * 查询物料版本历史列表
     * 
     * @param proItemVersion 物料版本历史
     * @return 物料版本历史集合
     */
    public List<ProItemVersion> selectProItemVersionList(ProItemVersion proItemVersion);

    /**
     * 新增物料版本历史
     * 
     * @param proItemVersion 物料版本历史
     * @return 结果
     */
    public int insertProItemVersion(ProItemVersion proItemVersion);
}
