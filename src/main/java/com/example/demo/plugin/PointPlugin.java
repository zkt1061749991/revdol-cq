package com.example.demo.plugin;

import com.example.demo.domin.Account;
import com.example.demo.service.impl.AccountServiceImpl;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;


/**
 * 示例插件
 * 插件必须继承CQPlugin，上面要 @Component
 * <p>
 * 添加事件：光标移动到类中，按 Ctrl+O 添加事件(讨论组消息、加群请求、加好友请求等)
 * 查看API参数类型：光标移动到方法括号中按Ctrl+P
 * 查看API说明：光标移动到方法括号中按Ctrl+Q
 */
@Component
public class PointPlugin extends CQPlugin {


    @Autowired
    private AccountServiceImpl accountService;

    /**
     * 收到私聊消息时会调用这个方法
     *
     * @param cq    机器人对象，用于调用API，例如发送私聊消息 sendPrivateMsg
     * @param event 事件对象，用于获取消息内容、群号、发送者QQ等
     * @return 是否继续调用下一个插件，IGNORE表示继续，BLOCK表示不继续
     */
    @Override
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
        // 获取 发送者QQ 和 消息内容
        long userId = event.getUserId();
        String msg = event.getMessage();
        String qq = Long.toString(userId);
        if (msg.equals("查询贝化值") || msg.equals("贝化值查询")) {

            if (accountService.accountExist(qq)) {
                Account account = accountService.getAccount(qq);
                cq.sendPrivateMsg(userId, "账号：" + userId + "\n当前贝化值余额：" + account.getPoint(), false);
            } else {
                cq.sendPrivateMsg(userId, "你好像还没有注册哦！回复 \"贝化值注册\" 可直接完成注册哦！", false);
            }
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
//        愚人节彩蛋
//        if (msg.equals("贝化值查询")) {
//            if (accountService.accountExist(qq)) {
//                Account account = accountService.getAccount(qq);
//                Random ran = new Random();
//                int result = ran.nextInt(5);
//                switch (result) {
//                    case 0:cq.sendPrivateMsg(userId, "账号：" + userId + "\n当前贝化值余额：100000，恭喜您你已经成为了突破天际的贝化大神！", false);break;
//                    case 1:cq.sendPrivateMsg(userId, "账号：" + userId + "\n当前贝化值余额：-1000，确定你单推贝拉？！", false);break;
//                    case 2:cq.sendPrivateMsg(userId, "账号：" + userId + "\n当前贝化值余额：13860，贝拉拉的数字诶！", false);break;
//                    case 3:cq.sendPrivateMsg(userId, "账号：" + userId + "\n当前贝化值余额：20200401，愚人节快乐！", false);break;
//                    case 4:cq.sendPrivateMsg(userId, "账号：" + userId + "\n当前贝化值余额：666666，人生就像贝化值，六的一批", false);break;
//                }
//            } else {
//                cq.sendPrivateMsg(userId, "你好像还没有注册哦！回复 \"贝化值注册\" 可直接完成注册哦！", false);
//            }
//            // 不执行下一个插件
//            return MESSAGE_BLOCK;
//        }
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }
}
