package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmTestReport;

/**
 * 测试报告Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-21
 */
public interface DmTestReportMapper 
{
    /**
     * 查询测试报告
     * 
     * @param id 测试报告主键
     * @return 测试报告
     */
    public DmTestReport selectDmTestReportById(Long id);

    /**
     * 查询测试报告列表
     * 
     * @param dmTestReport 测试报告
     * @return 测试报告集合
     */
    public List<DmTestReport> selectDmTestReportList(DmTestReport dmTestReport);

    /**
     * 新增测试报告
     * 
     * @param dmTestReport 测试报告
     * @return 结果
     */
    public int insertDmTestReport(DmTestReport dmTestReport);

    /**
     * 修改测试报告
     * 
     * @param dmTestReport 测试报告
     * @return 结果
     */
    public int updateDmTestReport(DmTestReport dmTestReport);

    /**
     * 删除测试报告
     * 
     * @param id 测试报告主键
     * @return 结果
     */
    public int deleteDmTestReportById(Long id);

    /**
     * 批量删除测试报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmTestReportByIds(Long[] ids);
}
