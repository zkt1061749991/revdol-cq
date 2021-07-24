package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Event implements Serializable{
    private int id;
    private int state;
    private int type;
    private String event_title;
    private String event_alt;
    private String event_body;
    private String img;
    private String url;
    private Date begin_date;
    private Date end_date;
}
