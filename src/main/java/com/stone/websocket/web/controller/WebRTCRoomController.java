package com.stone.websocket.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rtcroom")
public class WebRTCRoomController {

	@RequestMapping(value = "/room")
	public ModelAndView room(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String r = request.getParameter("r");
		if (StringUtils.isEmpty(r)) {// 如果房间为空，则生成一个新的房间号
			r = String.valueOf(System.currentTimeMillis());
			mv.setViewName("redirect:/rtcroom/room?r=" + r);
		} else {
			Integer initiator = 1;
			String user = UUID.randomUUID().toString().replace("-", "");
			// 第一次进入可能是没有人的，所以就要等待连接，如果有人进入了带这个房间好的页面就会发起视频通话的连接
			if (!WebRTCRoomManager.haveUser(r)) {
				initiator = 0;// 如果房间没有人则不发送连接的请求
			}
			WebRTCRoomManager.addUser(r, user);// 向房间中添加一个用户

			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
			String roomLink = basePath + "room?" + "r";
			String roomKey = r;
			mv.addObject("initiator", initiator);
			mv.addObject("roomLink", roomLink);
			mv.addObject("roomKey", roomKey);
			mv.addObject("user", user);
			mv.setViewName("/webrtc/room");
		}
		return mv;
	}
}
