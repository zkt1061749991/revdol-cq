package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Zf implements Serializable {
    private int id;
    private String qq;
    private String topic;
    private Timestamp date;
}
