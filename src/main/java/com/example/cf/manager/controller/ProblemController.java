package com.example.cf.manager.controller;

import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.domain.ProblemInfo;
import com.example.cf.manager.domain.SiteInfos;
import com.example.cf.manager.dto.ProblemInfoDto;
import com.example.cf.manager.service.CommentService;
import com.example.cf.manager.service.PostingService;
import com.example.cf.manager.service.ProblemService;
import com.example.cf.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ProblemController {
    @Autowired
    private final ProblemService problemService;

    @GetMapping("/problem/create")
    public String viewProblems(Model model, Principal principal){
        model.addAttribute("form", new ProblemInfoDto());
        List<ProblemInfo> p=problemService.pinfo(principal.getName());
        model.addAttribute("problems",p);
        return "problem/create";
    }

    @PostMapping("/problem/create")
    public String createProblem(@ModelAttribute("form") ProblemInfoDto problemDto, Principal principal){
        problemDto.setUserid(principal.getName());
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        problemDto.setAddedTime(format.format(new Date()));
        problemService.save(problemDto);
        return "redirect:/problem/create";
    }

    @GetMapping("/deleteproblem/{code}")
    public String deleteProblem(@PathVariable("code") Long code){
        problemService.deleteProblem(code);
        return "redirect:/problem/create";
    }

    @GetMapping("/deletebookmark/{code}")
    public String deleteBookmark(@PathVariable("code") Long code){
        problemService.deleteBookmark(code);
        return "redirect:/mypage";
    }

    @GetMapping("/addbookmark/{code}")
    public String addBookmark(@PathVariable("code") Long code){
        problemService.addBookmark(code);
        return "redirect:/mypage";
    }

    @GetMapping("/mypage")
    public String getMypage(Model model,Principal principal){
        List<ProblemInfo> p=problemService.pinfo(principal.getName());
        model.addAttribute("problems",p);
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList()); //온라인 저지 사이트 목록
        return "problem/mypage";
    }

}
