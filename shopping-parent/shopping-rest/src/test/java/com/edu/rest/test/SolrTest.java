package com.edu.rest.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 使用java代码去操作我们的solr服务
 * 1.添加solr对应的jar
 */
public class SolrTest {
    @Test
    public void test3() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://60.205.252.145:8080/solr/");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");// 设置查询条件
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        for(SolrDocument document:results) {
            String id = (String) document.get("id");
            String item_title = (String) document.get("item_title");
            System.out.println(id + "::" + item_title);
        }
    }
    @Test
    public void testDeleteById() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://60.205.252.145:8080/solr/");
       // solrServer.deleteById("test001");
        solrServer.deleteByQuery("*:*");
       solrServer.commit();
    }
    @Test
    public void testAdd() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://60.205.252.145:8080/solr/");
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id","test001");
        document.setField("item_title","测试商品2");
        document.setField("item_price",6000);
        solrServer.add(document);
        solrServer.commit();
    }
}
