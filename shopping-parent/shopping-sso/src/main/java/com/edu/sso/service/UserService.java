package com.edu.sso.service;

import com.edu.bean.TbUser;
import com.edu.common.bean.ShoppingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    ShoppingResult getCheckUser(String param, int type);

    ShoppingResult saveUser(TbUser user);

    ShoppingResult getUserByNameAndPwd(String username, String password, HttpServletRequest request, HttpServletResponse response);

    TbUser getByToken(String token,HttpServletRequest request, HttpServletResponse response);

    ShoppingResult logout(String token);
}
