package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.WfProcessDefinitionMapper;
import com.ruoyi.system.domain.WfProcessDefinition;
import com.ruoyi.system.service.IWfProcessDefinitionService;

/**
 * 流程定义Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class WfProcessDefinitionServiceImpl implements IWfProcessDefinitionService
{
    @Autowired
    private WfProcessDefinitionMapper wfProcessDefinitionMapper;

    /**
     * 查询流程定义
     *
     * @param id 流程定义主键
     * @return 流程定义
     */
    @Override
    public WfProcessDefinition selectWfProcessDefinitionById(Long id)
    {
        return wfProcessDefinitionMapper.selectWfProcessDefinitionById(id);
    }

    /**
     * 根据流程标识查询流程定义
     *
     * @param processKey 流程标识
     * @return 流程定义
     */
    @Override
    public WfProcessDefinition selectWfProcessDefinitionByKey(String processKey)
    {
        return wfProcessDefinitionMapper.selectWfProcessDefinitionByKey(processKey);
    }

    /**
     * 查询流程定义列表
     *
     * @param wfProcessDefinition 流程定义
     * @return 流程定义
     */
    @Override
    public List<WfProcessDefinition> selectWfProcessDefinitionList(WfProcessDefinition wfProcessDefinition)
    {
        return wfProcessDefinitionMapper.selectWfProcessDefinitionList(wfProcessDefinition);
    }

    /**
     * 新增流程定义
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    @Override
    public int insertWfProcessDefinition(WfProcessDefinition wfProcessDefinition)
    {
        return wfProcessDefinitionMapper.insertWfProcessDefinition(wfProcessDefinition);
    }

    /**
     * 修改流程定义
     *
     * @param wfProcessDefinition 流程定义
     * @return 结果
     */
    @Override
    public int updateWfProcessDefinition(WfProcessDefinition wfProcessDefinition)
    {
        return wfProcessDefinitionMapper.updateWfProcessDefinition(wfProcessDefinition);
    }

    /**
     * 批量删除流程定义
     *
     * @param ids 需要删除的流程定义主键
     * @return 结果
     */
    @Override
    public int deleteWfProcessDefinitionByIds(Long[] ids)
    {
        return wfProcessDefinitionMapper.deleteWfProcessDefinitionByIds(ids);
    }

    /**
     * 删除流程定义信息
     *
     * @param id 流程定义主键
     * @return 结果
     */
    @Override
    public int deleteWfProcessDefinitionById(Long id)
    {
        return wfProcessDefinitionMapper.deleteWfProcessDefinitionById(id);
    }
}
