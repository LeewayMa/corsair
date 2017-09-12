package com.sishuok.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.dao.ChannelClassDao;
import com.sishuok.dto.Channel;
import com.sishuok.entity.ChannelClass;
import com.sishuok.utils.DateUtils;
import com.sishuok.utils.StringUtils;
import com.sishuok.utils.excel.ExportExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
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

	private static final HashMap<String, Integer> codeMap = new HashMap<>();
//	@Autowired
//	private RedisUtil redisUtil;

	//	JsonMapper jm = JsonMapper.getInstance();
	String key = "surveys_count";
	HttpServletRequest final_request;
	HttpServletResponse final_response;
	//	private static final String path = "/CJ2017/Survey";
//	@Autowired
//	private WeixinUserInfoDao infoDao;
//	@Autowired
//	private WeixinUserListDao listDao;
	@Autowired
	private ChannelClassDao channelClassDao;

	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public String index(@PathVariable String key, ModelMap map, HttpServletRequest request) {
		if (StringUtils.isBlank(key)) return "链接地址错误";
		Channel surveys = new Channel();
		surveys.setChannelName(key);
		map.addAttribute("channel", surveys);
		return "Channel";
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

	@RequestMapping(value = "/saveSurveys")
	@ResponseBody
	public HashMap saveSurveys(ModelMap map, @ModelAttribute(value = "prizes") Channel surveys) {

		log.info(" --#### 提交问卷 -- " + surveys);
		ChannelClass channel = channelClassDao.findByEmail(surveys.getEmail());
		HashMap data = Maps.newHashMap();

		if (channel != null) {
			data.put("result", "xxx");
			data.put("code", channel.getCode());
			return data;
		}
		int maxCode = 1;

		if (codeMap.get(surveys.getChannelName()) != null) {
			maxCode = codeMap.get(surveys.getChannelName());
		}
//		if (codeMap.get(surveys.getChannelName()) == null) {
//			int maxCode = channelClassDao.findMaxCodeByChannelName(surveys.getChannelName());
//			maxCode = channelClassDao.findByChannelName(surveys.getChannelName());
//			Iterator<ChannelClass> iss = channelClassDao.findAllByChannelName(surveys.getChannelName());

//			List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAllByChannelName(surveys.getChannelName()));
//			if (list.size() > 0) maxCode = list.get(list.size() - 1).getCode();
//			if (maxCode == -1) {
//				codeMap.put(surveys.getChannelName(), 1);
//			} else {
//				codeMap.put(surveys.getChannelName(), maxCode + 1);
//			}
//			codeMap.put(surveys.getChannelName(), maxCode);
//		}else{
//
//			codeMap.put(surveys.getChannelName(), 1);
//		}

		channel = new ChannelClass();
		try {
			BeanUtils.copyProperties(channel, surveys);
			channel.setCode(maxCode++);
			codeMap.put(surveys.getChannelName(), maxCode);
			channelClassDao.save(channel);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		log.info(" --#### id-- " + channel.getId());
//		channel.setCode((int)(Math.random()*10000)+""+channel.getId());
//		channel.setCode(channel.getId());
//		channelClassDao.save(channel);

//		redisUtil.rpush(key, jm.toJsonString(surveys));

		data.put("result", "ok");
		data.put("code", channel.getCode());
		return data;
	}

	@RequestMapping(value = "export/{key}", method = RequestMethod.GET)
	public String exportFile(@PathVariable String key, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(key)) return "链接地址错误";
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
//			List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAllByChannelName(key));
			List<ChannelClass> listsssss=Lists.newArrayList();
for(ChannelClass ch:list){
	if(ch.getChannelName().equals(key)){
		listsssss.add(ch);
	}
}
			new ExportExcel("活动名单", ChannelClass.class).setDataList(listsssss).write(response, fileName).dispose();
		} catch (Exception e) {
			log.error("######################  导出失败-" + e);
		}
		return null;
	}

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