package com.example.cf.manager.dto;

import com.example.cf.manager.domain.CommentInfo;
import com.example.cf.manager.domain.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostingInfoDto {
    private UserInfo userinfo;
    private String title,contents,addedTime;
    private List<CommentInfo> comments;
    private Long code,views,likes;
}
