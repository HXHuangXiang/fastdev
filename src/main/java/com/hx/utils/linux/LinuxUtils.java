package com.hx.utils.linux;

import com.hx.utils.linux.defaults.LinuxFile;
import com.hx.utils.properties.PropertiesUtils;

/**
 * linux 操作工具类
 * @author hx
 * @version 创建时间：2020年8月26日  下午11:12:33
 */
public class LinuxUtils {
	private static class LinuxUtilsTemp {
		static LinuxFile linuxFile = LinuxFile.newLinuxConn(PropertiesUtils.getConfigString("linux.host"),
				PropertiesUtils.getConfigString("linux.userName"), 
				PropertiesUtils.getConfigString("linux.password"), 
				PropertiesUtils.getConfigClass("linux.port", int.class));
	}
	public static LinuxI getLinux() {
		return LinuxUtilsTemp.linuxFile;
	}
}