package com.edu.service.impl;

import com.edu.bean.TbItemParam;
import com.edu.bean.TbItemParamExample;
import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.mapper.TbItemParamMapper;
import com.edu.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper ;
    @Override
    public EUDataGridResult getAll(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo pageInfo = new PageInfo(itemParams);
        EUDataGridResult dataGridResult = new EUDataGridResult();
        dataGridResult.setRows(pageInfo.getList());
        dataGridResult.setTotal(pageInfo.getTotal());
        return dataGridResult;
    }

    @Override
    public ShoppingResult getItemParamByCategoryId(Long categoryId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(categoryId);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        if(itemParams != null && itemParams.size() >0) {
            return ShoppingResult.ok(itemParams.get(0));
        }
        return ShoppingResult.build(500,"商品不存在");
    }

    @Override
    public ShoppingResult insertItemParam(Long categoryId, String paramData) {
        // 补全数据
        TbItemParam itemParam = new TbItemParam() ;
        itemParam.setItemCatId(categoryId);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insertSelective(itemParam);
        return ShoppingResult.ok();
    }
}
