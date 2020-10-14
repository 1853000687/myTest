package com.edu.search.service.impl;

import com.edu.common.bean.SearchResult;
import com.edu.search.dao.SearchDao;
import com.edu.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao ;
    @Override
    public SearchResult query(String queryString, int page, int rows) throws Exception{
        SolrQuery query = new SolrQuery();
        // 设置查询条件
        query.setQuery(queryString);
        // 设置起始位置的索引 select * from produdct limmit ?,?
        query.setStart(rows*(page -1));
        // 设置取多少条数据
        query.setRows(rows);
        // 设置查询域
        query.set("df","item_keywords");
        // 设置高亮显示
        query.setHighlight(true); // true false
        //  设置哪一个域来进行高亮显示
        query.addHighlightField("item_title") ;
        // 设置高亮显示的前缀
        query.setHighlightSimplePre("<em style=\"color:red;\">");
        query.setHighlightSimplePost("</em>");
        SearchResult searchResult = searchDao.query(query);
        searchResult.setPage(page);
        long pageCount = searchResult.getRowCount() % rows == 0 ? searchResult.getRowCount()/rows :(searchResult.getRowCount()/rows + 1);
        searchResult.setPageCount(pageCount);
        return searchResult;
    }
}
