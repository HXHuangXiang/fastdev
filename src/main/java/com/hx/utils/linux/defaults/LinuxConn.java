package com.hx.utils.linux.defaults;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 连接 linux
 * @author hx
 * @version 创建时间：2020年8月24日  下午9:44:56
 */
public abstract class LinuxConn {
	final static Logger log = LoggerFactory.getLogger(LinuxConn.class);

	protected Session session = null;
	private int timeout = 60000;
	
	/**
	 * linux 连接, 初始化 session
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月24日  下午9:48:27
	 * @param host
	 * @param userName
	 * @param password
	 * @param port
	 * @return
	 * </pre>
	 */
	public LinuxConn conn(String host, String userName, String password, int port) {
		log.info("尝试连接 linux. url: {}:{}. username: {}. password: {}", host, port, userName, password);
		// 创建JSch对象
		JSch jsch = new JSch();
		try {
			// 根据用户名，主机ip，端口获取一个Session对象
			session = jsch.getSession(userName, host, port);
			// 设置密码
			session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			// 为Session对象设置properties
			session.setConfig(config);
			// 设置timeout时间
			session.setTimeout(timeout);
			// 通过Session建立链接
			session.connect();
			return this;
		} catch (JSchException e) {
			log.error("连接 linux 异常. 异常信息: ", e);
			throw new RuntimeException("连接 linux 异常", e);
		}
	}
	/**
	 * 获取 session
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月24日  下午9:49:19
	 * @return
	 * </pre>
	 */
	public Session getSession() {
		return session;
	}
}
