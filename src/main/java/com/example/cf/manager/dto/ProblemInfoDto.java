package com.example.cf.manager.dto;

import com.example.cf.manager.domain.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemInfoDto {
    private UserInfo userInfo;
    private String problemName,problemLink,addedTime;
    private Boolean bookmarked;

}
