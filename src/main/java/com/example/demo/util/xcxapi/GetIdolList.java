package com.example.demo.util.xcxapi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GetIdolList {

    public static JSONObject getIdol(){
        String url ="https://starmicro.happyelements.cn/v1/idol/idollist";//请求接口地址
        Map params = new HashMap();//请求参数
        String backType = "array";
        String method = "GET"; //请求http方法
        return (JSONObject) Net.get(url,params,method,backType,4);
    }

    public static JSONObject getEventList() {
        String url = "https://starmicro.happyelements.cn/v1/media/event-list";
        Map params = new HashMap();//请求参数
        params.put("type",1);
        params.put("limits",3);
        String backType = "object";
        String method = "GET"; //请求http方法
        return (JSONObject) Net.get(url,params,method,backType,0);
    }

    public static JSONArray getWeek() {
        String url ="https://starmicro.happyelements.cn/v1/rank/week";//请求接口地址
        Map params = new HashMap();//请求参数
        Date date = new Date();
        long temp = (date.getTime()+259200000)/604800000 - 2547;
        System.out.println(temp);
        params.put("batch",Long.toString(temp));
        String backType = "array2";
        String method = "GET"; //请求http方法
        return (JSONArray) Net.get(url,params,method,backType,0);
    }

    public static JSONObject getBFansNum(String mid) {
        String url ="https://api.bilibili.com/x/web-interface/card";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mid",mid);
        params.put("jsonp","jsonp");
        params.put("article",true);
        String backType = "object";
        String method = "GET"; //请求http方法
        return (JSONObject) Net.get(url,params,method,backType,0);
    }

    public static JSONObject getIdolforums(int page) {
        String url = "https://starmicro.happyelements.cn/v1/idol/idolforums";
        Map params = new HashMap();//请求参数
        params.put("idol_id",0);
        params.put("page",page);
        String backType = "object";
        String method = "GET"; //请求http方法
        return (JSONObject) Net.get(url,params,method,backType,0);
    }

    public static JSONObject getforum(int id) {
        String url = "https://starmicro.happyelements.cn/v1/idol/forumdetail";
        Map params = new HashMap();//请求参数
        params.put("id",id);
        String backType = "object";
        String method = "GET"; //请求http方法
        return (JSONObject) Net.get(url,params,method,backType,0);
    }
}
