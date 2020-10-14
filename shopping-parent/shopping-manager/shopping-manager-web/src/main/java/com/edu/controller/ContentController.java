package com.edu.controller;

import com.edu.bean.TbContent;
import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这是一个内容的控制器
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService ;
    @ResponseBody
    @RequestMapping("/query/list")
    public EUDataGridResult list(Long categoryId,int page,int rows) {
        return contentService.getContentByCategoryId(categoryId,page,rows);
    }
    @ResponseBody
    @RequestMapping("/save")
    public ShoppingResult save(TbContent content) {
        return contentService.saveContent(content);

    }
}
