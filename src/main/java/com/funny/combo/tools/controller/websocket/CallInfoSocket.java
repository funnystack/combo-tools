package com.funny.combo.tools.controller.websocket;

import com.alibaba.fastjson.JSON;
import com.funny.combo.tools.dto.ws.WsCallDTO;
import com.funny.combo.tools.service.ws.WebSocketSupport;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

@ServerEndpoint("/call")
@Component
public class CallInfoSocket {


    private static Map<String, String> sessionMap = Maps.newConcurrentMap();

    /***
     * @Param: [id, session]
     * @Return: void
     * @Author: ZCH
     * @Date: 2021-01-10 09:02
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            session.setMaxIdleTimeout(60000);// 可以设置session最大空闲时间
            session.getBasicRemote().sendText("连接上了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     */
    @OnMessage
    public void onMessage(Session session, String text) {

        try {
            WsCallDTO wsCallDTO = JSON.parseObject(text, WsCallDTO.class);
            switch (wsCallDTO.getCmd()) {
                case "PING":
                    break;
                case "MAKE_CALL":

                    break;
                default:
                    throw new RuntimeException("error cmd");

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.getBasicRemote().sendText(MessageFormat.format("执行命令{0}异常{1}", text, e.getMessage()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    /**
     * 异常处理
     *
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(Session session) {
        try {
            WebSocketSupport.releaseSession(session);
        } catch (Exception e) {
        }
    }
}
