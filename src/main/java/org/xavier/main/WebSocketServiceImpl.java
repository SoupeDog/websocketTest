package org.xavier.main;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.07.26
 * @since Jdk 1.8
 */
@CrossOrigin
@Service
@ServerEndpoint(value = "/webSocket/{sid}")
public class WebSocketServiceImpl {
    private WebSocketCache webSocketCache = WebSocketCacheFactory.getInstance_WebSocketCache();
    private Session session;
    private String sid;

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        webSocketCache.saveConnectionBySId(sid, this);

        try {
            sendMessage("[" + sid + "]: 连接成功");
            System.out.println("WebSocket[" + sid + "] opened.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketCache.removeConnectionBySId(sid);
        this.session = null;
        System.out.println("WebSocket[" + sid + "] closed.");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        for (Map.Entry<String, WebSocketServiceImpl> entry : webSocketCache.getCache().entrySet()) {
            entry.getValue().sendMessage(message);
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        if (session!=null) {
            this.session.getBasicRemote().sendText(message);
        } else {
            System.out.println("[" + sid + "] session 已经关闭.");
        }

    }
}
