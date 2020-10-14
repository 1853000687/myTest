package com.edu.service;

import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;

public interface ItemParamService {
    EUDataGridResult getAll(int page, int rows);

    ShoppingResult getItemParamByCategoryId(Long categoryId);

    ShoppingResult insertItemParam(Long categoryId, String paramData);
}
