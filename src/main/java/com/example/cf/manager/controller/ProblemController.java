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
    private final UserService userService;
    @Autowired
    private final ProblemService problemService;
    @Autowired
    private final PostingService postingService;
    @Autowired
    private final CommentService commentService;
    @GetMapping("/addproblem")
    public String addpview(Model model, Principal principal){
        model.addAttribute("form", new ProblemInfoDto());
        List<ProblemInfo> p=problemService.pinfo(principal.getName());
        model.addAttribute("problems",p);
        return "problem/addproblem";
    }

    @PostMapping("/addproblem")
    public String addppost(@ModelAttribute("form") ProblemInfoDto problemDto, Principal principal){
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
        Optional<PostingInfo> temp = postingService.findById(code);
        commentService.findAndDeleteByPosting(temp.get());
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
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
        return "problem/mypage";
    }

}
