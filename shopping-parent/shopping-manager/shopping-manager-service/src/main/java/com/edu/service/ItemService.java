package com.edu.service;

import com.edu.bean.TbItem;
import com.edu.common.bean.EUDataGridResult;
import com.edu.common.bean.ShoppingResult;

public interface ItemService {
    EUDataGridResult getAll(int page, int rows);

    ShoppingResult insertIterm(TbItem item, String desc, String itemParams);
}
