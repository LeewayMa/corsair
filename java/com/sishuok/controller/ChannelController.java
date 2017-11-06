package com.sishuok.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.dao.ChannelClassDao;
import com.sishuok.dto.Channel;
import com.sishuok.dto.ChannelResult;
import com.sishuok.entity.ChannelClass;
import com.sishuok.utils.DateUtils;
import com.sishuok.utils.RedisUtil;
import com.sishuok.utils.StringUtils;
import com.sishuok.utils.excel.ExportExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/C2017/cs")
public class ChannelController {
	private static final HashMap<String, Integer> codeMap = new HashMap<>();
	private static List<String> questions = new ArrayList();
	private static HashMap<Integer, Integer> answer = new HashMap<Integer, Integer>();
	private static int[] kkk = {0, 7, 19, 22, 28, 34, 39, 46, 53, 62, 70, 76, 81, 88, 96, 103, 109, 118, 125, 128};

	static {
		questions.add("1、您平时都有玩哪些类型的游戏？（多选）");
		questions.add("A、MOBA类");
		questions.add("B、RTS即时策略类");
		questions.add("C、FPS第一人称视角类");
		questions.add("D、格斗类");
		questions.add("F、MMORPG类");
		questions.add("F、其他");
		questions.add("2、您目前使用的键盘品牌是？");
		questions.add("A、美商海盗船");
		questions.add("B、Filco");
		questions.add("C、Cherry");
		questions.add("D、达尔优");
		questions.add("E、罗技");
		questions.add("F、雷蛇");
		questions.add("G、赛睿");
		questions.add("H、双飞燕");
		questions.add("I、凯酷");
		questions.add("J、Ducky");
		questions.add("L、其他");
		questions.add("3、您更喜欢使用薄膜键盘还是机械键盘？");
		questions.add("A、薄膜键盘（回答第4题）");
		questions.add("B、机械键盘（回答第5题）");
		questions.add("4、您为什么更喜欢用薄膜键盘？");
		questions.add("A、按键更轻柔");
		questions.add("B、安静的手感");
		questions.add("C、快速的按键回弹");
		questions.add("D、价格更便宜");
		questions.add("E、其他");
		questions.add("5、您为什么更喜欢机械键盘？");
		questions.add("A、更持久的使用寿命和恒久不变的手感");
		questions.add("B、精确的按键");
		questions.add("C、背光灯");
		questions.add("D、我喜欢更大的按键声音");
		questions.add("E、其他");
		questions.add("6、近期是否有买一个新键盘的计划？");
		questions.add("A、当然要买");
		questions.add("B、如果见到合适的会买");
		questions.add("C、如果有促销或折扣会买");
		questions.add("D、不会购买");
		questions.add("7、您愿意花费多少钱购买键盘？");
		questions.add("A、低于400元");
		questions.add("B、401~600元");
		questions.add("C、601~1000元");
		questions.add("D、1001~1500元");
		questions.add("E、1501~2000元");
		questions.add("F、我不在乎价钱");
		questions.add("8、您喜欢那种轴体？");
		questions.add("A、cherry红轴");
		questions.add("B、cherry茶轴");
		questions.add("C、cherry黑轴");
		questions.add("D、cherry青轴");
		questions.add("E、cherry银轴");
		questions.add("F、其他");
		questions.add("9、如果您要买一个新键盘，您会选择下列哪个品牌？");
		questions.add("A、美商海盗船");
		questions.add("B、Filco");
		questions.add("C、cherry");
		questions.add("D、达尔优");
		questions.add("E、罗技");
		questions.add("F、雷蛇");
		questions.add("G、赛睿");
		questions.add("E、其他");
		questions.add("10、您最喜欢机械键盘的什么特点？");
		questions.add("A、持久耐用和高品质");
		questions.add("B、优秀的轴体");
		questions.add("C、炫酷的灯光效果");
		questions.add("D、能自己进行DIY设计和更换键帽");
		questions.add("E、防水防尘的设计");
		questions.add("F、多媒体控制");
		questions.add("G、其他");
		questions.add("11、您最喜欢怎样的键帽？");
		questions.add("A、看起来很酷的");
		questions.add("B、触感好的");
		questions.add("C、耐用的");
		questions.add("D、能实时反馈的");
		questions.add("E、其他");
		questions.add("12、请问您是否了解美商海盗船的键盘产品？");
		questions.add("A、我很了解美商海盗船的键盘产品");
		questions.add("B、我对美商海盗船的键盘产品有所了解");
		questions.add("C、听说过但是并不十分了解");
		questions.add("D、完全不知道");
		questions.add("13、您是通过什么渠道了解到美商海盗船以及它的产品的信息的？");
		questions.add("A、杂志报刊");
		questions.add("B、美商海盗船的社交媒体");
		questions.add("C、朋友推荐的");
		questions.add("D、零售商的网站");
		questions.add("E、美商海盗船的官网");
		questions.add("F、其他");
		questions.add("14、您认为美商海盗船的机械键盘的优点是什么？");
		questions.add("A、高品质");
		questions.add("B、采用cherry轴体");
		questions.add("C、RGB灯光效果");
		questions.add("D、人体工程学设计");
		questions.add("E、合适的价格和高性价比");
		questions.add("F、持久耐用");
		questions.add("G、其他");
		questions.add("15、您认为美商海盗船的键盘还有什么需要改进的地方？");
		questions.add("A、增强产品的个性化");
		questions.add("B、键盘驱动太难运行");
		questions.add("C、增加DIY的便捷性");
		questions.add("D、太多相似的键盘，没有差异化");
		questions.add("E、价格太贵了");
		questions.add("F、其他");
		questions.add("16、您是否有考虑购买一把美商海盗船的机械键盘？");
		questions.add("A、当然，这是信仰");
		questions.add("B、如果看到有什么特别的我会购买");
		questions.add("C、如果有促销和打折我会购买");
		questions.add("D、不，虽然我喜欢但是太贵了");
		questions.add("E、不，我完全不会考虑购买");
		questions.add("17、您最喜欢美商海盗船的哪款机械键盘？");
		questions.add("A、K95 RGB PLATINUM");
		questions.add("B、K70 RGB");
		questions.add("C、STRAFE RGB");
		questions.add("D、K65 RGB");
		questions.add("E、K68");
		questions.add("F、K63");
		questions.add("G、K66");
		questions.add("H、我不清楚美商海盗船的键盘");
		questions.add("18、您的年龄是？");
		questions.add("A、12岁以下");
		questions.add("B、13~18岁");
		questions.add("C、19~24岁");
		questions.add("D、25~30岁");
		questions.add("E、31~40岁");
		questions.add("F、40以上");
		questions.add("19、您的性别是？");
		questions.add("A、男性");
		questions.add("B、女性");
		questions.add("20、您的职业是？");
		questions.add("A、学生");
		questions.add("B、一般企业工作人员");
		questions.add("C、公务员或国企、事业单位员工");
		questions.add("D、自由工作者/个体户");
		questions.add("E、其他");

	}

//	static {
//		this.getMaxCode();
//	}

