package com.example.cf.manager.controller;

import com.example.cf.manager.domain.ProblemInfo;
import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.dto.ProblemInfoDto;
import com.example.cf.manager.dto.UserInfoDto;
import com.example.cf.manager.service.ProblemService;
import com.example.cf.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequiredArgsConstructor
@Controller
public class controller {

    private final UserService userService;
    private final ProblemService problemService;
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

    @GetMapping("/addproblem")
    public String addpview(Model model,Principal principal){
        model.addAttribute("form", new ProblemInfoDto());
        List<ProblemInfo> p=problemService.pinfo(principal.getName());
        model.addAttribute("problems",p);
        return "addproblem";
    }

    @PostMapping("/addproblem")
    public String addppost(@ModelAttribute("form") ProblemInfoDto problemDto,Principal principal){
        problemDto.setUserid(principal.getName());
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        problemDto.setAddedTime(format.format(new Date()));
        problemService.save(problemDto);
        return "redirect:/addproblem";
    }

    @GetMapping("/deleteproblem/{code}")
    public String deleteproblem(@PathVariable("code") String problemcode){
        Long code=Long.parseLong(problemcode);
        System.out.println(code);
        problemService.deleteProblem(code);
        return "redirect:/addproblem";
    }

    @GetMapping("/deletebookmark/{code}")
    public String deletebookmark(@PathVariable("code") String problemcode){
        Long code=Long.parseLong(problemcode);
        System.out.println(code);
        problemService.deleteBookmark(code);
        return "redirect:/mypage";
    }
    @GetMapping("/addbookmark/{code}")
    public String addbookmark(@PathVariable("code") String problemcode){
        Long code=Long.parseLong(problemcode);
        System.out.println(code);
        problemService.addBookmark(code);
        return "redirect:/mypage";
    }
    @GetMapping("/mypage")
    public String mypage(Model model,Principal principal){
        List<ProblemInfo> p=problemService.pinfo(principal.getName());
        model.addAttribute("problems",p);
        return "mypage";
    }

    @GetMapping("/question")
    public String question(){
        return "question";
    }

}
