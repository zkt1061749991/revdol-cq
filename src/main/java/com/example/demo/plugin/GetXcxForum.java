package com.example.demo.plugin;

import com.example.demo.domin.*;
import com.example.demo.service.XcxApiService;
import com.example.demo.service.impl.AccountServiceImpl;
import com.example.demo.service.impl.PointServiceImpl;
import com.example.demo.service.impl.ResourceServiceImpl;
import com.example.demo.util.Getpic;
import com.example.demo.util.xcxapi.GetBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class GetXcxForum {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;
    @Autowired
    private ResourceServiceImpl resourceService;

    JSONArray jsonArray_items = new JSONArray();
    JSONObject item = new JSONObject();
    JSONObject jsonObject_member = new JSONObject();
    JSONObject jsonObject_contribute = new JSONObject();
    Getpic pic = new Getpic();
    Forum forum = new Forum();
    Member member = new Member();
    Contribute contribute = new Contribute();

    @Bean
    @Scheduled(cron = "0 */1 * * * ?")
    public void getForum() {

        //获取列表
        try {
            jsonArray_items = XcxApiService.getAllForumJson();
        } catch (Exception e) {
            System.out.println("Post crawling error");
        }

        //开始处理列表，从旧到新
        int num = jsonArray_items.size();
        System.out.println("----------------Start polling: crawl the latest post successfully,total " + num + " ,start processing");
        for (int j = num - 1; j >= 0; j--) {
            //数据准备模块
            item = jsonArray_items.getJSONObject(j);
            jsonObject_member = item.getJSONObject("member");
            jsonObject_contribute = item.getJSONObject("contribute");
            GetBean.getForum(forum, item);
            GetBean.getMember(member, jsonObject_member);
            if (member.getOfficial_verify() == 1) {
                forum.setIs_official(1);
            }
            GetBean.getContribute(contribute, jsonObject_contribute);

            //数据处理模块
            if (pointService.forumExist(forum.getId())) {
                //数据更新模块
                try {
                    accountService.updateMember(member);
                    accountService.updateContribute(contribute);
                    System.out.println("loop " + (num - j) + " information update OK");
                } catch (Exception e) {
                    System.out.println("An error occurred when update information");
                }
            } else {
                //主题获取模块
                forum.setTopics(XcxApiService.getForumTopics(forum.getId()).toString());

                //图片存储模块
                List<Image> thumbsList = resourceService.getThumbListByForum_id(forum.getId());
                List<Image> imagesList = resourceService.getImageListByForum_id(forum.getId());
                if (thumbsList.size() == 0 || imagesList.size() == 0) {
                    if (!forum.getThumbs().isEmpty() && !forum.getImages().isEmpty()) {
                        //String filePath = "/revdol/media/";
                        //将图片缓存至服务器并建立文件列表
                        /*缩略图不存了
                        for (Object thumb : forum.getThumbs()) {
                            String url = (String) thumb;
                            String fileName = url.substring(url.lastIndexOf("/"));
                            //pic.saveUrlAs(url, filePath + fileName);
                            Image image = new Image();
                            image.setForum_id(forum.getId());
                            image.setImg(fileName.substring(1));
                            image.setType(0);
                            image.setUid(forum.getUser_id());
                            image.setUrl(url);
                            try {
                                URL urlx = new URL(url);
                                URLConnection connection = urlx.openConnection();
                                connection.setDoOutput(true);
                                BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                                image.setWidth(String.valueOf(bufferedImage.getWidth()));      // 源图宽度
                                image.setHeight(String.valueOf(bufferedImage.getHeight()));      // 源图高度
                                resourceService.insertImage(image);
                            } catch (Exception e) {
                                System.out.println("An error occurred in image storage");
                            }
                            thumbsList.add(image);
                        }
                        */
                        for (Object image : forum.getImages()) {
                            String url = (String) image;
                            String fileName = url.substring(url.lastIndexOf("/"));
                            //pic.saveUrlAs(url, filePath + fileName);
                            Image image1 = new Image();
                            image1.setForum_id(forum.getId());
                            image1.setImg(fileName.substring(1));
                            image1.setType(1);
                            image1.setUid(forum.getUser_id());
                            image1.setUrl(url);
                            try {
                                URL urlx = new URL(url);
                                URLConnection connection = urlx.openConnection();
                                connection.setDoOutput(true);
                                BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                                image1.setWidth(String.valueOf(bufferedImage.getWidth()));      // 源图宽度
                                image1.setHeight(String.valueOf(bufferedImage.getHeight()));      // 源图高度
                                resourceService.insertImage(image1);
                            } catch (Exception e) {
                                System.out.println("An error occurred in image storage");
                            }
                            imagesList.add(image1);
                        }
                    }
                }
                //用户状态更新模块
                if (forum.getIdol_id() == 4) {
                    try {
                        Account account = accountService.getAccountByXcxuid(String.valueOf(forum.getUser_id()));
                        if (account != null) {
                            if (account.getState() == 0 && contribute.getUser_point() > 1386) {
                                account.setState(1);
                                account.setXcxname(member.getNickname());
                                accountService.updateState(account);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred in account information update");
                    }
                }
                //数据存储模块
                try {
                    pointService.insertForum(forum);
                    if (!accountService.memberExist(member.getUid())) {
                        accountService.insertMember(member);
                    } else {
                        accountService.updateMember(member);
                    }
                    if (!accountService.contributeExist(contribute.getId())) {
                        accountService.insertContribute(contribute);
                    } else {
                        accountService.updateContribute(contribute);
                    }
                    System.out.println("loop " + (num - j) + " new information storage OK-----");
                } catch (Exception e) {
                    System.out.println("An error occurred in information storage");
                }
            }
        }
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("-----------------------Successful treatment " + df.format(d));
    }
}
