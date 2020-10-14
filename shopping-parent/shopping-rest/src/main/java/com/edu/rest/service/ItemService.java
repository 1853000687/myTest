package com.edu.rest.service;

import com.edu.common.bean.ShoppingResult;

public interface ItemService {
    ShoppingResult getItemInfo(long itemId);

    ShoppingResult getDescInfo(long itemId);

    ShoppingResult getParamInfo(long itemId);
}
