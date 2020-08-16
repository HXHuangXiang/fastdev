package com.hx.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件管理类
 * @author hx
 * @version 创建时间：2020年7月27日  下午10:24:49
 */
public class PropertiesManager {
	final static Logger log = LoggerFactory.getLogger(PropertiesManager.class);
	/** 获取 配置文件管理类*/
	protected static PropertiesManager manager = new PropertiesManager();
	private Map<String, MyProperties> map = new ConcurrentHashMap<>();
	/** 项目地址*/
	private final static String PRO_PATH = Class.class.getResource("/").getPath();
	private Lock lock = new ReentrantLock();
	private PropertiesManager() {}
	/**
	 * 根据文件路径获取配置
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年7月27日  下午10:24:31
	 * @param filePath
	 * @return
	 * </pre>
	 */
	protected Properties getProperties(String filePath) {
		MyProperties myProperties = checkProperties(filePath);
		return myProperties.properties;
	}
	/**
	 * 检查获取配置文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年7月27日  下午10:23:59
	 * @param filePath
	 * @return
	 * </pre>
	 */
	private MyProperties checkProperties(String filePath) {
		MyProperties myProperties = map.get(filePath);
		File file = new File(PRO_PATH + filePath);
		if (myProperties == null) {
			myProperties = new MyProperties();
		}
		if (file.lastModified() != myProperties.time) {
			try {
				lock.lock();
				if (file.lastModified() != myProperties.time) {
					myProperties.time = file.lastModified();
					myProperties.properties = loadProperties(file);
				}
			} finally {
				lock.unlock();
			}
		}
		return myProperties;
	}
	/**
	 * 加载配置文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年7月27日  下午10:23:44
	 * @param filePath
	 * @return
	 * </pre>
	 */
	private synchronized Properties loadProperties(File filePath) {
		Properties properties = new Properties();
		try (InputStream is = new FileInputStream(filePath)) {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	public static void main(String[] args) {
		System.getProperties().forEach((key, val) -> {
			System.out.println(key + "-" + val);
		});
	}
	private class MyProperties {
		long time;
		Properties properties;
	}
}
