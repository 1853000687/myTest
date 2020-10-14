package com.edu.search.service;

import com.edu.common.bean.SearchResult;

public interface SearchService {
    SearchResult query(String queryString, int page, int rows) throws Exception;
}
