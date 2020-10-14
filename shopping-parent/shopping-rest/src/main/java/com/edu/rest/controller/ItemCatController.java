package com.edu.rest.controller;

import com.edu.common.util.JsonUtils;
import com.edu.rest.bean.CatNodeResult;
import com.edu.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService ;
//    @ResponseBody
//    @RequestMapping(value = "/itemcat/all",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
//    public String all(String callback) {
//        CatNodeResult result = itemCatService.getAll();
//        // 把这个对象转换成json数据格式
//        String str =  JsonUtils.objectToJson(result);
//        return callback +"(" + str +");";
//    }

    @ResponseBody
    @RequestMapping(value = "/itemcat/all")
    public Object all(String callback) {
        CatNodeResult result = itemCatService.getAll();
        // 把这个对象转换成json数据格式
        MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }
}
