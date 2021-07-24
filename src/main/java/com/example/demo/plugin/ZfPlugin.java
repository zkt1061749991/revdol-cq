package com.example.demo.plugin;

import com.example.demo.domin.Pointlog;
import com.example.demo.domin.Topic;
import com.example.demo.domin.Zf;
import com.example.demo.service.impl.AccountServiceImpl;
import com.example.demo.service.impl.PointServiceImpl;
import com.example.demo.service.impl.ResourceServiceImpl;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ZfPlugin extends CQPlugin {

    @Autowired
    private ResourceServiceImpl resourceService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        Long groupId = event.getGroupId();
        Long userId = event.getUserId();
        String msg = event.getMessage();
        if (msg.contains("[CQ:rich") && (groupId == 833473271 || groupId == 228415488 || groupId == 574057826 || groupId == 883078412)) {
            //System.out.println(msg);
            String title = getTitle(msg, userId);

            if (!title.equals("null")) {
                List<Topic> zfList = resourceService.getZfList();
                if (!zfList.isEmpty()) {
                    for (Topic topic : zfList) {
                        if (topic.getTopic().contains(title.substring(0, title.length() - 1))) {
                            String qq = Long.toString(userId);

                            if (accountService.accountExist(qq)) {

                                String pattern11 = "\"uin\":(.*?)(})?&#44;";
                                Pattern r11 = Pattern.compile(pattern11);
                                Matcher m11 = r11.matcher(msg);
                                if (m11.find()) {
                                    if (!qq.equals(m11.group(1))) {
                                        String result = CQCode.at(userId) + " 【禁止套娃转发】本次转发无效无贝化值，请按正规途径转发";
                                        cq.sendGroupMsg(groupId, result, false);
                                        return super.onGroupMessage(cq, event);
                                    }
                                }

                                Zf zf = new Zf();
                                zf.setQq(qq);
                                zf.setTopic(topic.getTopic());
                                if (resourceService.getLastZf(zf) == null) {
                                    try {
                                        Pointlog pointlog = new Pointlog();
                                        pointlog.setOperated_qq_id(qq);
                                        pointlog.setOperate_point(topic.getPoint());
                                        pointlog.setOperate_type(1);
                                        pointlog.setType(2);
                                        pointlog.setDescription("【视频转发奖励】：" + title);
                                        pointService.insertPointlog(pointlog);
                                    } catch (Exception e) {
                                        String result = CQCode.at(userId) + " 【转发获得贝化值失败，请联系群管解决】";
                                        cq.sendGroupMsg(groupId, result, false);
                                        return super.onGroupMessage(cq, event);
                                    }
                                    resourceService.insertZf(zf);
                                    String result = CQCode.at(userId) + " 【感谢你的转发，已获得贝化值】，转发视频：" + title;
                                    cq.sendGroupMsg(groupId, result, false);
                                    return super.onGroupMessage(cq, event);
                                } else {
                                    String result = CQCode.at(userId) + " 【今天你已经转发过啦，无法重复获得贝化值】，转发视频：" + title;
                                    cq.sendGroupMsg(groupId, result, false);
                                    return super.onGroupMessage(cq, event);
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onGroupMessage(cq, event);
    }

    public static String getTitle(String cqcode, Long qq) {
        String pattern = "title=&#91;(.*?)&#93;";
        String sou = "";
        String title = "";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cqcode);
        if (m.find()) {
            sou = m.group(1);
            System.out.println(sou);
        } else {
            return "null";
        }

        if (sou.equals("QQ小程序")) {

            String pattern1 = "\"desc\":\"(.*?)\"&#44;";
            Pattern r1 = Pattern.compile(pattern1);
            Matcher m1 = r1.matcher(cqcode);
            if (m1.find()) {
                return m1.group(1);
            } else {
                return "null";
            }

        }

//        if (sou.equals("分享")) {
//            String pattern2 = "分享&#93;(.*?)]";
//            Pattern r2 = Pattern.compile(pattern2);
//            Matcher m2 = r2.matcher(cqcode);
//            if (m2.find()) {
//                return m2.group(1);
//            } else {
//                return "null";
//            }
//        }
        return "null";
    }

}
