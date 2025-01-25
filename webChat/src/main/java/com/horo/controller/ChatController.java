package com.horo.controller;

import com.horo.utils.ThreadLocalUtil;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WebSocketSever
 * @Author iove
 * @Date 2025/1/25 上午11:27
 * @Version 1.0
 * @Description TODO
 **/
@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class ChatController {

	private static Map<String, Session> sessionMap=new HashMap<>();

	/**
	 * 连接建立成功调用的方法
	 * @param session
	 * @param sid
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid){
		log.info("客户端"+sid+"建立连接,用户id为："+ ThreadLocalUtil.get());
		sessionMap.put(sid,session);
	}

	/**
	 *收到客户端消息调用的方法
	 * @param message
	 * @param sid
	 */
	@OnMessage
	public void onMessage(String message,@PathParam("sid") String sid) throws IOException {
		log.info("收到客户端"+sid+"的消息："+message);
		this.sendToAllClient(message);
	}

	/**
	 * 断开连接的方法
	 * @param session
	 * @param sid
	 */
	@OnClose
	public void onClose(Session session,@PathParam("sid") String sid){
		log.info(sid+"连接断开");
		sessionMap.remove(sid);
	}

	/**
	 * 群发消息
	 * @param message
	 */
	public void sendToAllClient(String message) throws IOException {
		Collection<Session> sessions=sessionMap.values();
		for(Session session:sessions){
			//群发消息
			session.getBasicRemote().sendText(message);
		}
	}

}
