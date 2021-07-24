package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Contribute implements Serializable {
    private int id;
    private int user_id;
    private int idol_id;
    private int status;
    private int point;
    private int user_point;
    private int level;
}
