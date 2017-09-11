package com.sishuok.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.WeixinUtil;

import com.sishuok.dao.PrizeClassDao;
import com.sishuok.dao.WeixinUserInfoDao;
import com.sishuok.dao.WeixinUserListDao;
import com.sishuok.dto.Prizes;
import com.sishuok.entity.PrizeClass;
import com.sishuok.entity.WeixinUserInfo;
import com.sishuok.entity.WeixinUserList;

//@Controller
@RequestMapping("/CJ2016/Corsair")
public class HelloController {

	private static final String path = "/CJ2016/Corsair";
	@Autowired
	private WeixinUserInfoDao infoDao;
	@Autowired
	private WeixinUserListDao listDao;
	@Autowired
	private PrizeClassDao prizeClassDao ;

	@RequestMapping("/Go")
	public String index(ModelMap map, HttpServletRequest request) {
		// 加入一个属性，用来在模板中读取
		map.addAttribute("path", path);
		// return模板文件的名称，对应src/main/resources/templates/index.html
		return "1";
	}

	@RequestMapping("/ComeOn")
	public String step2(ModelMap map) {
		map.addAttribute("path", path);
		return "2";
	}

	private void updateUserInfo() {
		long time = System.currentTimeMillis();
		WeixinUserList list = WeixinUtil.getUserInfoList();
		if (list == null || list.getCount() == 0)
			return;
		list.setCreateDate(new Date(time));
		listDao.save(list);
		// list = this.listDao.findAll();
		JSONArray openid = (JSONArray) list.getData().get("openid");
		// String next_openid = list.getNext_openid();

		// @SuppressWarnings("rawtypes")
		// ArrayList users = new ArrayList<WeixinUserInfo>();
		int i = list.getCount() - 1;
		for (; i >= 0; i--) {
			WeixinUserInfo user = WeixinUtil.getUserInfo((String) openid
					.get(i));
			if (null != user) {
				// users.add(user);
				user.setCreateDate(new Date(time));
				infoDao.save(user);
			}
		}
	}

	@RequestMapping("/Holla")
	public String step3(ModelMap map) {
		updateUserInfo();
		map.addAttribute("path", path);
		return "3";
	}

	@RequestMapping("/set")
	public String set(ModelMap map,@ModelAttribute(value="prizes") Prizes prizes) {
//		final_request.getParameter("signature");
//		updateUserInfo();
		List<PrizeClass> list = (List<PrizeClass>)prizeClassDao.findAll();
		Prizes prize = new Prizes();
//		prize.setPrizeClass((List<PrizeClass>)prizeClassDao.findAll());
//		for(int i=0;i<list.size();i++){
//			map.addAttribute("prize"+i, list.get(i).getPrizeClass());
			prize.setPrize0(list.get(0).getPrizeClass());
			prize.setPrize1(list.get(1).getPrizeClass());
			prize.setPrize2(list.get(2).getPrizeClass());
			prize.setPrize3(list.get(3).getPrizeClass());
			prize.setPrize4(list.get(4).getPrizeClass());
			map.addAttribute("prizes", prize);
//		}
		
			LinkedList<WeixinUserInfo> users = new LinkedList<WeixinUserInfo>();
		LinkedList<WeixinUserInfo> userss = (LinkedList<WeixinUserInfo>)infoDao.findByPrizeClassAndOrderByPrizeClass();
		for(WeixinUserInfo info : userss){
			if(info.getPrizeClass()!=0)
				users.add(info);
		}
		
		map.addAttribute("users", users);
		return "set";
	}
	@RequestMapping("/setSave0")
	public String setSave0(ModelMap map,@ModelAttribute(value="prizes") Prizes prizes) {
//		List<PrizeClass> list = (List<PrizeClass>)prizeClassDao.findAll();
		PrizeClass one = prizeClassDao.findOne(1);
		one.setPrizeClass(prizes.getPrize0());
		prizeClassDao.save(one);
		
		one = prizeClassDao.findOne(1);
		one.setPrizeClass(prizes.getPrize0());
		prizeClassDao.save(one);
		
		one = prizeClassDao.findOne(2);
		one.setPrizeClass(prizes.getPrize1());
		prizeClassDao.save(one);
		
		one = prizeClassDao.findOne(3);
		one.setPrizeClass(prizes.getPrize2());
		prizeClassDao.save(one);
		
		one = prizeClassDao.findOne(4);
		one.setPrizeClass(prizes.getPrize3());
		prizeClassDao.save(one);
		
		one = prizeClassDao.findOne(5);
		one.setPrizeClass(prizes.getPrize4());
		prizeClassDao.save(one);

		return "set";
	}

