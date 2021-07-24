package com.example.demo.plugin;

import com.example.demo.domin.Signon;
import com.example.demo.domin.Topic;
import com.example.demo.service.impl.ResourceServiceImpl;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddZfTopicPlugin extends CQPlugin {
    @Autowired
    private ResourceServiceImpl resourceService;

//    @Override
//    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
//        // 获取 发送者QQ 和 消息内容
//        long userId = event.getUserId();
//        String msg = event.getMessage();
//        Topic topic = new Topic();
//        if (msg.contains("添加转发#")) {
//            String title = getTitle(msg);
//            int point = getPoint(msg);
//            if (!title.equals("null")) {
//                topic.setTopic(title);
//                if (point > 0 && point <= 20) {
//                    topic.setPoint(point);
//                } else {
//                    topic.setPoint(5);
//                }
//                try {
//                    resourceService.insertZfTopic(topic);
//                    String result = "\n添加贝化值转发小任务成功！\n视频标题：\n" + topic.getTopic() + "\n可获得贝化值：" + topic.getPoint();
//                    cq.sendPrivateMsg(userId, result, false);
//                } catch (Exception e) {
//                    String result = "添加贝化值转发视频失败！服务器出错，请联系管理员解决";
//                    cq.sendPrivateMsg(userId, result, false);
//                }
//            } else {
//                String result = "添加贝化值转发视频失败！格式错误（添加转发#完整标题#[可获取贝化值不写则默认为5]）";
//                cq.sendPrivateMsg(userId, result, false);
//            }
//
//            // 不执行下一个插件
//            return MESSAGE_BLOCK;
//        }
//
//        // 继续执行下一个插件
//        return MESSAGE_IGNORE;
//    }

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        Long groupId = event.getGroupId();
        Long userId = event.getUserId();
        String msg = event.getMessage();
        Topic topic = new Topic();
        if (msg.contains("添加转发#") && (groupId == 883078412 || groupId == 574057826 || groupId == 976515737)) {
            String title = getTitle(msg);
            int point = getPoint(msg);

            if (!title.equals("null")) {

                topic.setTopic(title);
                if (!resourceService.ZfTopicExist(topic)) {


                    if (point > 0 && point <= 20) {
                        topic.setPoint(point);
                    } else {
                        topic.setPoint(5);
                    }
                    try {
                        resourceService.insertZfTopic(topic);
                        String result = CQCode.at(userId) + "\n添加贝化值转发小任务成功！\n视频标题：\n" + topic.getTopic() + "\n可获得贝化值：" + topic.getPoint();
                        cq.sendGroupMsg(groupId, result, false);
                    } catch (Exception e) {
                        String result = CQCode.at(userId) + "添加贝化值转发视频失败！服务器出错，请联系管理员解决";
                        cq.sendGroupMsg(groupId, result, false);
                    }
                }  else {
                    String result = CQCode.at(userId) + "已添加贝化值转发视频，请勿重复添加";
                    cq.sendGroupMsg(groupId, result, false);
                }
            } else {
                String result = CQCode.at(userId) + "添加贝化值转发视频失败！格式错误（添加转发#完整标题#[可获取贝化值不写则默认为5]）";
                cq.sendGroupMsg(groupId, result, false);
            }
        }
        return super.onGroupMessage(cq, event);
    }

    public static String getTitle(String cqcode) {
        String pattern = "#(.*)#";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cqcode);
        if (m.find()) {
            return m.group(1);
        } else {
            return "null";
        }
    }

    public static int getPoint(String cqcode) {
        String pattern = "[^#]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cqcode);
        if (m.find()) {
            String point = m.group(0);
            if (isNumeric(point)) {
                return Integer.valueOf(point);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

//    public static void main(String[] args) {
//        //getOfficialForum(forum, member);
//        System.out.println(getPoint("添加转发#ashfio#awhi#22"));
//    }

}

