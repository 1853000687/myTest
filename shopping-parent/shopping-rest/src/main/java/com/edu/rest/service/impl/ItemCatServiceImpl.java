package com.edu.rest.service.impl;

import com.edu.bean.TbItemCat;
import com.edu.bean.TbItemCatExample;
import com.edu.mapper.TbItemCatMapper;
import com.edu.rest.bean.CatNode;
import com.edu.rest.bean.CatNodeResult;
import com.edu.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper ;
    @Override
    public CatNodeResult getAll() {
        CatNodeResult catNodeResult = new CatNodeResult() ;
        catNodeResult.setData(getAllCatByParentId(0));
        return catNodeResult;
    }

    private List<?> getAllCatByParentId(long parentId) {
        List lists = new ArrayList();
        TbItemCatExample example = new TbItemCatExample() ;
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId) ;
        // 这是所有的一级分类
        List<TbItemCat> topItemCat = itemCatMapper.selectByExample(example);
        int count = 0;
        for(TbItemCat itemCat:topItemCat) {// 开始迭代一级分类
            // 出现一个对象
            CatNode catNode = new CatNode() ;
            // 判断是父节点还是叶子节点
            if(itemCat.getIsParent()) {
                // 判断它是一级节点，还是不是一级
                if(parentId == 0) {
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                catNode.setItems(getAllCatByParentId(itemCat.getId()));// 递归调用
                lists.add(catNode);
                count ++;
                if(count >=14 && parentId==0) {
                    break;
                }
            } else {
                // 叶子节点
                lists.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }

        }
        return lists;
    }
}
