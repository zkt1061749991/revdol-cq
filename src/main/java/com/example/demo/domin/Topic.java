package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Topic implements Serializable {
    private int id;
    private String topic;
    private int type;
    private int state;
    private int point;
    private String description;
}
