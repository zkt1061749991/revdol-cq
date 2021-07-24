package com.example.demo.plugin;

import com.example.demo.domin.*;
import com.example.demo.service.XcxApiService;
import com.example.demo.service.impl.AccountServiceImpl;
import com.example.demo.service.impl.PointServiceImpl;
import com.example.demo.service.impl.ResourceServiceImpl;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.plugin.XcxForum.getforumtype;

@Component
public class EcdjPlugin extends CQPlugin {

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;
    @Autowired
    private ResourceServiceImpl resourceService;

    @Override
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
        long userId = event.getUserId();
        String msg = event.getMessage();

        if (msg.equals("二创登记") || msg.equals("发帖登记")) {
            String qq = Long.toString(userId);
            if (accountService.accountExist(qq)) {
                Account account = accountService.getAccount(qq);
                if (account.getXcxuid().equals("")) {
                    cq.sendPrivateMsg(userId, "未绑定小程序战姬众号(UID)，请前往贝化值系统网站填写绑定", false);
                } else {
                    cq.sendPrivateMsg(userId, "正在获取发帖并处理中，稍等片刻，请勿发送其他消息………………", false);

                    List<Forum> forumList = pointService.getForumListByXcxuid(account.getXcxuid());

                    if (!forumList.isEmpty()) {
                        int suc = 0;
                        for (Forum forum : forumList) {
                            if (forum.getTag() != 1 && forum.getTag() != 7) {

                                //判断是否删了
                                boolean flag = false;
                                try {
                                    flag = XcxApiService.isForumDel(forum.getId());
                                } catch (Exception e) {
                                    System.out.println("Post crawling error");
                                }
                                if (flag) {
                                    continue;
                                }

                                if (!pointService.bookedForumExist(forum.getId())) {
                                    Pointlog pointlog = new Pointlog();
                                    pointlog.setState(0);
                                    pointlog.setOperated_qq_id(qq);
                                    pointlog.setOperate_type(1);
                                    pointlog.setOperate_point(0);
                                    pointlog.setExplain("来自蛋仔客服的一键登记");
                                    pointlog.setType(0);
                                    pointlog.setForum_id(forum.getId());
                                    pointlog.setRemark("http://isabella.revdol.club/point/forum?key=mark&id=" + forum.getId());
                                    if (!forum.getVid().isEmpty()) {
                                        pointlog.setRemark("http://isabella.revdol.club/point/forum?key=mark&id=" + forum.getId() + "    视频链接： https://v.qq.com/x/page/" + forum.getVid() + ".html");
                                    }
                                    List<Image> imageList = resourceService.getImageListByForum_id(forum.getId());
                                    if (imageList.size() != 0) {
                                        pointlog.setImg(imageList.get(0).getImg());
                                    }

                                    String title = forum.getTitle();
                                    if (title.length() > 18) title = title.substring(0, 18) + "……";
                                    switch (forum.getTag()) {
                                        case 1:
                                            pointlog.setDescription("活动发帖：" + title);
                                            pointlog.setType(1);
                                            break;
                                        case 2:
                                            pointlog.setDescription("二创：表情包 " + title);
                                            break;
                                        case 3:
                                            pointlog.setDescription("二创：同人文 " + title);
                                            break;
                                        case 4:
                                            pointlog.setDescription("二创：同人图 " + title);
                                            break;
                                        case 5:
                                            pointlog.setDescription("二创：Cosplay " + title);
                                            break;
                                        case 6:
                                            pointlog.setDescription("二创：手工制品 " + title);
                                            break;
                                        case 7:
                                            pointlog.setDescription(title);
                                            break;
                                        case 8:
                                            pointlog.setDescription("二创：翻唱翻跳 " + title);
                                            break;
                                        case 9:
                                            pointlog.setDescription("二创：视频类 " + title);
                                            break;
                                        default:
                                            pointlog.setDescription("未识别的类型帖：" + title);
                                    }
                                    try {
                                        pointService.insertbookedForum(forum);
                                        pointService.insertPointlog(pointlog);
                                        suc += 1;
                                        cq.sendPrivateMsg(userId, "二创登记成功！\n类型：" + getforumtype(forum.getTag()) + "\n标题：" + forum.getTitle() + "\n" + getImageurl(imageList) + "二创将在最晚两周内发放贝化值，详细请咨询群管理", false);
                                    } catch (Exception e) {
                                        cq.sendPrivateMsg(userId, "二创登记失败，可前往贝化值系统网站登记或联系管理员解决", false);
                                    }
                                }
                                //普通帖则判断是否为活动帖
                            } else {
//                                List<Topic> topicList = resourceService.getActTopicList();
//                                if(!topicList.isEmpty()) {
//                                    forum.getTitle().contains()
//                                }

                            }

                        }
                        if (suc == 0) {
                            cq.sendPrivateMsg(userId, "未获取到可登记的发帖，请稍等几分钟再试", false);
                        } else {
                            cq.sendPrivateMsg(userId, "本次成功登记" + suc + "篇发帖，期待你的下一次二创哦！", false);
                            suc = 0;
                        }
                    } else {
                        cq.sendPrivateMsg(userId, "未获取到可登记的发帖，请稍等几分钟再试", false);
                    }
                }
            } else {
                cq.sendPrivateMsg(userId, "你好像还没有注册哦！回复 \"贝化值注册\" 可直接完成注册哦！", false);
            }
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }

    //    public String getImageurl(int forum_id) {
//        List<Image> imageList = resourceService.getImageListByForum_id(forum_id);
//        if (!imageList.isEmpty()) {
//            String imageurl = "";
//            for (Image image : imageList) {
//                //imageurl = imageurl + "http://resource.revdol.club/revdol/media/" + image.getImg() + "\n";
//                imageurl = imageurl + CQCode.image(image.getUrl()) + "\n";
//            }
//            return imageurl;
//        } else {
//            return "\n";
//        }
//    }
    public static String getImageurl(List<Image> imageList) {
        if (!imageList.isEmpty()) {
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
