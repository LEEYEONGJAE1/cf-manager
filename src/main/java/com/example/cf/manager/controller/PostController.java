package com.example.cf.manager.controller;

import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.domain.SiteInfos;
import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.dto.CommentInfoDto;
import com.example.cf.manager.dto.PostingInfoDto;
import com.example.cf.manager.service.CommentService;
import com.example.cf.manager.service.PostingService;
import com.example.cf.manager.service.ProblemService;
import com.example.cf.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PostController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final ProblemService problemService;
    @Autowired
    private final PostingService postingService;
    @Autowired
    private final CommentService commentService;

    @GetMapping("/question")
    public String question(Model model){
        model.addAttribute("postings",postingService.findAll());
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
        return "posting/question";
    }

    @GetMapping("/addposting")
    public String addposting(Model model, Principal principal){
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
    public String deletePost(@PathVariable("code") Long code, Authentication authentication){
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

    @GetMapping("/postingview/{code}")
    public String postingview(@PathVariable("code") String code,Model model){
        Long c=Long.parseLong(code);
        Optional<PostingInfo> temp=postingService.findById(c);

        if(temp.isPresent()) {
            model.addAttribute("comments",commentService.findByPosting(temp.get()));
            model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
            model.addAttribute("posting", temp.get());
            model.addAttribute("newLineChar", '\n');
            return "posting/postingview";
        }
        return "redirect:/question";
    }

    @PostMapping("/postingview/{code}")
    public String postComment(@PathVariable("code") Long code, CommentInfoDto commentdto, Authentication authentication){
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

    public Boolean checkUser(Long code,Authentication authentication){
        UserInfo me=(UserInfo) authentication.getPrincipal();
        if(me.getUserid()==postingService.findById(code).get().getUserinfo().getUserid())
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
