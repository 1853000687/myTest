package com.edu.rest.controller;

import com.edu.common.bean.ShoppingResult;
import com.edu.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService ;
    // 商品的基本信息 商品的id  单表查询，， tbItem  ----->返回的数据都是json格式，ShoppingResult
    @ResponseBody
    @RequestMapping("/info/{itemId}")
    public ShoppingResult info(@PathVariable("itemId") long itemId) {
        ShoppingResult result = itemService.getItemInfo(itemId);
        if(result.getStatus() == 200) {
            return result ;
        } else {
            return ShoppingResult.build(500, "出现错误");
        }
    }

    // 商品的描述信息 商品的id  单表查询  TbItemDesc ---------> ShoppingResult
    @ResponseBody
    @RequestMapping("/desc/{itemId}")
    public ShoppingResult desc(@PathVariable("itemId") long itemId) {
        ShoppingResult result = itemService.getDescInfo(itemId);
        if(result.getStatus() == 200) {
            return result ;
        } else {
            return ShoppingResult.build(500, "出现错误");
        }
    }

    // 商品的规格参数信息 商品的id  单表查询  TbItemparamItem -------------->shoppingResult
    @ResponseBody
    @RequestMapping("/param/{itemId}")
    public ShoppingResult param(@PathVariable("itemId") long itemId) {
        ShoppingResult result = itemService.getParamInfo(itemId);
        if(null == result) {
            return ShoppingResult.build(400,"没有数据.....");
        }
        if(result.getStatus() == 200) {
            return result ;
        } else {
            return ShoppingResult.build(500, "出现错误");
        }
    }
}
