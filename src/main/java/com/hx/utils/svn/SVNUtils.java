package com.hx.utils.svn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.utils.properties.PropertiesUtils;
import com.hx.utils.svn.model.SVNPathModel;

/**
 * SVN 日志获取工具类
 * @author hx
 * @version 创建时间：2020年8月21日  下午10:28:20
 */
public class SVNUtils {
	final static Logger log = LoggerFactory.getLogger(SVNUtils.class);
	private static class SVNUtilsPrivate {
		static SVNRead read = new SVNRead(PropertiesUtils.getConfigString("svn.url"), PropertiesUtils.getConfigString("svn.username"), PropertiesUtils.getConfigString("svn.password"));
	}
	
	/**
	 * 获取最新版本文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月21日  下午9:39:11
	 * @return
	 * </pre>
	 */
	public static List<SVNPathModel> getLastSVNPaths() {
		return SVNUtilsPrivate.read.getSVNPaths(-1);
	}
	public static void main(String[] args) {
		System.out.println(getLastSVNPaths());
	}
}