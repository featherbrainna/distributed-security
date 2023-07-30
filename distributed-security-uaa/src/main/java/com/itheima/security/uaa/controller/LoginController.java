package com.itheima.security.uaa.controller;

import com.itheima.security.uaa.utils.SysConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/22 16:57
 */
@Controller
public class LoginController {

//    @PreAuthorize("hasAuthority('r1')")
    @GetMapping(value = "/r/r1",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String r1(HttpSession session){

        return getUsername()+"访问资源r1！";
    }

//    @PreAuthorize("hasAuthority('r2')")
    @GetMapping(value = "/r/r2",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String r2(HttpSession session){
        return getUsername()+"访问资源r2！";
    }

    @GetMapping(value = "/loginsuccess")
    public String loginsuccess(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute(SysConstant.USER_NAME,userDetails.getUsername());
        return "loginsuccess";
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
        if (principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        }else {
            return principal.toString();
        }
    }
}
