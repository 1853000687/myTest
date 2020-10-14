package com.edu.sso.service.impl;

import com.edu.bean.TbUser;
import com.edu.bean.TbUserExample;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.CookieUtils;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbUserMapper;
import com.edu.sso.dao.JedisDao;
import com.edu.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper ;
    @Autowired
    private JedisDao jedisDao;
    @Value("${REDIS_TOKEN}")
    private String REDIS_TOKEN;
    @Value("${REDIS_TOKEN_EXPIRE}")
    private int REDIS_TOKEN_EXPIRE;
    @Override
    public ShoppingResult getCheckUser(String param, int type) {
        TbUserExample example =  new TbUserExample() ;
        TbUserExample.Criteria criteria = example.createCriteria();
        // 数1、2、3分别代表username、phone、email
        if(1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if(2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if(3== type) {
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if(null != tbUsers && tbUsers.size() > 0) {
            // 存在
            return ShoppingResult.ok(false);
        }
        return ShoppingResult.ok(true);
    }

    @Override
    public ShoppingResult saveUser(TbUser user) {
        // 补全数据
       try {
           user.setCreated(new Date());
           user.setUpdated(new Date());
           // 密码要经过md5加密
           user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
           userMapper.insertSelective(user);
           return ShoppingResult.ok(user);
       } catch (Exception e) {
           return ShoppingResult.build(500,"新增出错了....");
       }

    }

    @Override
    public ShoppingResult getUserByNameAndPwd(String username, String password,
                                              HttpServletRequest request, HttpServletResponse response) {
        // 首先根据用户名去查询
        TbUserExample example =  new TbUserExample() ;
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if(null == tbUsers || tbUsers.size() == 0) {
            return ShoppingResult.build(500,"用户名或者密码错误....");
        }
        // 开始校验密码
        TbUser tbUser = tbUsers.get(0);
        if(!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return ShoppingResult.build(500,"用户名或者密码错误....");
        }
        // 产生一个token,这个令牌必须唯一
        String token = UUID.randomUUID().toString();
        // 把这个token所对用的用户写入到redis中
        jedisDao.set(REDIS_TOKEN +":"+token, JsonUtils.objectToJson(tbUser));
        // 设置token的失效时间
        jedisDao.expire(REDIS_TOKEN +":"+token,REDIS_TOKEN_EXPIRE);
        // 把token写入到用户浏览器的cookie
        CookieUtils.setCookie(request,response,"TT_TOKEN",token,true);
        return ShoppingResult.ok(token);
    }

    @Override
    public TbUser getByToken(String token,HttpServletRequest request, HttpServletResponse response) {
        String result = jedisDao.get(REDIS_TOKEN +":"+token);
        if(StringUtils.isEmpty(result)) {
            return null ;
        }
        CookieUtils.setCookie(request,response,"TT_TOKEN",token,true);
        return JsonUtils.jsonToPojo(result,TbUser.class);
    }

    @Override
    public ShoppingResult logout(String token) {
        jedisDao.expire(REDIS_TOKEN +":"+token,0);
        return ShoppingResult.ok();
    }
}
