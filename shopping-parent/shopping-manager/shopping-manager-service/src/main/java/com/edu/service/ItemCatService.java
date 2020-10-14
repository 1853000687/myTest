package com.edu.service;

import com.edu.common.bean.EUTreeResult;

import java.util.List;

public interface ItemCatService {
    List<EUTreeResult> getAll(Long id);
}
