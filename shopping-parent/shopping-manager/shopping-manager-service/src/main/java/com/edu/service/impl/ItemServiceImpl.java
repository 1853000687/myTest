package com.edu.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemExample;
import com.edu.bean.TbItemParamItem;
import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.IDUtils;
import com.edu.mapper.TbItemDescMapper;
import com.edu.mapper.TbItemMapper;
import com.edu.mapper.TbItemParamItemMapper;
import com.edu.service.ItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper ;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper ;
    @Override
    public EUDataGridResult getAll(int page, int rows) {
        PageHelper.startPage(page,rows);
        // 获取所有的数据
        List<TbItem> tbItemList = itemMapper.selectByExample(new TbItemExample());
        PageInfo pageInfo = new PageInfo(tbItemList);
        EUDataGridResult dataGridResult = new EUDataGridResult() ;
        dataGridResult.setRows(pageInfo.getList());
        dataGridResult.setTotal(pageInfo.getTotal());

        return dataGridResult;
    }

    @Override
    public ShoppingResult insertIterm(TbItem item, String desc, String itemParams) {
        // 保存商品，
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 保存商品
        itemMapper.insertSelective(item);
        // 保存商品所对应的描述信息
        ShoppingResult result = insertItemDesc(itemId,desc);
        if(result.getStatus() == 200) {
            // 保存商品所对应的规格参数
            result =  insertItemParamItem(itemId,itemParams);
            if(result.getStatus() == 200) {
                return result ;
            } else {
                throw new RuntimeException();
            }
        } else {
            // 它一抛出异常，因为这个方法在事务管理中，spring框架就开始自动回滚。
            throw new RuntimeException();
        }
    }

    private ShoppingResult insertItemParamItem(long itemId, String itemParams) {
        TbItemParamItem itemParamItem = new TbItemParamItem() ;
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insertSelective(itemParamItem);
        return ShoppingResult.ok() ;
    }

    private ShoppingResult insertItemDesc(long itemId, String desc) {
        TbItemDesc itemDesc = new TbItemDesc() ;
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insertSelective(itemDesc);
        return ShoppingResult.ok();
    }
}
