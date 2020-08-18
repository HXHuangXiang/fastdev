package com.hx.fastdev.generate.uppack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.utils.CliUtils;

/**
 * 生成升级文件工具类
 * @author hx
 */
public class UppackUtils {
	final static Logger log = LoggerFactory.getLogger(UppackUtils.class);
	
	public static void main(String[] args) {
		cliDataGenerateFiles();
	}
	/**
	 * 根据剪切板 SVN 路径生成上下文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月18日  下午10:31:53
	 * @return
	 * </pre>
	 */
	public static String cliDataGenerateFiles() {
		String[] pathStrs = getPathStrs();
		CopyFileModel copyFileModel = CopyFileModel.getDefaultCopyFileModel();
		for (int i = 0; i < pathStrs.length; i++) {
			copyFileModel.copy(pathStrs[i]);
		}
		String savePathStr = copyFileModel.getSavePathStr();
		log.info("生成文件完成. 路径: {}", savePathStr);
		return savePathStr;
	}
	/**
	 * 获取剪切板路径列表
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月18日  下午10:31:57
	 * @return
	 * </pre>
	 */
	private static String[] getPathStrs() {
		String data = CliUtils.getCliDataString();
		return data.split("(\r\n|\n)");
	}
}
