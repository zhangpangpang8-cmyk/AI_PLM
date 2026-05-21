package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmTestReportMapper;
import com.ruoyi.system.domain.DmTestReport;
import com.ruoyi.system.service.IDmTestReportService;

/**
 * 测试报告Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-21
 */
@Service
public class DmTestReportServiceImpl implements IDmTestReportService 
{
    @Autowired
    private DmTestReportMapper dmTestReportMapper;

    /**
     * 查询测试报告
     * 
     * @param id 测试报告主键
     * @return 测试报告
     */
    @Override
    public DmTestReport selectDmTestReportById(Long id)
    {
        return dmTestReportMapper.selectDmTestReportById(id);
    }

    /**
     * 查询测试报告列表
     * 
     * @param dmTestReport 测试报告
     * @return 测试报告
     */
    @Override
    public List<DmTestReport> selectDmTestReportList(DmTestReport dmTestReport)
    {
        return dmTestReportMapper.selectDmTestReportList(dmTestReport);
    }

    /**
     * 新增测试报告
     * 
     * @param dmTestReport 测试报告
     * @return 结果
     */
    @Override
    public int insertDmTestReport(DmTestReport dmTestReport)
    {
        dmTestReport.setCreateTime(DateUtils.getNowDate());
        return dmTestReportMapper.insertDmTestReport(dmTestReport);
    }

    /**
     * 修改测试报告
     * 
     * @param dmTestReport 测试报告
     * @return 结果
     */
    @Override
    public int updateDmTestReport(DmTestReport dmTestReport)
    {
        dmTestReport.setUpdateTime(DateUtils.getNowDate());
        return dmTestReportMapper.updateDmTestReport(dmTestReport);
    }

    /**
     * 批量删除测试报告
     * 
     * @param ids 需要删除的测试报告主键
     * @return 结果
     */
    @Override
    public int deleteDmTestReportByIds(Long[] ids)
    {
        return dmTestReportMapper.deleteDmTestReportByIds(ids);
    }

    /**
     * 删除测试报告信息
     * 
     * @param id 测试报告主键
     * @return 结果
     */
    @Override
    public int deleteDmTestReportById(Long id)
    {
        return dmTestReportMapper.deleteDmTestReportById(id);
    }
}
