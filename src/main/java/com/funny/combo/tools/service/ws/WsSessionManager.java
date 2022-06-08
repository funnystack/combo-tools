package com.funny.combo.tools.service.ws;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WsSessionManager {

    final ConcurrentHashMap<Object, Session> sessionPool = new ConcurrentHashMap<>();

    void save(Object key, Session session) {

        sessionPool.put(key, session);
    }

    Session get(String key) {
        return sessionPool.get(key);
    }

    boolean haveSession(String key) {
        return sessionPool.containsKey(key);
    }

    void releaseSession(String key) {
        Session session = sessionPool.remove(key);
        try {
            if (session.isOpen())
                session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
