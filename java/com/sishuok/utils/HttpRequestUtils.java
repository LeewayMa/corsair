package com.sishuok.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import weixin.TokenThread;


//import cn.com.people.data.common.config.Global;

public class HttpRequestUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码

	@Autowired
	private TaskExecutor taskExecutor;
	private static final String appid="";
	private static final String appsecret="";
	private static final String access_token="";
	private static final int expires_in=0;
	
	public  void getToken(){
		taskExecutor.execute(new TokenThread()
		);
	}
//	private get 
	/**
	 * post请求（包括附件上传）
	 * 
	 * @param url :full path include parameters
	 * @param fileKey : param key name of file to be uploaded
	 * @param filePath : relative file path 
	 * @return true : success
	 */
	public static boolean sendPostWithFile(String url, String fileKey, String filePath) {
		logger.debug("#### url:" + url+" fileKey :" + fileKey+"  filePath :" + filePath);
		OutputStream outputSteam = null;
		PrintWriter out = null;
		BufferedReader in = null;
		InputStream is = null;
		DataOutputStream dos = null;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		try {
			URL RequestURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) RequestURL.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			
			outputSteam = conn.getOutputStream();
			dos = new DataOutputStream(outputSteam);
			StringBuffer sb = new StringBuffer();
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINE_END);

			File file =null;
			/**
			 * 当文件不为空，把文件包装并且上传
			 */
			if (StringUtils.isNotBlank(fileKey) ) {
//				 file = new File(FileUtils.getAbsolutePath(filePath));
//				 file = new File(Global.getCkExtFolder()+Global.getExtFilePrefix()+filePath.substring(filePath.indexOf("/", 1), filePath.length()));
//				 file = new File(Global.getCkExtFolder()+filePath.substring(filePath.indexOf("/", 1), filePath.length()));
				/**
				 * 这里重点注意： name里面的值为服务器端需要key(php里面需要是file),只有这个key才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的 比如:abc.png,可以看到下面在开始与结束都写入了一些分隔符等标示.
				 */
				sb.append("Content-Disposition: form-data; name=\"" + fileKey + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
			}
			sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
			sb.append(LINE_END);
			dos.write(sb.toString().getBytes());
			if ( file != null) {
				is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
			}
			dos.write(LINE_END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
			dos.write(end_data);
			dos.flush();
			/**
			 * 获取响应码 200=成功 当响应成功，获取响应的流
			 */
			int res = conn.getResponseCode();
			
			logger.debug("#### getResponseCode :" + res);
			if (res == 200) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
				String line;
				StringBuilder result = new StringBuilder("");
				while ((line = in.readLine()) != null) {
					result = result.append(line);
				}
				logger.debug("#### result :" + result.toString());
				return true;
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (outputSteam != null) {
					outputSteam.close();
				}
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (dos != null) {
					dos.close();
				}
				if (is != null) {
					is.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		return sendGet(url, param, null);
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		return sendPost(url, param, null);
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charsetName
	 *            编码
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param, String charsetName) {
		String result = "";
		BufferedReader in = null;
		String urlNameString = url;
		try {
			if (param != null && param.trim().length() > 0) {
				urlNameString += "?" + param;
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setConnectTimeout(90000); // 设置超时时间
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			
			logger.debug("http请求" + urlNameString);
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			if (null != charsetName) {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetName));
			}
			else {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}
		catch (Exception e) {
			logger.warn("发送GET请求出现异常！url:" + urlNameString + e);
			result = "error";
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
			}
			catch (Exception e2) {
				e2.printStackTrace();
				result = "error";
			}
		}
		return result;
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charsetName
	 *            编码
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String charsetName) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setConnectTimeout(90000); // 设置超时时间
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			if (null != charsetName) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetName));
			}
			else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}
		catch (Exception e) {
			logger.warn("发送POST请求出现异常！" + e);
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}