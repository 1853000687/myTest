package com.edu.service;

import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;

import java.util.List;

public interface ContentCategoryService {
    List<EUTreeResult> getAll(Long parentId);

    ShoppingResult saveContentCategory(Long parentId, String name);

    ShoppingResult updateContentCategory(Long id, String name);

    ShoppingResult deleteContentCategoryById(Long id);
}
