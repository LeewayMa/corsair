package com.sishuok.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.dao.ChannelClassDao;
import com.sishuok.dao.SurveyClassDao;
import com.sishuok.dao.WeixinUserInfoDao;
import com.sishuok.dao.WeixinUserListDao;
import com.sishuok.dto.Channel;
import com.sishuok.dto.Surveys;
import com.sishuok.entity.ChannelClass;
import com.sishuok.entity.SurveyClass;
import com.sishuok.utils.DateUtils;
import com.sishuok.utils.JsonMapper;
import com.sishuok.utils.RedisUtil;
import com.sishuok.utils.StringUtils;
import com.sishuok.utils.excel.ExportExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/C2017/cs")
public class ChannelController {

//	private static final String path = "/CJ2017/Survey";
//	@Autowired
//	private WeixinUserInfoDao infoDao;
//	@Autowired
//	private WeixinUserListDao listDao;
	@Autowired
	private ChannelClassDao channelClassDao;
//	@Autowired
//	private RedisUtil redisUtil;

//	JsonMapper jm = JsonMapper.getInstance();

	String key = "surveys_count";

	@RequestMapping(value = "/{key}",method = RequestMethod.GET)
	public String index(@PathVariable String key, ModelMap map, HttpServletRequest request) {
		if(StringUtils.isBlank(key))return "链接地址错误";
		Channel surveys = new Channel();
		surveys.setChannelName(key);
		map.addAttribute("channel", surveys);
		return "Channel";
	}

	@RequestMapping(value = "/saveSurveys")
	@ResponseBody
	public HashMap saveSurveys(ModelMap map, @ModelAttribute(value = "prizes") Channel surveys) {

		log.info(" --#### 提交问卷 -- " + surveys);
		ChannelClass channel = channelClassDao.findByEmail(surveys.getEmail());
		HashMap data = Maps.newHashMap();

		if (channel != null) {
			data.put("result","xxx");
			data.put("code",channel.getCode());
			return data;
		}

		channel = new ChannelClass();
		try {
			BeanUtils.copyProperties(channel, surveys);
			channelClassDao.save(channel);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		log.info(" --#### id-- " + channel.getId());
//		channel.setCode((int)(Math.random()*10000)+""+channel.getId());
		channel.setCode(channel.getId());
		channelClassDao.save(channel);

//		redisUtil.rpush(key, jm.toJsonString(surveys));

		data.put("result","ok");
		data.put("code",channel.getCode());
		return data;
	}

	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportFile(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final_request = request;
		final_response = response;
		try {
			String fileName = "活动名单" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

			List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAll());
			new ExportExcel("活动名单", ChannelClass.class).setDataList(list).write(response, fileName).dispose();
		} catch (Exception e) {
			log.error("######################  导出失败-" + e);
		}
		return null;
	}

//	@RequestMapping(value = "/Go1")
//	public String saveSurveys(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		response.setCharacterEncoding("UTF-8");
//		map.addAttribute("path", path);
//		Surveys surveys = new Surveys();
//		map.addAttribute("surveys", surveys);
//
//		return "Survey-new";
//	}


	HttpServletRequest final_request;
	HttpServletResponse final_response;


	//向请求端发送返回数据
	public void print(String content) {
		try {
			final_response.getWriter().print(content);
			final_response.getWriter().flush();
			final_response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}