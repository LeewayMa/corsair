package com.sishuok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import weixin.TokenThread;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-22
 * <p>Version: 1.0
 * 配置 + 扫描 + 自动配置
 */
//@Configuration  
//@ComponentScan  
//@EnableAutoConfiguration  (exclude={DataSourceAutoConfiguration.class}) 
@SpringBootApplication
public class Application {
	public static TokenThread local = null;

	//	https://api.weixin.qq.com/cgi-bin/user/get?access_token=oqn-Es6X9XL8KZPbZme-RIwEvjLcSRTFkVpEZIptKWxaF0f3YKaNnGUHgjg6t6iIRtyhZTj4v6NJERRilQ8FFuy1mgtBGU8P-9AU75aexJp1D2sGvZuyoiE3Gs4Bj97AZRSdAGAOGH
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
		local = new TokenThread();
		local.run();
	}
}