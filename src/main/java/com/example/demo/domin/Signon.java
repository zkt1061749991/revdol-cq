package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Signon implements Serializable{
    private String qq;
    private String password;
}
