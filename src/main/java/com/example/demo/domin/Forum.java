package com.example.demo.domin;

import lombok.Data;
import net.sf.json.JSONArray;

import java.io.Serializable;

@Data
public class Forum implements Serializable {
    private int id;
    private int idol_id;
    private int user_id;
    private int type; //1为图文，2为视频
    private String title;
    private String content;
    private String vid = ""; //https://v.qq.com/x/page/[vid].html
    private String audio_url;
    private int praise_counts;  //点赞数
    private String is_pick;  //YES 或 NO 上周赏
    private String is_original;
    private String is_top;
    private int tag;  //1普文2表情包3文4图5cos6手工7普视频8翻唱翻跳9二创视频
    private int status;  //1正常，其他均不正常
    private String created_time;
    private int is_official;
    private JSONArray thumbs;
    private JSONArray images;
    private String topics = "";

//    public void setVid(String vid) {
//        if(!vid.equals("")) {
//            this.vid = "https://v.qq.com/x/page/" + vid + ".html";
//        }
//        else {
//            this.vid = vid;
//        }
//    }
}
