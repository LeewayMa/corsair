package com.sishuok.controller;
//http://git.oschina.net/didispace/SpringBoot-Learning/tree/master

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
//@Controller
@RequestMapping("/weixinCore")
public class OuthController {
	//WeiXinHandler为内部类不能使用非final类型的对象  
	final String TOKEN = "hokuden_1981@PRC";
	HttpServletRequest final_request;
	HttpServletResponse final_response;

	@RequestMapping("/valid")
	@ResponseBody
	public void valid(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		final_request = request;
		final_response = response;
		String echostr = request.getParameter("echostr");
		log.info("#####  echostr  {}", echostr);
		if (null == echostr || echostr.isEmpty()) {
			responseMsg();
		} else {
			if (this.checkSignature()) {
				this.print(echostr);
			} else {
				this.print("error");
			}
		}
	}

	//自动回复内容
	public void responseMsg() {
		String postStr = null;
		try {
			postStr = this.readStreamParameter(final_request.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(postStr);
		if (null != postStr && !postStr.isEmpty()) {
			Document document = null;
			try {
				document = DocumentHelper.parseText(postStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				this.print("");
				return;
			}
			Element root = document.getRootElement();
			log.info("#####  {}", root.getText());
			String fromUsername = root.elementText("FromUserName");
			String toUsername = root.elementText("ToUserName");
			String keyword = root.elementTextTrim("Content");
			String time = new Date().getTime() + "";
			String textTpl = "<xml>" +
					"<ToUserName><![CDATA[%1$s]]></ToUserName>" +
					"<FromUserName><![CDATA[%2$s]]></FromUserName>" +
					"<CreateTime>%3$s</CreateTime>" +
					"<MsgType><![CDATA[%4$s]]></MsgType>" +
					"<Content><![CDATA[%5$s]]></Content>" +
					"<FuncFlag>0</FuncFlag>" +
					"</xml>";

			if (null != keyword && !keyword.equals("")) {
				String msgType = "text";
				String contentStr = "你好，我是西瓜爸爸，有事您说话!";
				String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
				this.print(resultStr);
			} else {
				this.print("Input something...");
			}

		} else {
			this.print("");
		}
	}

	//微信接口验证
	public boolean checkSignature() {
		String signature = final_request.getParameter("signature");
		String timestamp = final_request.getParameter("timestamp");
		String nonce = final_request.getParameter("nonce");
		String token = TOKEN;
		String[] tmpArr = {token, timestamp, nonce};
		Arrays.sort(tmpArr);
		String tmpStr = this.ArrayToString(tmpArr);
		tmpStr = this.SHA1Encode(tmpStr);
		if (tmpStr.equalsIgnoreCase(signature)) {
			return true;
		} else {
			return false;
		}
	}

//	public static void main(String[] args) {
//		long ss = System.currentTimeMillis();
//		System.out.println(ss);
//	}

	//向请求端发送返回数据
	public void print(String content) {
		try {
			final_response.getWriter().print(content);
			final_response.getWriter().flush();
			final_response.getWriter().close();
		} catch (Exception e) {

		}
	}

	//数组转字符串
	public String ArrayToString(String[] arr) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			bf.append(arr[i]);
		}
		return bf.toString();
	}

	//sha1加密
	public String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	//从输入流读取post参数
	public String readStreamParameter(ServletInputStream in) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
}  

