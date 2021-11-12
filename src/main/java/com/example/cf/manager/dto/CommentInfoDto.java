package com.example.cf.manager.dto;

import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.domain.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInfoDto {
    private UserInfo userinfo;
    private PostingInfo postinginfo;
    private String contents,addedTime;
    private Long id;
}
