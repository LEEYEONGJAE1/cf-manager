package com.example.cf.manager.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class PostingInfo {
    @Id
    @Column(name = "code")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long code;

    @ManyToOne
    @JoinColumn(name="userid")
    private UserInfo userinfo;

    @OneToMany(mappedBy = "postinginfo", cascade = CascadeType.REMOVE) //영속성
    private List<CommentInfo> commentInfos = new ArrayList<>();

    @Column(name="title")
    private String title;

    @Column(name="contents",length=10000)
    private String contents;

    @Column(name="addedTime")
    private String addedTime;

    @Column(name="views")
    private Long views;

    @Column(name="likes")
    private Long likes;


    @Builder
    public PostingInfo(UserInfo userinfo, String title,String contents,String addedTime,Long views,Long likes) {
        this.userinfo = userinfo;
        this.title=title;
        this.contents=contents;
        this.addedTime=addedTime;
        this.views=views;
        this.likes=likes;
    }


}
