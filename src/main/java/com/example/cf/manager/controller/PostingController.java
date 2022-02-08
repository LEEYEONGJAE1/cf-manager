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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PostingController {
    @Autowired
    private final PostingService postingService;
    @Autowired
    private final CommentService commentService;

    @GetMapping("/question")
    public String question(Model model,@RequestParam Map<String, String> param){
        if(param.get("keyword")==null){
            model.addAttribute("postings",postingService.findAllPosting());
        }
        else{
            String keyword=param.get("keyword");
            model.addAttribute("postings",postingService.findPostingByKeyword(keyword));
        }
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
    public String addpost(@ModelAttribute("form") PostingInfoDto postingInfoDto, @AuthenticationPrincipal UserInfo userInfo){
        postingInfoDto.setUserinfo(userInfo);
        postingInfoDto.setViews(0L);
        postingInfoDto.setLikes(0L);
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        postingInfoDto.setAddedTime(format.format(new Date()));
        postingService.save(postingInfoDto);
        return "redirect:/question";
    }

    @PostMapping("/posting/delete")
    public String deletePost(PostingInfoDto postingInfoDto,@AuthenticationPrincipal UserInfo userInfo){
       postingService.deletePosting(postingInfoDto.getCode(),userInfo);
        return "redirect:/question";
    }

    @GetMapping("/posting/update/{code}")
    public String editPost(@PathVariable("code") Long code,Model model,@AuthenticationPrincipal UserInfo userInfo){
        if(postingService.checkUserValid(code,userInfo)){
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
    public String putEdit(@PathVariable("code") Long code, PostingInfoDto postingInfoDto,@AuthenticationPrincipal UserInfo userInfo){
        postingService.updatePosting(code,postingInfoDto,userInfo);
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
    public String postComment(@PathVariable("code") Long code, CommentInfoDto commentInfoDto, @AuthenticationPrincipal UserInfo userInfo){
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        commentInfoDto.setAddedTime(format.format(new Date()));
        commentInfoDto.setUserinfo(userInfo);
        commentInfoDto.setPostinginfo(postingService.findById(code).get());
        commentService.save(commentInfoDto);
        return "redirect:/posting/view/"+Long.toString(code);
    }

    @GetMapping("/comment/delete/{PostingCode}/{CommentCode}")
    public String deleteComment(@PathVariable("PostingCode") Long PostingCode,@PathVariable("CommentCode") Long CommentCode,@AuthenticationPrincipal UserInfo userInfo){
        commentService.deleteComment(CommentCode,userInfo);
        return "redirect:/posting/view/"+Long.toString(PostingCode);
    }

}
