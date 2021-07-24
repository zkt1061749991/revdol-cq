package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    private String qq;
    private String name;
    private int point;
    private int state;
    private String weixin;
    private String bilibili;
    private String xcxuid = "";
    private String xcxname;
    private String remark;
    private String address;
    private String hiddenadd = "";

    public void hiddenadd() {
        if(address != null) {
            int num = address.length();
            String add = this.address;
            String str = "********";
            if (num >= 6) {
                StringBuilder sb = new StringBuilder(add);
                sb.replace(3, num - 1, str);
                this.hiddenadd = sb.toString();
            } else {
                this.hiddenadd = add;
            }
        }
        this.address = "";
    }
}
