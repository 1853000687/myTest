package com.edu.controller;

import com.edu.bean.TbItem;
import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这是商品的控制器
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService ;
    /**
     * page 是当前第几页   默认是1
     *
     * @param page
     * @param rows  是每页显示多少条数据 默认是30
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/list")
    public EUDataGridResult list(int page,int rows) {
        return itemService.getAll(page,rows);
    }
    @ResponseBody
    @RequestMapping("/item/save")
    public ShoppingResult save(TbItem item,String desc,String itemParams) {
        return itemService.insertIterm(item,desc,itemParams);
    }
}
