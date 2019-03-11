package com.td.controller;

import com.td.model.LoginModel;
import com.td.util.DBUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
public class LoginController {

    @RequestMapping("checkLogin")
    @ResponseBody
    public LoginModel checkLogin(HttpServletResponse response, String username, String password) {
        System.out.println(username);
        LoginModel log = new LoginModel();
        Connection conn = null;
        PreparedStatement pred = null;

        try{
            conn = DBUtils.getConnection();
            String sql = "select * from users where username = ? and password = ?";
            pred = conn.prepareStatement(sql);
            pred.setObject(1, username.trim());
            pred.setObject(2, password.trim());
            ResultSet res = pred.executeQuery();
            if(res.next()) {
                log.setMsg("success");
                Cookie cookie = new Cookie("tdUser", username);
                cookie.setPath("/");
                response.addCookie(cookie);
            }else{
                log.setMsg("error");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }
}