	HttpServletRequest final_request;
	HttpServletResponse final_response;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ChannelClassDao channelClassDao;

	//调查答卷导出之前，初始化
	private static void init() {
		answer.clear();
		for (int j = 0; j < 134; j++) {
			boolean flag = false;
			for (int k : kkk) {
				if (j == k) flag = true;
			}
			if (!flag) {
				answer.put(j, 0);
			} else
				answer.put(j, null);
		}
	}

	private void getMaxCode() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAll());
		for (ChannelClass ch : list) {
//			int maxCode = 1;
			if (map.get(ch.getChannelName()) == null) {

				map.put(ch.getChannelName(), 1);
				ch.setCode(1);
			} else {
				map.put(ch.getChannelName(), map.get(ch.getChannelName()) + 1);
				ch.setCode(map.get(ch.getChannelName()));
			}
		}
		channelClassDao.save(list);

		Set<String> keys = map.keySet();
		for (String ch : keys) {
			redisUtil.set(ch, map.get(ch) + 1);
		}
	}

	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public String index(@PathVariable String key, ModelMap map, HttpServletRequest request) {
		if (StringUtils.isBlank(key)) return "链接地址错误";
		Channel surveys = new Channel();
		surveys.setChannelName(key);
		map.addAttribute("channel", surveys);
		return "Channel";
	}

	@RequestMapping(value = "/saveSurveys")
	@ResponseBody
	@Transactional
	public HashMap saveSurveys(ModelMap map, @ModelAttribute(value = "prizes") Channel surveys) {
		//每次保存前，同步maxcode值
		this.getMaxCode();

		log.info(" --#### 提交问卷 -- " + surveys);
		List<ChannelClass> channels = channelClassDao.findByChannelNameAndEmail(surveys.getChannelName(), surveys.getEmail());
		HashMap data = Maps.newHashMap();

		if (channels != null && channels.size() > 0) {
			data.put("result", "xxx");
			data.put("code", channels.get(0).getCode());
			return data;
		}
		int maxCode = 1;
		try {

			maxCode = Integer.valueOf(redisUtil.get(surveys.getChannelName()));

		} catch (Exception e) {
			log.error("######################  导出" + surveys.getChannelName() + "-" + e);
		}

		ChannelClass channel = new ChannelClass();
		try {
			BeanUtils.copyProperties(channel, surveys);
			channel.setCode(maxCode);
//			codeMap.put(surveys.getChannelName(), maxCode);
			redisUtil.set(surveys.getChannelName(), maxCode++);
			channelClassDao.save(channel);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		log.info(" --#### id-- " + channel.getId());

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
			//每次保存前，同步maxcode值
			this.getMaxCode();

			String fileName = "活动名单" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

			log.info("######################  导出-" + key + " " + fileName);
			List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAll());
//			List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAllByChannelName(key));
			List<ChannelClass> listsssss = Lists.newArrayList();
			for (ChannelClass ch : list) {
				if (ch.getChannelName().equals(key)) {
					listsssss.add(ch);
				}
			}
			if (listsssss.size() > 0
					) new ExportExcel("活动名单", ChannelClass.class).setDataList(listsssss).write(response, fileName).dispose();
		} catch (Exception e) {
			log.error("######################  导出失败-");
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "result", method = RequestMethod.GET)
	public String result(String key, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(key)) return "key错误";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final_request = request;
		final_response = response;
		try {
			String fileName = "结果：" + key + "-" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

			log.info("######################  " + fileName);
			List<ChannelClass> list = Lists.newArrayList(channelClassDao.findAll());
			List<ChannelResult> listsssss = Lists.newArrayList();
//			ArrayList<HashMap<Integer, Integer>> anssList = new ArrayList(20);
			this.init();

			for (ChannelClass ch : list) {
				if (!StringUtils.isBlank(key) && !key.equals("a") && !key.equals(ch.getChannelName())) {
					log.info("#### key-" + key + " getChannelName-" + ch.getChannelName());
					continue;
				}
				log.info("#### ch-" + ch.getId());

				//1111111111
				String[] first = ch.getSurvey1().split(",");
				int sco;
				for (String ans : first) {
					sco = answer.get(Integer.valueOf(ans));
					answer.put(Integer.valueOf(ans), sco == 0 ? 1 : sco + 1);
				}
//
				//22222222
				int ans = ch.getSurvey2();
				sco = answer.get(7 + ans);
				answer.put(7 + ans, sco == 0 ? 1 : sco + 1);
//
				//3333333
				ans = ch.getSurvey3();
				sco = answer.get(19 + ans);
				answer.put(19 + ans, sco == 0 ? 1 : sco + 1);

				//44444
				try {
					ans = ch.getSurvey4();
					sco = answer.get(22 + ans);
					answer.put(22 + ans, sco == 0 ? 1 : sco + 1);
				} catch (Exception e) {
				}

				//5555555
				try {
					ans = ch.getSurvey5();
					sco = answer.get(28 + ans);
					answer.put(28 + ans, sco == 0 ? 1 : sco + 1);
				} catch (Exception e) {
				}

				//66666
				ans = ch.getSurvey6();
				sco = answer.get(34 + ans);
				answer.put(34 + ans, sco == 0 ? 1 : sco + 1);

				//77777
				ans = ch.getSurvey7();
				sco = answer.get(39 + ans);
				answer.put(39 + ans, sco == 0 ? 1 : sco + 1);

				//8888888
				ans = ch.getSurvey8();
				sco = answer.get(46 + ans);
				answer.put(46 + ans, sco == 0 ? 1 : sco + 1);

				//99999
				ans = ch.getSurvey9();
				sco = answer.get(53 + ans);
				answer.put(53 + ans, sco == 0 ? 1 : sco + 1);

				//10
				ans = ch.getSurvey10();
				sco = answer.get(62 + ans);
				answer.put(62 + ans, sco == 0 ? 1 : sco + 1);

				//11
				ans = ch.getSurvey11();
				sco = answer.get(70 + ans);
				answer.put(70 + ans, sco == 0 ? 1 : sco + 1);

				//12
				ans = ch.getSurvey12();
				sco = answer.get(76 + ans);
				answer.put(76 + ans, sco == 0 ? 1 : sco + 1);

				//13
				ans = ch.getSurvey13();
				sco = answer.get(81 + ans);
				answer.put(81 + ans, sco == 0 ? 1 : sco + 1);

				//14
				ans = ch.getSurvey14();
				sco = answer.get(88 + ans);
				answer.put(88 + ans, sco == 0 ? 1 : sco + 1);

				//15
				ans = ch.getSurvey15();
				sco = answer.get(96 + ans);
				answer.put(96 + ans, sco == 0 ? 1 : sco + 1);

				//16
				ans = ch.getSurvey16();
				sco = answer.get(103 + ans);
				answer.put(103 + ans, sco == 0 ? 1 : sco + 1);

				//17
				ans = ch.getSurvey17();
				sco = answer.get(109 + ans);
				answer.put(109 + ans, sco == 0 ? 1 : sco + 1);

				//18
				ans = ch.getSurvey18();
				sco = answer.get(118 + ans);
				answer.put(118 + ans, sco == 0 ? 1 : sco + 1);

				//19
				ans = ch.getSurvey19();
				sco = answer.get(125 + ans);
				answer.put(125 + ans, sco == 0 ? 1 : sco + 1);

				//222222220
				ans = ch.getSurvey20();
				sco = answer.get(128 + ans);
				answer.put(128 + ans, sco == 0 ? 1 : sco + 1);
			}
			//

//			int i = 0;
//			for (HashMap<Integer, Integer> count : anssList) {
//
//				Set<Integer> keys = count.keySet();
//
////				log.info("#### keys-"+keys.size());
//				for (Integer num : keys) {
//					log.info("#### answer.put("+i+", "+num+")");
//					for (int k : kkk) {
//						if (k == i) i++;
//					}
//					int sco = count.get(num);
////					sco = sco == 0 ? 1 : sco + 1;
//					if(sco == 0) {
//						i++;
//						continue;
//					}
//					answer.put(i, sco + "");
//					log.info("#### answer.put("+i+", "+sco+")");
//					i++;
//				}
//			}
			ChannelResult result;
			int ind = 0;
			for (String name : questions) {
				result = new ChannelResult(name, answer.get(ind) == null ? "" : answer.get(ind) + "");
				log.info("####  new ChannelResult(" + name + ", " + answer.get(ind) + ")");
				listsssss.add(result);
				ind++;
			}

			if (listsssss.size() > 0
					)
				new ExportExcel("结果:" + key + "-" + DateUtils.getDate("yyyyMMddHHmmss"), ChannelResult.class).setDataList(listsssss).write(response, fileName).dispose();
		} catch (Exception e) {
			log.error("######################  导出失败-");
			e.printStackTrace();
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