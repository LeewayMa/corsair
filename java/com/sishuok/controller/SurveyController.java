package com.sishuok.controller;

import com.google.common.collect.Lists;
import com.sishuok.dao.SurveyClassDao;
import com.sishuok.dao.WeixinUserInfoDao;
import com.sishuok.dao.WeixinUserListDao;
import com.sishuok.dto.Surveys;
import com.sishuok.entity.SurveyClass;
import com.sishuok.utils.DateUtils;
import com.sishuok.utils.JsonMapper;
import com.sishuok.utils.RedisUtil;
import com.sishuok.utils.excel.ExportExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/CJ2017/Survey")
public class SurveyController {

	private static final String path = "/CJ2017/Survey";
	@Autowired
	private WeixinUserInfoDao infoDao;
	@Autowired
	private WeixinUserListDao listDao;
	@Autowired
	private SurveyClassDao serveyClassDao;
//	@Autowired
	private RedisUtil redisUtil;

	JsonMapper jm = JsonMapper.getInstance();

	String key = "surveys_count";

	@RequestMapping("/Go")
	public String index(ModelMap map, HttpServletRequest request) {
		// 加入一个属性，用来在模板中读取
		map.addAttribute("path", path);
		Surveys surveys = new Surveys();
		map.addAttribute("surveys", surveys);
		return "Survey";
	}

	@RequestMapping(value = "/Go1")
	public String saveSurveys(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		map.addAttribute("path", path);
		Surveys surveys = new Surveys();
		map.addAttribute("surveys", surveys);

		return "Survey-new";
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
//			List<Surveys> list = Lists.newArrayList();
//			String page = redisUtil.get(this.key);
			String page = redisUtil.lpop(this.key);
			for (; page != null; page = redisUtil.lpop(this.key)) {
				log.info("######################  新增问卷 -" + page);
//				System.out.println("######################  " + page);
				Surveys surveys = JsonMapper.getInstance().fromJson(page, Surveys.class);
//				list.add(surveys);

				if (serveyClassDao.findByEmail(surveys.getEmail()) != null) {
					log.info("###################### 邮箱重复 " + surveys.getEmail());
					continue;
				}
				SurveyClass entity = new SurveyClass();
				try {
					BeanUtils.copyProperties(entity, surveys);
					serveyClassDao.save(entity);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

//			list = JsonMapper.getInstance().fromJson(page, Surveys.class);
//			log.info("###################### list.size() " + list.size());
//			System.out.println("######################  list.size() " + list.size());
			List<SurveyClass> list = Lists.newArrayList(serveyClassDao.findAll());
			new ExportExcel("活动名单", SurveyClass.class).setDataList(list).write(response, fileName).dispose();
		} catch (Exception e) {
//			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
			log.error("######################  导出失败-" + e);
		}
		return null;
	}

	HttpServletRequest final_request;
	HttpServletResponse final_response;

	@RequestMapping(value = "/saveSurveys")
	@ResponseBody
	public String saveSurveys(ModelMap map, @ModelAttribute(value = "prizes") Surveys surveys) {

		log.info(" --#### 提交问卷 -- " + surveys);
		if (serveyClassDao.findByEmail(surveys.getEmail()) != null) return "ok";

		redisUtil.rpush(key, jm.toJsonString(surveys));
//		SurveyClass entity = new SurveyClass();
//		try {
//			BeanUtils.copyProperties(entity, surveys);
//			serveyClassDao.save(entity);
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}

		return "ok";
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