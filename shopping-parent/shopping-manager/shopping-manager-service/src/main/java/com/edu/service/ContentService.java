package com.edu.service;

import com.edu.bean.TbContent;
import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;

public interface ContentService {
    EUDataGridResult getContentByCategoryId(Long categoryId,int page,int rows);

    ShoppingResult saveContent(TbContent content);
}
