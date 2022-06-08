package com.funny.combo.tools.controller.websocket;

import com.funny.combo.tools.service.ws.WebSocketSupport;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/send/voice")
@Component
public class SendVoiceSocket {

    private static final Map<String, Session> connections = new Hashtable<>();
    private static ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    /***
     * @Description:打开连接
     * @Param: [id, session]
     * @Return: void
     * @Author: ZCH
     * @Date: 2021-01-10 09:02
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            session.setMaxIdleTimeout(60000);// 可以设置session最大空闲时间
            System.out.println(session.getId() + "连上了");
            WebSocketSupport.storageSession(session);
            session.getBasicRemote().sendText("连接上了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     */
    @OnMessage
    public void onMessage(Session session, InputStream inputStream) {
        System.out.println("onMessage+" + session.getId());
        try {
            int rc = 0;
            byte[] buff = new byte[100];
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                byteArrayOutputStream.write(buff, 0, rc);
            }
            connections.get(session.getId()).getBasicRemote().sendText("接收信息中");
        } catch (Exception e) {
            e.printStackTrace();
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
        System.out.println(session.getId() + "断开");
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            System.out.println(byteArrayOutputStream.toByteArray().length);
            file = new File("/Users/fangli/record/" + session.getId() + ".mp3");
//            file = new File("/opt/nineday/testtest.mp3");
            //输出流
            fos = new FileOutputStream(file);
            //缓冲流
            bos = new BufferedOutputStream(fos);
            //将字节数组写出
            bos.write(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            WebSocketSupport.releaseSession(session);
            session.getBasicRemote().sendText("关闭了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
