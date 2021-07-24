package com.example.demo.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;


/**
 * 示例插件
 * 插件必须继承CQPlugin，上面要 @Component
 *
 * 添加事件：光标移动到类中，按 Ctrl+O 添加事件(讨论组消息、加群请求、加好友请求等)
 * 查看API参数类型：光标移动到方法括号中按Ctrl+P
 * 查看API说明：光标移动到方法括号中按Ctrl+Q
 */
@Component
public class DemoPlugin extends CQPlugin {
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

        if (msg.equals("连接测试")) {
            // 调用API发送hello
            cq.sendPrivateMsg(userId, "WebSocket连接正常", false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }

        if (msg.equals("贝化值网站") || msg.equals("贝化值网页") || msg.equals("贝化值网址") ) {
            // 调用API发送hello
            cq.sendPrivateMsg(userId, "http://isabella.revdol.club/account/space\n被恶意举报遭QQ拦截，暂时请复制网址用浏览器访问", false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }

        if (msg.equals("贝化值")) {
            // 调用API发送hello
            cq.sendPrivateMsg(userId, "可回复我以下指令：\n" +
                    "\"贝化值说明\"：了解应援系统\n" +
                    "\"贝化值注册\"：注册贝化值账号\n" +
                    "\"贝化值查询\"：显示当前贝化值余额\n" +
                    "\"二创登记\"：自动登记小程序二创帖（需先绑定小程序战姬众号）", false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }
}
