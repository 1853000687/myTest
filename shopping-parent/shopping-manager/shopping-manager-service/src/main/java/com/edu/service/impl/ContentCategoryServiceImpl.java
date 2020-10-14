package com.edu.service.impl;

import com.edu.bean.TbContentCategory;
import com.edu.bean.TbContentCategoryExample;
import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.mapper.TbContentCategoryMapper;
import com.edu.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper ;
    @Override
    public List<EUTreeResult> getAll(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
        List<EUTreeResult> results = new ArrayList<>();
        for(TbContentCategory contentCategory:contentCategories) {
            EUTreeResult result = new EUTreeResult() ;
            result.setId(contentCategory.getId());
            result.setText(contentCategory.getName());
            result.setState(contentCategory.getIsParent()?"closed":"open");
            results.add(result);
        }
        return results;
    }

    @Override
    public ShoppingResult saveContentCategory(Long parentId, String name) {
        // 补全数据
        TbContentCategory contentCategory = new TbContentCategory() ;
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        contentCategory.setParentId(parentId);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.insertSelective(contentCategory);
        // 判断当前的节点是不是叶子节点，如果是叶子节点，则把页子节点更新父节点
        TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentCategory.getIsParent()) {
            parentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKeySelective(parentCategory);
        }
        return ShoppingResult.ok(contentCategory);
    }

    @Override
    public ShoppingResult updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return ShoppingResult.ok();
    }

    @Override
    public ShoppingResult deleteContentCategoryById(Long id) {
        // 根据id获取当前分类
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
         // 判断当前分类是否是叶子
        if(contentCategory.getIsParent()) {
            caseCadeAll(id);
        } else {
            // 这是叶子节点，
            contentCategoryMapper.deleteByPrimaryKey(id);
        }
        return ShoppingResult.ok();
    }

    private void caseCadeAll(Long id) {
        // 找它下面的所有的子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> topContentCategories = contentCategoryMapper.selectByExample(example);
        for(TbContentCategory contentCategory:topContentCategories) {
            // 判断它是父节点还是子节点
            if(contentCategory.getIsParent()) {
                caseCadeAll(contentCategory.getId());
            } else {
                // 子节点直接删除
                contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
            }
        }
        // 删除当前的节点
        contentCategoryMapper.deleteByPrimaryKey(id);
    }
}
