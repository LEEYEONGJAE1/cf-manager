package com.example.cf.manager.controller;

import com.example.cf.manager.domain.*;
import com.example.cf.manager.dto.CommentInfoDto;
import com.example.cf.manager.dto.PostingInfoDto;
import com.example.cf.manager.dto.ProblemInfoDto;
import com.example.cf.manager.dto.UserInfoDto;
import com.example.cf.manager.service.CommentService;
import com.example.cf.manager.service.PostingService;
import com.example.cf.manager.service.ProblemService;
import com.example.cf.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }


    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) { // 회원 추가
        userService.save(infoDto);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        List<UserInfo> infoList=userService.getAllUserInfo();
        model.addAttribute("UserInfo",infoList);
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList()); //온라인 저지 사이트 목록
        return "admin";
    }

    @PostMapping("/deleteuser")
    public String delete(UserInfoDto userInfoDto,Authentication authentication)
    {

        userService.deleteUser(userInfoDto.getCode());
        return "redirect:/admin";
    }

    @RequestMapping(value="/idcheck",method = RequestMethod.POST)
    @ResponseBody
    public int validId(UserInfoDto userInfoDto){
       if(userService.isUserExistById(userInfoDto.getUserid())){
            return 0;
        }
        else{
            return 1;
        }
    }
}
