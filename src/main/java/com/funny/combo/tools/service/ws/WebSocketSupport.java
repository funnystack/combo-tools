package com.funny.combo.tools.service.ws;

import com.alibaba.fastjson.JSON;
import com.funny.combo.tools.dto.ws.MsgDTO;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

public class WebSocketSupport {

    private static final WsSessionManager sessionManager = new WsSessionManager();

    /**
     * 尝试向客户端推送消息
     *
     * @param msgDTO
     */
    public static void tryPush(MsgDTO msgDTO) {
        if (msgDTO == null)
            return;

        String userId = String.valueOf(msgDTO.getReceiverId());
        Session session = sessionManager.get(userId);

        if (session != null && session.isOpen()) {
            push(session, msgDTO.getMsgBody());
        }

    }

    public static void push(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storageSession(Session session) {
        String querystring = session.getQueryString();

        if (!StringUtils.isEmpty(querystring)) {
            Map<String, String> param = JSON.parseObject(querystring, Map.class);
            String key = param.get("userId");

            sessionManager.save(key, session);
        }
    }

    public static Session getSession(String id) {
        return sessionManager.get(id);
    }

    public static void releaseSession(String id) {
        sessionManager.releaseSession(id);
    }

    public static void releaseSession(Session session) {
        String querystring = session.getQueryString();

        if (!StringUtils.isEmpty(querystring)) {
            Map<String, String> param = JSON.parseObject(querystring, Map.class);
            String key = param.get("userId");

            sessionManager.releaseSession(key);
        }
    }
}
