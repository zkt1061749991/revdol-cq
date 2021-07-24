package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class XcxData implements Serializable {
    private int week_popular_num;
    private int week_no;
    private String week_string;
    private int popular_num;
    private int fans_num;
    private int attention_num;
    private String img_url;
}
