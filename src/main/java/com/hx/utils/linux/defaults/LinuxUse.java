package com.hx.utils.linux.defaults;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.utils.UtilsConstants;
import com.jcraft.jsch.ChannelExec;

public class LinuxUse extends LinuxConn {
	final static Logger log = LoggerFactory.getLogger(LinuxUse.class);
	protected LinuxUse() {}
	public static LinuxUse newLinuxConn(String host, String userName, String password, int port) {
		return (LinuxUse) new LinuxUse().conn(host, userName, password, port);
	}
	/**
	 * 执行命令
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月24日  下午10:04:20
	 * @param cmd
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	public String runCmd(String cmd) {
		return runCmd(cmd, UtilsConstants.UTF_8_CHARSET);
	}
	/**
	 * 执行命令
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月24日  下午10:04:31
	 * @param cmd
	 * @param charset
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	public String runCmd(String cmd, Charset charset) {
		try {
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(cmd);
			channelExec.setInputStream(null);
			channelExec.setErrStream(System.err);
			channelExec.connect();
			try (InputStream in = channelExec.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset))) {
				StringBuilder context = new StringBuilder();
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					context.append(buf).append("\r\n");
				}
				channelExec.disconnect();
				buf = context.toString().replaceAll("(\r\n|\n)$", "");
				log.info("执行命令: {}. 返回: {}", cmd, buf);
				return buf;
			}
		} catch (Exception e) {
			log.info("执行命令: {}. 异常信息: ", cmd, e);
			throw new RuntimeException("执行命令异常. cmd: " + cmd);
		}
	}
}
