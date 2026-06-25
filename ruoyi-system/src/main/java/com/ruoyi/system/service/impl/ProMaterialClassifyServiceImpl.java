package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProMaterialClassifyMapper;
import com.ruoyi.system.domain.ProMaterialClassify;
import com.ruoyi.system.service.IProMaterialClassifyService;

/**
 * 物料分类（支持多级分类）Service业务层处理
 *
 * @author ruoyi
 * @date 2026-05-13
 */
@Service
public class ProMaterialClassifyServiceImpl implements IProMaterialClassifyService {
    @Autowired
    private ProMaterialClassifyMapper proMaterialClassifyMapper;

    /**
     * 查询物料分类（支持多级分类）
     *
     * @param id 物料分类（支持多级分类）主键
     * @return 物料分类（支持多级分类）
     */
    @Override
    public ProMaterialClassify selectProMaterialClassifyById(Long id) {
        return proMaterialClassifyMapper.selectProMaterialClassifyById(id);
    }

    /**
     * 查询物料分类（支持多级分类）列表
     *
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 物料分类（支持多级分类）
     */
    @Override
    public List<ProMaterialClassify> selectProMaterialClassifyList(ProMaterialClassify proMaterialClassify) {
        return proMaterialClassifyMapper.selectProMaterialClassifyList(proMaterialClassify);
    }

    /**
     * 新增物料分类（支持多级分类）
     *
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 结果
     */
    @Override
    public int insertProMaterialClassify(ProMaterialClassify proMaterialClassify) {
        proMaterialClassify.setCreateBy(SecurityUtils.getUsername());
        proMaterialClassify.setCreateTime(DateUtils.getNowDate());
        return proMaterialClassifyMapper.insertProMaterialClassify(proMaterialClassify);
    }

    /**
     * 修改物料分类（支持多级分类）
     *
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 结果
     */
    @Override
    public int updateProMaterialClassify(ProMaterialClassify proMaterialClassify) {
        proMaterialClassify.setUpdateBy(SecurityUtils.getUsername());
        proMaterialClassify.setUpdateTime(DateUtils.getNowDate());
        return proMaterialClassifyMapper.updateProMaterialClassify(proMaterialClassify);
    }

    /**
     * 批量删除物料分类（支持多级分类）
     *
     * @param ids 需要删除的物料分类（支持多级分类）主键
     * @return 结果
     */
    @Override
    public int deleteProMaterialClassifyByIds(Long[] ids) {
        return proMaterialClassifyMapper.deleteProMaterialClassifyByIds(ids);
    }

    /**
     * 删除物料分类（支持多级分类）信息
     *
     * @param id 物料分类（支持多级分类）主键
     * @return 结果
     */
    @Override
    public int deleteProMaterialClassifyById(Long id) {
        return proMaterialClassifyMapper.deleteProMaterialClassifyById(id);
    }

    /**
     * 查询物料分类树列表
     *
     * @param proMaterialClassify 物料分类（支持多级分类）
     * @return 物料分类（支持多级分类）集合
     */
    @Override
    public List<ProMaterialClassify> selectProMaterialClassifyTreeList(ProMaterialClassify proMaterialClassify) {
        // 查询所有启用的分类
        proMaterialClassify.setEnable("1");
        List<ProMaterialClassify> list = proMaterialClassifyMapper.selectProMaterialClassifyList(proMaterialClassify);

        // 构建树形结构
        return buildClassifyTree(list, 0L);
    }

    /**
     * 构建物料分类树
     *
     * @param classifyList 分类列表
     * @param parentId     父级ID
     * @return 树形结构
     */
    private List<ProMaterialClassify> buildClassifyTree(List<ProMaterialClassify> classifyList, Long parentId) {
        List<ProMaterialClassify> treeList = new java.util.ArrayList<>();
        for (ProMaterialClassify classify : classifyList) {
            if (classify.getParentClassifyId().equals(parentId)) {
                // 递归查找子节点
                List<ProMaterialClassify> children = buildClassifyTree(classifyList, classify.getId());
                if (!children.isEmpty()) {
                    classify.setChildren(children);
                }
                treeList.add(classify);
            }
        }
        return treeList;
    }
}

