package com.edu.rest.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemParamItem;
import com.edu.bean.TbItemParamItemExample;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbItemDescMapper;
import com.edu.mapper.TbItemMapper;
import com.edu.mapper.TbItemParamItemMapper;
import com.edu.rest.dao.JedisDao;
import com.edu.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private JedisDao jedisDao ;
    @Autowired
    private TbItemMapper itemMapper ;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Value("${REDIS_ITEM_BASE}")
    private String REDIS_ITEM_BASE;
    @Value("${REDIS_ITEE_EXPIRE}")
    private int REDIS_ITEE_EXPIRE;
    @Override
    public ShoppingResult getItemInfo(long itemId) {

        TbItem tbItem = null ;
        // 首先从缓存中找
        // 找到了直接返回
        try {
            String itemInfo = jedisDao.get(REDIS_ITEM_BASE+":"+itemId+":INFO");
            if(!StringUtils.isEmpty(itemInfo)) {
                // 把json格式的字符串转换成对象(TbItem)
                tbItem = JsonUtils.jsonToPojo(itemInfo, TbItem.class);
                return ShoppingResult.ok(tbItem);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        if(tbItem == null) {
            // 没有找到从数据库中去找，找到了之后，向缓存放一份
            tbItem = itemMapper.selectByPrimaryKey(itemId);
            try {
                jedisDao.set(REDIS_ITEM_BASE+":"+itemId+":INFO",JsonUtils.objectToJson(tbItem));
                // 设置一下缓存的过期时间
                jedisDao.expire(REDIS_ITEM_BASE+":"+itemId+":INFO",REDIS_ITEE_EXPIRE);
                return ShoppingResult.ok(tbItem);
            } catch (Exception e) {
                e.printStackTrace();
                return ShoppingResult.build(500,"出错了");
            }
        }

        return ShoppingResult.build(500,"出错了....");
    }

    /**
     * 这是商品的描述信息
     * @param itemId
     * @return
     */
    @Override
    public ShoppingResult getDescInfo(long itemId) {
        TbItemDesc tbItemDesc = null;
        try {
            String itemInfo = jedisDao.get(REDIS_ITEM_BASE+":"+itemId+":DESC");
            if(!StringUtils.isEmpty(itemInfo)) {
                // 把json格式的字符串转换成对象(TbItem)
                tbItemDesc = JsonUtils.jsonToPojo(itemInfo, TbItemDesc.class);
                return ShoppingResult.ok(tbItemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null == tbItemDesc) {
            tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
            try {
                jedisDao.set(REDIS_ITEM_BASE+":"+itemId+":DESC",JsonUtils.objectToJson(tbItemDesc));
                // 设置一下缓存的过期时间
                jedisDao.expire(REDIS_ITEM_BASE+":"+itemId+":DESC",REDIS_ITEE_EXPIRE);
                return ShoppingResult.ok(tbItemDesc);
            } catch (Exception e) {
                e.printStackTrace();
                return ShoppingResult.build(500,"出错了");
            }
        }
        return null;
    }

    @Override
    public ShoppingResult getParamInfo(long itemId) {
        TbItemParamItem tbItemParamItem = null ;
        try {
            String itemInfo = jedisDao.get(REDIS_ITEM_BASE+":"+itemId+":PARAM");
            if(!StringUtils.isEmpty(itemInfo)) {
                // 把json格式的字符串转换成对象(TbItem)
                tbItemParamItem = JsonUtils.jsonToPojo(itemInfo, TbItemParamItem.class);
                return ShoppingResult.ok(tbItemParamItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null == tbItemParamItem) {
            TbItemParamItemExample example = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = example.createCriteria();
            criteria.andItemIdEqualTo(itemId);
            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
            if(null != tbItemParamItems && tbItemParamItems.size() > 0) {
                tbItemParamItem = tbItemParamItems.get(0);
                try {
                    jedisDao.set(REDIS_ITEM_BASE+":"+itemId+":PARAM",JsonUtils.objectToJson(tbItemParamItem));
                    // 设置一下缓存的过期时间
                    jedisDao.expire(REDIS_ITEM_BASE+":"+itemId+":PARAM",REDIS_ITEE_EXPIRE);
                    return ShoppingResult.ok(tbItemParamItem);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ShoppingResult.build(500,"出错了");
                }
            }

        }
        return null;
    }
}
