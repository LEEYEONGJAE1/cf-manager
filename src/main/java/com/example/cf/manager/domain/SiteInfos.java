package com.example.cf.manager.domain;

import java.util.ArrayList;
import java.util.List;

public class SiteInfos {
    public List<SiteInfo> info=new ArrayList<>();
    public SiteInfos(){
        info.add(new SiteInfo("Codeforces","https://codeforces.com"));
        info.add(new SiteInfo("Baekjoon online judge","https://acmicpc.net"));
        info.add(new SiteInfo("Atcoder","https://atcoder.jp/"));
        info.add(new SiteInfo("정올 Jungol","http://jungol.co.kr/"));
    }
    public List<SiteInfo> getList(){
        return info;
    }
}
