package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {
    private int id;
    private int type;
    private String img;
    private String url;
    private int forum_id;
    private int uid;
    private String width;
    private String height;

//    public void setWidth(String url) throws IOException {
//        URL urlx = new URL(url);
//        URLConnection connection = urlx.openConnection();
//        connection.setDoOutput(true);
//        BufferedImage image = ImageIO.read(connection.getInputStream());
//        this.width = String.valueOf(image .getWidth());      // 源图宽度
//    }
//
//    public void setHeight(String url) throws IOException {
//        URL urlx = new URL(url);
//        URLConnection connection = urlx.openConnection();
//        connection.setDoOutput(true);
//        BufferedImage image = ImageIO.read(connection.getInputStream());
//        this.height = String.valueOf(image .getWidth());      // 源图高度
//    }
}
