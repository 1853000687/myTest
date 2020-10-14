package com.edu.search.dao;

import com.edu.common.bean.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {
    SearchResult query(SolrQuery query) throws  Exception;
}
