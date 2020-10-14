package com.edu.portal.service.impl;

import com.edu.common.bean.SearchResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SOLR_BASE_URL}")
    private String SOLR_BASE_URL;

    @Override
    public SearchResult query(String q, int page) {
        // 远程调用服务 http://localhost:8083/search/query?q=手机
        Map<String,String> params = new HashMap<>();
        params.put("q",q);
        params.put("page",page+"");
        String result = HttpClientUtil.doGet(SOLR_BASE_URL,params);
        // SearchResult data中所存放的数据的类型
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(result,SearchResult.class);
        if(shoppingResult.getStatus() == 200) {
            return (SearchResult) shoppingResult.getData();
        }
        return null;
    }
}
