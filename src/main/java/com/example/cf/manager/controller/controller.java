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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class controller {
    @Autowired
    private final UserService userService;
    @Autowired
    private final ProblemService problemService;
    @Autowired
    private final PostingService postingService;
    @Autowired
    private final CommentService commentService;

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
        return "problem/addproblem";
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

    @GetMapping("/addposting")
    public String addposting(Model model,Principal principal){
        model.addAttribute("form", new PostingInfoDto());
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
        return "posting/addposting";
    }

    @PostMapping("/addposting")
    public String addpost(@ModelAttribute("form") PostingInfoDto postingInfoDto, Authentication authentication){
        UserInfo writer=(UserInfo) authentication.getPrincipal();
        System.out.println(authentication.getPrincipal().getClass().getName());
        postingInfoDto.setUserinfo(writer);
        postingInfoDto.setViews(0L);
        postingInfoDto.setLikes(0L);
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        postingInfoDto.setAddedTime(format.format(new Date()));
        postingService.save(postingInfoDto);
        return "redirect:/question";
    }

    @GetMapping("/deleteposting/{code}")
    public String deletePost(@PathVariable("code") Long code,Authentication authentication){
        if(checkUser(code,authentication))
            postingService.delete(code);
        return "redirect:/question";
    }

    @GetMapping("/editposting/{code}")
    public String editPost(@PathVariable("code") Long code,Model model,Authentication authentication){
        if(checkUser(code,authentication)) {
            Optional<PostingInfo> temp = postingService.findById(code);
            if (temp.isPresent()) {
                model.addAttribute("posting", temp.get());
                model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
                return "posting/editposting";
            }
        }
        return "redirect:/question";
    }

    @PutMapping("/editposting/{code}")
    public String getEdit(@PathVariable("code") Long code, PostingInfoDto postingdto,Authentication authentication){
        if(checkUser(code,authentication))
            postingService.update(code,postingdto);
        return "redirect:/question";
    }

    @GetMapping("/mypage")
    public String mypage(Model model,Principal principal){
        List<ProblemInfo> p=problemService.pinfo(principal.getName());
        model.addAttribute("problems",p);
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
        return "problem/mypage";
    }

    @GetMapping("/question")
    public String question(Model model){
        model.addAttribute("postings",postingService.findAll());
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
        return "posting/question";
    }

    @GetMapping("/postingview/{code}")
    public String postingview(@PathVariable("code") String code,Model model){
        Long c=Long.parseLong(code);
        Optional<PostingInfo> temp=postingService.findById(c);

        if(temp.isPresent()) {
            model.addAttribute("comments",commentService.findByPosting(temp.get()));
            model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
            model.addAttribute("posting", temp.get());
            return "posting/postingview";
        }
        return "redirect:/question";
    }

    @PostMapping("/postingview/{code}")
    public String postComment(@PathVariable("code") Long code,CommentInfoDto commentdto,Authentication authentication){
        UserInfo writer=(UserInfo) authentication.getPrincipal();
        commentdto.setUserinfo(writer);
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        commentdto.setAddedTime(format.format(new Date()));
        System.out.println(postingService.findById(code).get().getTitle());
        commentdto.setPostinginfo(postingService.findById(code).get());
        commentService.save(commentdto);
        return "redirect:/postingview/"+Long.toString(code);
    }

    @GetMapping("/cdelete/{pcode}/{ccode}")
    public String deleteComment(@PathVariable("ccode") Long code,@PathVariable("pcode") Long pcode,Authentication authentication){
        UserInfo me=(UserInfo) authentication.getPrincipal();
        if(me.getUserid()==commentService.findById(code).get().getUserinfo().getUserid())
            commentService.delete(code);
        return "redirect:/postingview/"+Long.toString(pcode);
    }

    @RequestMapping(value="/idcheck",method = RequestMethod.POST)
    @ResponseBody
    public int validId(UserInfoDto userInfoDto){
        System.out.println(userInfoDto.getUserid());
        if(userService.exists(userInfoDto.getUserid())){
            return 0;
        }
        else{
            return 1;
        }
    }

    public Boolean checkUser(Long code,Authentication authentication){
        UserInfo me=(UserInfo) authentication.getPrincipal();
        if(me.getUserid()==postingService.findById(code).get().getUserinfo().getUserid())
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

}
