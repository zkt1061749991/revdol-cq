package com.example.demo.service;

import com.example.demo.domin.Contribute;
import com.example.demo.domin.Forum;
import com.example.demo.domin.Member;
import com.example.demo.domin.XcxData;
import com.example.demo.util.xcxapi.GetIdolList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class XcxApiService {

    public static JSONArray getAllForumJson() {
        JSONObject jsonObject = GetIdolList.getIdolforums(1);
        return jsonObject.getJSONArray("items");
    }


    public static JSONArray getForumTopics(int id) {
        JSONObject jsonObject = GetIdolList.getforum(id);
        return jsonObject.getJSONArray("topics");
    }


    public static boolean getOfficialForum(Forum forum, Member member) {
        boolean flag = false;

        try {
            JSONObject jsonObject = GetIdolList.getIdolforums(1);
            JSONArray jsonArray_items = jsonObject.getJSONArray("items");
            for (int j = 0; j < 3; j++) {
                JSONObject item = jsonArray_items.getJSONObject(j);
                JSONObject jsonObject_member = item.getJSONObject("member");
                if (jsonObject_member.getInt("official_verify") == 1) {
                    forum.setId(item.getInt("id"));
                    forum.setUser_id(item.getInt("user_id"));
                    forum.setType(item.getInt("type"));
                    forum.setTitle(item.getString("title"));
                    forum.setContent(item.getString("content"));
                    forum.setVid(item.getString("vids"));
                    forum.setPraise_counts(item.getInt("praise_counts"));
                    forum.setIs_pick(item.getString("is_pick"));
                    forum.setTag(item.getInt("tag"));
                    forum.setStatus(item.getInt("status"));
                    forum.setCreated_time(item.getString("created_time"));
                    forum.setThumbs(item.getJSONObject("forum_picture").getJSONArray("thumbs"));
                    forum.setImages(item.getJSONObject("forum_picture").getJSONArray("images"));
                    System.out.println(forum.toString());

                    member.setId(jsonObject_member.getInt("id"));
                    member.setOpenid(jsonObject_member.getString("openid"));
                    member.setUid(jsonObject_member.getInt("uid"));
                    member.setSex(jsonObject_member.getInt("sex"));
                    member.setNickname(jsonObject_member.getString("nickname"));
                    member.setBirth(jsonObject_member.getString("birth"));
                    member.setHeadimg(jsonObject_member.getString("headimg"));
                    member.setAddress(jsonObject_member.getString("address"));
                    member.setSignature(jsonObject_member.getString("signature"));
                    member.setStatus(jsonObject_member.getInt("status"));
                    System.out.println(member.toString());

                    flag = true;
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean getLastForum(Forum forum, Member member) {
        boolean flag = false;

        try {
            JSONObject jsonObject = GetIdolList.getIdolforums(1);
            JSONArray jsonArray_items = jsonObject.getJSONArray("items");

            JSONObject item = jsonArray_items.getJSONObject(0);
            JSONObject jsonObject_member = item.getJSONObject("member");

            forum.setId(item.getInt("id"));
            forum.setUser_id(item.getInt("user_id"));
            forum.setType(item.getInt("type"));
            forum.setTitle(item.getString("title"));
            forum.setContent(item.getString("content"));
            forum.setVid(item.getString("vids"));
            forum.setPraise_counts(item.getInt("praise_counts"));
            forum.setIs_pick(item.getString("is_pick"));
            forum.setTag(item.getInt("tag"));
            forum.setStatus(item.getInt("status"));
            forum.setCreated_time(item.getString("created_time"));
            forum.setThumbs(item.getJSONObject("forum_picture").getJSONArray("thumbs"));
            forum.setImages(item.getJSONObject("forum_picture").getJSONArray("images"));
            System.out.println(forum.toString());

            member.setId(jsonObject_member.getInt("id"));
            member.setOpenid(jsonObject_member.getString("openid"));
            member.setUid(jsonObject_member.getInt("uid"));
            member.setSex(jsonObject_member.getInt("sex"));
            member.setNickname(jsonObject_member.getString("nickname"));
            member.setBirth(jsonObject_member.getString("birth"));
            member.setHeadimg(jsonObject_member.getString("headimg"));
            member.setAddress(jsonObject_member.getString("address"));
            member.setSignature(jsonObject_member.getString("signature"));
            member.setStatus(jsonObject_member.getInt("status"));
            System.out.println(member.toString());

            flag = true;

        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isForumDel(int id) {
        JSONObject jsonObject = GetIdolList.getforum(id);
        if(jsonObject.containsKey("error")) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Forum forum = new Forum();
        Member member = new Member();
        //getOfficialForum(forum, member);
        System.out.println(getForumTopics(70984).toString());
    }
}
