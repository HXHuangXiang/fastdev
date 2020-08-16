package com.hx.utils.properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取配置工具类
 * @author hx
 * @version 创建时间：2020年7月27日  下午10:53:20
 */
public abstract class PropertiesUtils {
	static PropertiesManager manager = PropertiesManager.manager;
	
	/** 默认配置文件*/
	private final static String config = "properties/config.properties";
	@SuppressWarnings("unchecked")
	private static<T> T getVal(String propFileName, String key, Class<T> t) {
		if (null == t) {
			return null;
		}
		String str = manager.getProperties(propFileName).getProperty(key);
		if (null != str) {
			str = str.trim();
		}
		if (String.class.getName().equals(t.getName())) {
			return (T) str;
		}
		if (int.class.getName().equals(t.getName()) || Integer.class.getName().equals(t.getName())) {
			if (int.class.getName().equals(t.getName()) && null == str) {
				return (T) Integer.valueOf(-1);
			}
			return (T) Integer.valueOf(str);
		}
		if (long.class.getName().equals(t.getName()) || Long.class.getName().equals(t.getName())) {
			if (long.class.getName().equals(t.getName()) && null == str) {
				return (T) Long.valueOf(-1);
			}
			return (T) Long.valueOf(str);
		}
		if (boolean.class.getName().equals(t.getName()) || Boolean.class.getName().equals(t.getName())) {
			return (T) ((Boolean) ("true".equals(str) || "1".equals(str)));
		}
		if (String[].class.getName().equals(t.getName())) {
			if (null == str) {
				return (T) new String[0];
			}
			return (T) str.split("(, |,)");
		}
		if (JSONObject.class.getName().equals(t.getName())) {
			return (T) JSON.parseObject(str);
		}
		if (JSONArray.class.getName().equals(t.getName())) {
			return (T) JSON.parseArray(str);
		}
		return null;
	}
	/**
	 * 根据类型获取默认配置文件值
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年7月27日  下午10:51:45
	 * @param <T>
	 * @param key
	 * @param t
	 *     int.class/Integer.class
	 *     long.class/Long.class
	 *     boolean.class/Boolean.class
	 *     String.class
	 *     String[].class
	 *     JSONObject.class(com.alibaba.fastjson.JSONObject)
	 *     JSONArray.class(com.alibaba.fastjson.JSONArray)
	 * @return
	 * </pre>
	 */
	public static<T> T getConfigClass(String key, Class<T> t) {
		return getVal(config, key, t);
	}
	/**
	 * 获取默认配置文件 String 值
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年7月27日  下午10:52:56
	 * @param key
	 * @return
	 * </pre>
	 */
	public static String getConfigString(String key) {
		return getConfigClass(key, String.class);
	}
}
