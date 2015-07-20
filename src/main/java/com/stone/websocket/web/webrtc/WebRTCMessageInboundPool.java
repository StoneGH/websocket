package com.stone.websocket.web.webrtc;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

public class WebRTCMessageInboundPool {
	private static final Map<String, WebRTCMessageInbound> connections = new HashMap<String, WebRTCMessageInbound>();

	/**
	 * 添加连接
	 * 
	 * @param webRTCMessageInbound
	 */
	public static void addMessageInbound(WebRTCMessageInbound inbound) {
		System.out.println("user:" + inbound.getUser() + " join..");
		connections.put(inbound.getUser(), inbound);
	}

	/**
	 * 移除连接
	 * 
	 * @param webRTCMessageInbound
	 */
	public static void removeMessageInbound(WebRTCMessageInbound inbound) {
		connections.remove(inbound.getUser());
	}

	/**
	 * 发送消息
	 * 
	 * @param user
	 * @param message
	 */
	public static void sendMessage(String user, String message) {
		System.out.println("send message to user:" + user
				+ "message conenction:" + message);
		WebRTCMessageInbound inbound = connections.get(user);
		if (null != inbound) {
			try {
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
