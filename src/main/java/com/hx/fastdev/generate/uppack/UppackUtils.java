package com.hx.fastdev.generate.uppack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.fastdev.generate.uppack.model.PathHandle;
import com.hx.utils.CliUtils;
import com.hx.utils.properties.PropertiesUtils;

/**
 * 生成升级文件工具类
 * @author hx
 */
public class UppackUtils {
	final static Logger log = LoggerFactory.getLogger(UppackUtils.class);
	
	public static void main(String[] args) {
		cliDataGenerateFiles(PropertiesUtils.getConfigString("generate.uppack.pro.paths"));
	}
	/**
	 * 剪切板路径生成升级文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月31日  下午10:35:24
	 * @param proPath 项目路径
	 * @param homePath 生成文件主目录
	 * @param savePath 生成文件保存目录
	 * @return
	 * </pre>
	 */
	public static String cliDataGenerateFiles(String proPath, String homePath, String savePath) {
		String[] pathStrs = getPathStrs();
		PathHandle pathHandle = new PathHandle(proPath, homePath, savePath);
		pathHandle.copys(pathStrs);
		String savePathStr = pathHandle.getSavePathStr();
		log.info("生成文件完成. 路径: {}", savePathStr);
		return savePathStr;
	}
	/**
	 * 剪切板路径生成升级文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月31日  下午10:35:54
	 * @param proPath 项目路径
	 * @return
	 * </pre>
	 */
	public static String cliDataGenerateFiles(String proPath) {
		return cliDataGenerateFiles(proPath,
				PropertiesUtils.getConfigString("generate.uppack.home.path"),
				PropertiesUtils.getConfigString("generate.uppack.save.path") );
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
