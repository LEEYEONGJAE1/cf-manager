package com.example.cf.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemInfoDto {

    private String userid,problemName,problemLink,addedTime;
    private Boolean bookmarked;

}
