package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ProItemVersion;

/**
 * 物料版本历史Service接口
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
public interface IProItemVersionService 
{
    /**
     * 查询物料版本历史列表
     * 
     * @param proItemVersion 物料版本历史
     * @return 物料版本历史集合
     */
    public List<ProItemVersion> selectProItemVersionList(ProItemVersion proItemVersion);

    /**
     * 新增物料版本历史（保存版本快照）
     * 
     * @param proItemVersion 物料版本历史
     * @return 结果
     */
    public int insertProItemVersion(ProItemVersion proItemVersion);
}
