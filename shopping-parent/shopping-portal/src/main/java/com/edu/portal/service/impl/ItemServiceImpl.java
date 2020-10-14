package com.edu.portal.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemParamItem;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.common.util.JsonUtils;
import com.edu.portal.bean.ItemCustomer;
import com.edu.portal.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Value("${CONTENT_BASE_URL}")
    private String CONTENT_BASE_URL ;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;
    @Value("${ITEM_INFO_DESC}")
    private String ITEM_INFO_DESC;
    @Value("${ITEM_INFO_PARAM}")
    private String ITEM_INFO_PARAM;
    @Override
    public ItemCustomer getItemById(long itemId) {
        ItemCustomer itemCustomer = null;
        // 调用远程服务 http://localhost:8081/rest/item/info/1433500495290
        String itemInfoString = HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_INFO_URL + itemId);
        // 把json格式的字符串转换成shoppingresult对象
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfoString, TbItem.class);
        if(shoppingResult.getStatus() == 200) {
            TbItem tbItem = (TbItem) shoppingResult.getData();
            if(tbItem != null) {
                itemCustomer = new ItemCustomer() ;
                BeanUtils.copyProperties(tbItem,itemCustomer);
            }
        }
        return itemCustomer;
    }

    @Override
    public TbItemDesc getItemDesc(long itemId) {

        String itemDesc = HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_INFO_DESC + itemId);
        // 把json格式的字符串转换成TbItemDesc对象
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemDesc, TbItemDesc.class);
        if(shoppingResult.getStatus() == 200) {
            return (TbItemDesc) shoppingResult.getData();
        }
        return null;
    }

    @Override
    public String getItemParam(long itemId) {
        String itemParam = HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_INFO_PARAM + itemId);
        // 把json格式的字符串转换成TbItemDesc对象
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemParam, TbItemParamItem.class);
        StringBuilder sb = new StringBuilder();
        if(shoppingResult.getStatus() == 200) {
            TbItemParamItem tbItemParamItem = (TbItemParamItem)shoppingResult.getData();
            String paramData = tbItemParamItem.getParamData();
            // 把这个字符串转换List集合
            List<Map> maps = JsonUtils.jsonToList(paramData, Map.class);

            sb.append("<table class=\"tm-tableAttr\">\n");
            sb.append("	<thead>\n");
            sb.append("		<tr>\n");
            sb.append("			<td colspan=\"2\" data-spm-anchor-id=\"a220o.1000855.0.i0.66b829003o3V0f\">规格参数</td>\n");
            sb.append("		</tr>\n");
            sb.append("	</thead>\n");
            sb.append("	<tbody>\n");
            for(Map map:maps) {
                sb.append("		<tr class=\"tm-tableAttrSub\">\n");
                sb.append("			<th colspan=\"2\" data-spm-anchor-id=\"a220o.1000855.0.i2.66b829003o3V0f\">"+map.get("group")+"</th>\n");
                sb.append("		</tr>\n");
                List<Map> childrens = (List<Map>) map.get("params");
                for(Map child:childrens) {
                    sb.append("		<tr>\n");
                    sb.append("			<th data-spm-anchor-id=\"a220o.1000855.0.i1.66b829003o3V0f\">"+child.get("k")+"</th>\n");
                    sb.append("			<td data-spm-anchor-id=\"a220o.1000855.0.i4.66b829003o3V0f\">&nbsp;"+child.get("v")+"\n");
                    sb.append("				Pro+）</td>\n");
                    sb.append("		</tr>\n");
                }

            }
            sb.append("	</tbody>\n");
            sb.append("</table>");
        }
        return sb.toString();
    }
}
