package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private int id;
    private String openid;
    private int uid;
    private int sex;
    private String nickname;
    private String birth;
    private String headimg;
    private String address;
    private String signature;
    private int status;
    private int official_verify;
}
