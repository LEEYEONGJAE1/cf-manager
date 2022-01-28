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
public class PostingController {
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

    @GetMapping("/posting/create")
    public String addposting(Model model, Principal principal){
        model.addAttribute("form", new PostingInfoDto());
        model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
        return "posting/create";
    }

    @PostMapping("/posting/create")
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

    @PostMapping("/deleteposting")
    public String deletePost(PostingInfoDto postingInfoDto,Authentication authentication){
        postingService.delete(postingInfoDto.getCode(),authentication);
        return "redirect:/question";
    }

    @GetMapping("/posting/update/{code}")
    public String editPost(@PathVariable("code") Long code,Model model,Authentication authentication){
        if(checkUser(code,authentication)) {
            Optional<PostingInfo> temp = postingService.findById(code);
            if (temp.isPresent()) {
                model.addAttribute("posting", temp.get());
                model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
                return "posting/update";
            }
        }
        return "redirect:/question";
    }

    @PutMapping("/posting/update/{code}")
    public String putEdit(@PathVariable("code") Long code, PostingInfoDto postingdto,Authentication authentication){
        if(checkUser(code,authentication))
            postingService.update(code,postingdto);
        return "redirect:/question";
    }

    @GetMapping("/posting/view/{code}")
    public String viewPosting(@PathVariable("code") Long code,Model model){
        Optional<PostingInfo> postingInfo=postingService.findById(code);
        if(postingInfo.isPresent()) {
            postingService.increaseView(code,1);
            model.addAttribute("comments",commentService.findByPosting(postingInfo.get()));
            model.addAttribute("onlineJudgeSites", new SiteInfos().getList());
            model.addAttribute("posting", postingInfo.get());
            model.addAttribute("newLineChar", '\n');
            return "posting/view";
        }
        return "redirect:/question";
    }

    @PostMapping("/posting/view/{code}")
    public String postComment(@PathVariable("code") Long code, CommentInfoDto commentDTO, Authentication authentication){
        UserInfo writerInfo=(UserInfo) authentication.getPrincipal();
        commentDTO.setUserinfo(writerInfo);
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        commentDTO.setAddedTime(format.format(new Date()));
        commentDTO.setPostinginfo(postingService.findById(code).get());
        commentService.save(commentDTO);
        return "redirect:/posting/view/"+Long.toString(code);
    }

    @GetMapping("/cdelete/{pcode}/{ccode}")
    public String deleteComment(@PathVariable("ccode") Long userCode,@PathVariable("pcode") Long postingCode,Authentication authentication){
        UserInfo myInfo=(UserInfo) authentication.getPrincipal();
        if(myInfo.getUserid().equals(commentService.findById(userCode).get().getUserinfo().getUserid()))
            commentService.delete(userCode);
        return "redirect:/posting/view/"+Long.toString(postingCode);
    }

    public Boolean checkUser(Long code,Authentication authentication){
        UserInfo me=(UserInfo) authentication.getPrincipal();
        if(me.getUserid().equals(postingService.findById(code).get().getUserinfo().getUserid()))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
