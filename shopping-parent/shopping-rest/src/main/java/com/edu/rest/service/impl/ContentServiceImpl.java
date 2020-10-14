package com.edu.rest.service.impl;

import com.edu.bean.TbContent;
import com.edu.bean.TbContentExample;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbContentMapper;
import com.edu.rest.dao.JedisDao;
import com.edu.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Value("${INDEX_CACHE_CONTENT_PIC}")
    private String INDEX_CACHE_CONTENT_PIC;
    @Autowired
    private JedisDao jedisDao ;
    @Override
    public List<Map<String, Object>> getAllByCategoryId(Long categoryId) {
        List<TbContent> contentList = null;
        // 先从缓存中找，如果有直接返回
        try{
            String str = jedisDao.hget(INDEX_CACHE_CONTENT_PIC,categoryId+"");
            if(!StringUtils.isEmpty(str)) {
                contentList = JsonUtils.jsonToList(str,TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(contentList == null) {
            TbContentExample example = new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(categoryId);
            contentList = contentMapper.selectByExampleWithBLOBs(example);
            // 把数据放入到缓存中
            try {
                String result = JsonUtils.objectToJson(contentList);
                jedisDao.hset(INDEX_CACHE_CONTENT_PIC,categoryId+"",result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        List<Map<String,Object>> results = new ArrayList<>();
        for(TbContent content:contentList) {
            // srcB : "http://image.shopping.com/images/2015/03/03/2015030304360302109345.jpg"
            Map<String,Object> map = new HashMap<>();
            map.put("src",content.getPic());
            // height : 240
            map.put("height",240);
            map.put("alt",content.getSubTitle());
            map.put("width",670);

            map.put("srcB",content.getPic2());
            map.put("widthB",550);
            map.put("heightB",240);
            map.put("href",content.getUrl());
            results.add(map);

        }
        return results;
    }
}
