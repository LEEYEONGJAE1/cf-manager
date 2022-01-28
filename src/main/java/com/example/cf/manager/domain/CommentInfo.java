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
public class CommentInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userid")
    private UserInfo userinfo;

    @ManyToOne
    @JoinColumn(name="code")
    private PostingInfo postinginfo;

    @Column(name="contents",length=5000)
    private String contents;

    @Column(name="addedTime")
    private String addedTime;

    @Builder
    public CommentInfo(UserInfo userinfo, PostingInfo postinginfo,String contents, String addedTime) {
        this.userinfo = userinfo;
        this.postinginfo=postinginfo;
        this.contents=contents;
        this.addedTime=addedTime;
    }


}
