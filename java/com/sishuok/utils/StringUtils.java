package com.sishuok.utils;



import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private static final String DEFAULT_SEPERATOR = ",";
	private static final String CHARSET_NAME = "UTF-8";

	public static String lowerFirst(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0, 1).toLowerCase() + str.substring(1);
		}
	}

	public static String upperFirst(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(final String str, int length) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 缩略字符串（替换html）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
	}
		
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}
	
	/**
	 * 转换为Long类型
	 */
	public static Long[] toLong(Object[] val){
		Long[] result = new Long[val.length];
		
		for(int i = 0; i<val.length; i++){
			result[i] = toDouble(val[i]).longValue();
		}
		
		return result;
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获得i18n字符串
	 */
//	public static String getMessage(String code, Object[] args) {
//		LocaleResolver localLocaleResolver = SpringContextHolder
//				.getBean(LocaleResolver.class);
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes()).getRequest();
//		Locale localLocale = localLocaleResolver.resolveLocale(request);
//		return SpringContextHolder.getApplicationContext().getMessage(code,
//				args, localLocale);
//	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
	
	/**
	 * 获取经Punycode编码前的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnicode(String str) {
		return str == null ? null : IDN.toUnicode(str);
	}

    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }

	/**
	 * 判断去掉首尾空格后的字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isEmptyAfterTrim(String str) {
		return isEmpty(str) || str.trim().length() == 0;
	}
	
	/**
	 * 将字符串以separator分割成数组返回
	 * 
	 * @param str
	 *            待操作的字符串
	 * @param separator
	 *            分割符
	 * @return 分割后的数组
	 * 
	 */
	public static String[] split2Array(String str, String separator) {
		if (StringUtils.isEmptyAfterTrim(str)) {
			return new String[0];
		}
		if (isEmpty(separator)) {
			return StringUtils.split(str, DEFAULT_SEPERATOR);
		} else {
			return StringUtils.split(str, separator);
		}
	}
	
}
