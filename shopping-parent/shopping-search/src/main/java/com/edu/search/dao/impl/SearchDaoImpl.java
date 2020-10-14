package com.edu.search.dao.impl;

import com.edu.common.bean.Item;
import com.edu.common.bean.SearchResult;
import com.edu.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询索引库
 */
@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrServer solrServer ;
    @Override
    public SearchResult query(SolrQuery query) throws Exception {
        SearchResult searchResult = new SearchResult() ;
        List<Item> items = new ArrayList<>();
        QueryResponse response = solrServer.query(query);
        SolrDocumentList documentList = response.getResults();
        
        searchResult.setRowCount(documentList.getNumFound()); // 设置总的记录数
        // 取高亮的数据
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for(SolrDocument document:documentList) {
            Item item = new Item();
            String itemId = (String)document.get("id");
            item.setId(itemId);
            Map<String, List<String>> stringListMap = highlighting.get(itemId);
            List<String> titles = stringListMap.get("item_title");
            String title = null ;
            if(null != titles && titles.size()>0) {
                title = titles.get(0);
            } else {
                title = (String)document.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String)document.get("item_image"));
            item.setCategory_name((String)document.get("item_category_name"));
            item.setSell_point((String)document.get("item_sell_point"));
            item.setPrice((Long)document.get("item_price"));
            items.add(item);
        }
        searchResult.setItemList(items);
        return searchResult;
    }
}
