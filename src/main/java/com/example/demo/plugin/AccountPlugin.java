package com.example.demo.plugin;

import com.example.demo.domin.Account;
import com.example.demo.domin.Signon;
import com.example.demo.service.impl.AccountServiceImpl;
import com.example.demo.service.impl.SignonServiceImpl;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountPlugin extends CQPlugin {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private SignonServiceImpl signonService;

    @Override
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
        // 获取 发送者QQ 和 消息内容
        long userId = event.getUserId();
        String msg = event.getMessage();

        if (msg.equals("贝化值注册") || msg.equals("注册贝化值")) {
            String qq = Long.toString(userId);
            if (accountService.accountExist(qq)) {
                cq.sendPrivateMsg(userId, "贝化值账号："+userId+" 已存在，请勿重复注册", false);
            } else {
                Signon signon = new Signon();
                signon.setQq(qq);
                signon.setPassword("123456");
                try {
                    signonService.insertSignon(signon);
                    cq.sendPrivateMsg(userId, "注册成功！\n账号：" + qq + "\n初始查询密码：123456" + "\n可以开始积累你的贝化值啦！\n可登录网站完善账号资料或修改查询密码", false);
                } catch (Exception e) {
                    cq.sendPrivateMsg(userId, "注册失败，请尝试使用网页注册或联系群管理员", false);
                }
            }
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }
}
