package com.example.demo.plugin;

import com.example.demo.domin.*;
import com.example.demo.service.XcxApiService;
import com.example.demo.service.impl.AccountServiceImpl;
import com.example.demo.service.impl.PointServiceImpl;
import com.example.demo.service.impl.ResourceServiceImpl;
import com.example.demo.util.Getpic;
import com.example.demo.util.xcxapi.GetBean;
import net.lz1998.cq.event.meta.CQHeartBeatMetaEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class XcxForum extends CQPlugin {

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;
    @Autowired
    private ResourceServiceImpl resourceService;

    List<Forum> informableForumList = new ArrayList<Forum>();
    String msg = "";
    String imgurl = "";

    @Override
    public int onHeartBeatMeta(CoolQ cq, CQHeartBeatMetaEvent event) {

        //获取列表
        try {
            informableForumList = pointService.getInformableForumList();
            System.out.println("~~~~~~~~~~~Start notice forum , total " + informableForumList.size() + "~~~~~~~~~~~~");
        } catch (Exception e) {
            cq.sendPrivateMsg(1061749991, "获取小程序发帖发生错误", false);
            return MESSAGE_BLOCK;
        }

        //进行推送
        if (informableForumList.size() != 0) {
            for (Forum forum : informableForumList) {
                try {
                    Member member = accountService.getMember(forum.getUser_id());
                    imgurl = getImageurl(forum.getId());
                    if (member == null) {
                        cq.sendPrivateMsg(1061749991, "获取小程序发帖用户发生错误,帖子：" + forum.getId(), false);
                        pointService.insertInformedForum(forum.getId());
                        continue;
                    }

                    if (forum.getIs_official() == 0) {
                        //msg = "新增小程序发帖 \n战姬众：" + member.getNickname() + "\n应援歌姬：" + getidolname(forum.getIdol_id()) + "\n发帖类型：" + getforumtype(forum.getTag()) + "\n发帖标题：" + forum.getTitle() + "\n内容预览：" + getcontextshort(forum.getContent(), 100) + "\n请前往小程序查看";
                        //cq.sendGroupMsg(574057826, msg, false);
                    } else {
                        msg = member.getNickname() + " 在小程序发帖啦:\n" + forum.getTitle() + "\n" + getcontextshort(forum.getContent(), 1000) + "\n" + imgurl + "请前往小程序查看";
                        cq.sendGroupMsg(574057826, msg, false);
                        cq.sendGroupMsg(883078412, msg, false);
                        cq.sendGroupMsg(892937521, msg, false);
                        cq.sendGroupMsg(833473271, msg, false);
                    }
                    for(Topic topic : resourceService.getSendTopicList()) {
                        if (forum.getTitle().contains(topic.getTopic())) {
                            msg = member.getNickname() + " " +topic.getDescription() + "\n" + forum.getTitle() + "\n" + getcontextshort(forum.getContent(), 1000) + "\n" + imgurl +"请前往小程序查看";
                            cq.sendGroupMsg(892937521, msg, false);
                            cq.sendGroupMsg(833473271, msg, false);
                        }
                    }

                    if (forum.getIdol_id() == 4 && forum.getTag() != 1 && forum.getTag() != 2) {
                        msg = "检测到新的贝拉二创！ \n战姬众：" + member.getNickname() + "\n类型：" + getforumtype(forum.getTag()) + "\n标题：" + forum.getTitle() + "\n" + imgurl + "请八宝前往小程序捞人";
                        cq.sendGroupMsg(883078412, msg, false);
                        cq.sendGroupMsg(976515737, msg, false);
                    }
                    pointService.insertInformedForum(forum.getId());
                    System.out.println("informed ok forum_id: " + forum.getId());
                } catch (Exception e) {
                    cq.sendPrivateMsg(1061749991, "发帖推送发生错误，帖子id："+forum.getId(), false);
                    continue;
                }
            }
            informableForumList.clear();
        }

        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("~~~~~~~~~~~finish notice forum : " + df.format(d));
        return MESSAGE_BLOCK;
    }

    public static String getcontextshort(String context, int num) {
        int length = context.length();
        if (length > num) {
            return context.substring(0, num - 1) + "…………";
        } else {
            return context;
        }
    }


    public static String getidolname(int id) {
        switch (id) {
            case 1:
                return "卡缇娅";
            case 2:
                return "罗兹";
            case 3:
                return "清歌";
            case 4:
                return "伊莎贝拉";
            case 5:
                return "玉藻";
            case 6:
                return "墨汐";
        }
        return "null";
    }

    public static String getforumtype(int id) {
        switch (id) {
            case 1:
                return "普通文字帖";
            case 2:
                return "表情包";
            case 3:
                return "同人文";
            case 4:
                return "同人图";
            case 5:
                return "Cosplay";
            case 6:
                return "手工制品";
            case 7:
                return "普通视频帖";
            case 8:
                return "翻唱翻跳";
            case 9:
                return "二创视频";
        }
        return "null";
    }

    public String getImageurl(int forum_id) {
        List<Image> imageList =  resourceService.getImageListByForum_id(forum_id);
        if(!imageList.isEmpty()) {
            String imageurl = "";
            for (Image image : imageList) {
                //imageurl = imageurl + "http://resource.revdol.club/revdol/media/" + image.getImg() + "\n";
                imageurl = imageurl + CQCode.image(image.getUrl()) + "\n";
            }
            return imageurl;
        } else {
            return "\n";
        }
    }
}