	@RequestMapping("/setSave1")
	public String setSave1(ModelMap map,HttpServletRequest request) {
//		Prizes prize = (Prizes)map.get("prizes");
		String pr = request.getParameter("prizesClass");
//		updateUserInfo();
//		List<PrizeClass> prizes = (List<PrizeClass>)prizeClassDao.findAll();
//		map.addAttribute("prizes", prizes);
		return "set";
	}
	@RequestMapping("/reset")
	@ResponseBody
	public String reset(ModelMap map,HttpServletRequest request) {

		ArrayList<WeixinUserInfo> list = (ArrayList<WeixinUserInfo>) this.infoDao
				.findAll();
		for(WeixinUserInfo info : list){
			info.setPrizeClass(Integer.valueOf(0));
			infoDao.save(info);
		}
		return "set";
	}
	@RequestMapping("/{step}")
	public String First_002_go(ModelMap map, @PathVariable String step) {
		long time = System.currentTimeMillis();
		// WeixinUserList list = WeixinUtil.getUserInfoList();
		// JSONArray openid = (JSONArray) list.getData().get("openid");
		ArrayList<WeixinUserInfo> list = (ArrayList<WeixinUserInfo>) this.infoDao
				.findAll();
		int total = list.size();
		@SuppressWarnings("rawtypes")
		ArrayList users = new ArrayList<WeixinUserInfo>();
		int i = 0;
		int prizeClass = Integer.valueOf(step.split("_")[1]);
		switch (prizeClass) {
		case 1:
			i = prizeClassDao.findOne(1).getPrizeClass();
			break;
		case 2:
			i = prizeClassDao.findOne(2).getPrizeClass();
			break;
		case 3:
			i = prizeClassDao.findOne(3).getPrizeClass();
			break;
		case 4:
			i = prizeClassDao.findOne(4).getPrizeClass();
			break;
		case 5:
			i = prizeClassDao.findOne(5).getPrizeClass();
			break;
		}
		for (; i > 0; i--) {
			WeixinUserInfo user = list.get(RandomUtils.nextInt(total));
			if (user.getPrizeClass() != 0) {
				i++;
				continue;
			}
			user.setPrizeClass(Integer.valueOf(prizeClass));
			user.setUpdateDate(new Date(time));
			this.infoDao.save(user);
			users.add(user);

		}
		map.put("prizeClass", prizeClass);
		map.put("users", users);
		map.addAttribute("path", path);
		return "First_002_go";
	}

	// private List<User> getUsersList() {
	// List<User> users = null;
	// HttpRequestUtils
	// .sendGet(
	// "https://api.weixin.qq.com/cgi-bin/user/get?access_token=oqn-Es6X9XL8KZPbZme-RIwEvjLcSRTFkVpEZIptKWxaF0f3YKaNnGUHgjg6t6iIRtyhZTj4v6NJERRilQ8FFuy1mgtBGU8P-9AU75aexJp1D2sGvZuyoiE3Gs4Bj97AZRSdAGAOGH",
	// "");
	// return users;
	// }

	// @RequestMapping("/CG2016/Corsair/First_001_go")
	public String First_001_go(ModelMap map) {
		WeixinUserList list = WeixinUtil.getUserInfoList();
		// System.out.println(list.getCount());
		// System.out.println(list.getTotal());
		// System.out.println(list.getNext_openid());
		// JSONObject obj = list.getData();
		JSONArray openid = (JSONArray) list.getData().get("openid");
		// System.out.println("openid=" + openid.get(1));
		WeixinUserInfo user = WeixinUtil.getUserInfo((String) openid
				.get(RandomUtils.nextInt(list.getCount() - 1)));
		// System.out.println("OpenID：" + user.getOpenId());
		// System.out.println("关注状态：" + user.getSubscribe());
		// System.out.println("关注时间：" + user.getSubscribeTime());
		// System.out.println("昵称：" + user.getNickname());
		// System.out.println("性别：" + user.getSex());
		// System.out.println("国家：" + user.getCountry());
		// System.out.println("省份：" + user.getProvince());
		// System.out.println("城市：" + user.getCity());
		// System.out.println("语言：" + user.getLanguage());
		// System.out.println("头像：" + user.getHeadImgUrl());
		map.put("user", user);
		return "First_001_go";
	}
}