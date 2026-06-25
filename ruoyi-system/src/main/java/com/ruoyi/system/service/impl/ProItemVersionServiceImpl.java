package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProItemVersionMapper;
import com.ruoyi.system.domain.ProItemVersion;
import com.ruoyi.system.service.IProItemVersionService;

/**
 * 物料版本历史Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-09
 */
@Service
public class ProItemVersionServiceImpl implements IProItemVersionService 
{
    @Autowired
    private ProItemVersionMapper proItemVersionMapper;

    /**
     * 查询物料版本历史列表
     * 
     * @param proItemVersion 物料版本历史
     * @return 物料版本历史集合
     */
    @Override
    public List<ProItemVersion> selectProItemVersionList(ProItemVersion proItemVersion)
    {
        return proItemVersionMapper.selectProItemVersionList(proItemVersion);
    }

    /**
     * 新增物料版本历史
     * 
     * @param proItemVersion 物料版本历史
     * @return 结果
     */
    @Override
    public int insertProItemVersion(ProItemVersion proItemVersion)
    {
        proItemVersion.setCreateTime(new Date());
        return proItemVersionMapper.insertProItemVersion(proItemVersion);
    }
}
