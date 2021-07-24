package com.example.demo.util.xcxapi;

import com.example.demo.domin.Contribute;
import com.example.demo.domin.Forum;
import com.example.demo.domin.Member;
import net.sf.json.JSONObject;

public class GetBean {
    public static boolean getForum(Forum forum,JSONObject item) {
        try {
            forum.setId(item.getInt("id"));
            forum.setUser_id(item.getInt("user_id"));
            forum.setIdol_id(item.getInt("idol_id"));
            forum.setType(item.getInt("type"));
            forum.setTitle(item.getString("title"));
            forum.setContent(item.getString("content"));
            forum.setVid(item.getString("vids"));
            forum.setAudio_url(item.getString("audio_url"));
            forum.setPraise_counts(item.getInt("praise_counts"));
            forum.setIs_pick(item.getString("is_pick"));
            forum.setIs_original(item.getString("is_original"));
            forum.setIs_top(item.getString("is_top"));
            forum.setTag(item.getInt("tag"));
            forum.setStatus(item.getInt("status"));
            forum.setCreated_time(item.getString("created_time"));
            forum.setThumbs(item.getJSONObject("forum_picture").getJSONArray("thumbs"));
            forum.setImages(item.getJSONObject("forum_picture").getJSONArray("images"));
            forum.setIs_official(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean getMember(Member member, JSONObject jsonObject_member) {
        try {
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
            member.setOfficial_verify(jsonObject_member.getInt("official_verify"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean getContribute(Contribute contribute, JSONObject jsonObject_contribute) {
        try {
            contribute.setId(jsonObject_contribute.getInt("id"));
            contribute.setUser_id(jsonObject_contribute.getInt("user_id"));
            contribute.setIdol_id(jsonObject_contribute.getInt("idol_id"));
            contribute.setPoint(jsonObject_contribute.getInt("point"));
            contribute.setUser_point(jsonObject_contribute.getInt("user_point"));
            contribute.setLevel(jsonObject_contribute.getInt("level"));
            contribute.setStatus(jsonObject_contribute.getInt("status"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
