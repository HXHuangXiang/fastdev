package com.hx.utils;

/**
 * 字符串工具类
 * @author hx
 * @version 创建时间：2018年10月2日  下午2:41:03
 */
public abstract class StringUtils extends org.apache.commons.lang.StringUtils {
	/**
	 * 下划线后字母转为大写
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月2日  下午2:42:13
	 * @param name
	 * @return
	 * </pre>
	 */
	public static String lineToDot(String name) {
		if (name == null || name.isEmpty()) {
			// 不用转换
			return "";
		}
		name = name.toLowerCase();
		if (!name.contains("_")) {
			return name;
		}
		StringBuilder sb = new StringBuilder();
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (sb.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				sb.append(camel);
			} else {
				// 其他的驼峰片段，首字母大写
				sb.append(camel.substring(0, 1).toUpperCase());
				sb.append(camel.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}
	/**
	 * 首字母 + 下划线后 1 个字母
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月2日  下午2:43:14
	 * @param name
	 * @return
	 * </pre>
	 */
	public static String lineToFirstWord(String name) {
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		}
		name = name.toLowerCase();
		if (!name.contains("_")) {
			return name.substring(0, 1);
		}
		StringBuilder sb = new StringBuilder();
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty()) {
				continue;
			}
			sb.append(camel.substring(0, 1));
		}
		return sb.toString();
	}
}
