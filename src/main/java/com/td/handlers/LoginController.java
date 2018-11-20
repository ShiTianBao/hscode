package com.td.handlers;

import com.td.model.LoginModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping("checkLogin")
    @ResponseBody
    public LoginModel checkLogin(HttpServletResponse response, String username, String password) {
        System.out.println(username);
        LoginModel log = new LoginModel();
        if(username.equals("test") && password.equals("16d7a4fca7442dda3ad93c9a726597e4")){
            log.setMsg("success");
            Cookie cookie = new Cookie("tdUser", username);
            cookie.setPath("/");
            System.out.println("已添加===============");
            response.addCookie(cookie);
        }else{
            log.setMsg("error");
        }
        return log;
    }
}
