package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Pointlog implements Serializable {
    private int state = 1;
    private int type = 2;
    private String operated_qq_id;
    private int operate_type;
    private int operate_point;
    private String description;
    private Date operate_date;
    private String img = "";
    private String explain;
    private String remark;
    private int forum_id;
}
