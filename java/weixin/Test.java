package weixin;

import net.sf.json.JSONArray;

import com.sishuok.entity.WeixinUserInfo;
import com.sishuok.entity.WeixinUserList;

public class Test {
//	@Autowired
//	static UserInfoServiceImpl dao;
	public static void main(String[] args) {
//		// TokenThread.accessToken.setToken("GKUCZA2d8HO38me61UV59MrD5CTM8qzmrGWqguKcYDeAh14bzKvqIN4UBHM5X7Yn7yAmBHNyF9QWsjs_v1i2ZXfuAeMypdUqIHeZ6TyYYnmk0gzJ3C5UJkBKBzSbLglWODCfAGAYNM");
		WeixinUserList list = WeixinUtil.getUserInfoList();
		System.out.println(list.getCount());
		System.out.println(list.getTotal());
		System.out.println(list.getNext_openid());
//		JSONObject obj = list.getData();
		JSONArray openid = (JSONArray) list.getData().get("openid");
		System.out.println("openid=" + openid.get(1));
		WeixinUserInfo user = WeixinUtil.getUserInfo((String)openid.get(1));
		// System.out.println("OpenID：" + user.getOpenId());
		System.out.println("关注状态：" + user.getSubscribe());
		System.out.println("关注时间：" + user.getSubscribeTime());
		System.out.println("昵称：" + user.getNickName());
		System.out.println("性别：" + user.getSex());
		System.out.println("国家：" + user.getCountry());
		System.out.println("省份：" + user.getProvince());
		System.out.println("城市：" + user.getCity());
		System.out.println("语言：" + user.getLanguage());
		System.out.println("头像：" + user.getHeadImgUrl());
//		dao.save(user);

	}

}
