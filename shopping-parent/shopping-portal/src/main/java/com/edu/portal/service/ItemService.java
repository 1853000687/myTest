package com.edu.portal.service;

import com.edu.bean.TbItemDesc;
import com.edu.portal.bean.ItemCustomer;

public interface ItemService {
    ItemCustomer getItemById(long itemId);

    TbItemDesc getItemDesc(long itemId);

    String getItemParam(long itemId);
}
