package weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sishuok.entity.AccessToken;

/**
 * 定时获取微信access_token的线程
 *
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// 第三方用户唯一凭证
	public static String appid = "wx4be2b8a46778682b";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "c4f9cf255b6b4e8b71da13801298f023";
	public static String next_openid = "";
	public static AccessToken accessToken = null;

	public void run() {
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
				if (null != accessToken) {
//					WeixinUtil.accessToken = accessToken.getToken();
					log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}

//	public static void main(String[] args) {
//		new TokenThread().run();
//	}
}
