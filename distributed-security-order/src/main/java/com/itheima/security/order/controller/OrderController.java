package com.itheima.security.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/28 8:08
 */
@RestController
public class OrderController {

    @PreAuthorize("hasAuthority('r1')")
    @GetMapping(value = "/r/r1",produces = "text/plain;charset=utf-8")
    public String r1(HttpSession session){

        return getUsername()+"访问资源r1！";
    }

    @PreAuthorize("hasAuthority('r2')")
    @GetMapping(value = "/r/r2",produces = "text/plain;charset=utf-8")
    public String r2(HttpSession session){
        return getUsername()+"访问资源r2！";
    }

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null){
            return "匿名";
        }
        Object principal = authentication.getPrincipal();
        if (principal == null){
            return "匿名";
        }
        if (principal instanceof Jwt){
            return ((Jwt) principal).getSubject();
        }else {
            return principal.toString();
        }
    }

}
