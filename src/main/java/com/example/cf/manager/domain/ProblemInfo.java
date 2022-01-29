package com.example.cf.manager.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class ProblemInfo {
    @Id
    @Column(name = "code")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long code;

    @ManyToOne
    @JoinColumn(name="userid")
    private UserInfo userinfo;

    @Column(name="problemName")
    private String problemName;

    @Column(name="problemLink")
    private String problemLink;

    @Column(name="addedTime")
    private String addedTime;

    @Column(name="bookmarked")
    private Boolean bookmarked;


    @Builder
    public ProblemInfo(UserInfo userInfo, String problemName,String problemLink,String addedTime,Boolean bookmarked) {
        this.userinfo = userInfo;
        this.problemName=problemName;
        this.problemLink=problemLink;
        this.addedTime=addedTime;
        this.bookmarked=bookmarked;
    }


}
