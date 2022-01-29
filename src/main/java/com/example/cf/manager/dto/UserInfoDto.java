package com.example.cf.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String userid,cfid,username,password,auth;
    private Long code;
}
