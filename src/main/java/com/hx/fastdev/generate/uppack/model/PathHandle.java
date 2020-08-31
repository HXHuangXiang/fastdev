package com.hx.fastdev.generate.uppack.model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件路径地址
 * @author hx
 * @version 创建时间：2020年8月31日  下午10:31:07
 */
public class PathHandle {
	final static Logger log = LoggerFactory.getLogger(PathHandle.class);
	
	/** 文件目录规则*/
	private static String mavenProRule = "src/main/";
	
	/** 保存文件地址*/
	private String savePathStr;
		
	/** 项目目录*/
	private String proPath;
	
	/** class 文件所在目录*/
	private String proPathClass;
	
	/** class 文件保存目录*/
	private String savePathClass;
	/** 静态文件保存目录*/
	private String savePathStatic;
	
	/** 项目名称*/
	private String proName;
	
	/** 普通文件路径规则*/
	private final static String STATIC_START_RULE = "webapp"; 
	
	/**
	 * 带参数构造函数
	 * @param proPath 项目路径
	 * @param homePath 生成文件主目录
	 * @param savePath 生成文件保存目录
	 */
	public PathHandle(String proPath, String homePath, String savePath) {
		this.proPath = proPath + "\\";
		this.savePathStr = getPathSave(savePath);
		
		this.proPathClass = this.proPath + "target\\classes\\";
		
		this.savePathStatic = this.savePathStr + homePath + '\\';
		this.savePathClass = this.savePathStatic + "WEB-INF\\classes\\";
		
		this.proName = new File(this.proPath).getName();
	}
	
	/**
	 * 获取文件保存路径
	 * @author hx
	 * @return
	 */
	private String getPathSave(final String savePath) {
		// 配置文件路径 + 日期 + 时间. E:\\xjx_online\\20190115_\\
		StringBuilder pathSave = new StringBuilder(savePath.length() + 25);
		pathSave.append(savePath)
			.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		File file;
		while (true) {
			if (!(file = new File(pathSave.toString())).exists()) {
				break;
			}
			pathSave.append('_');
		}
		file.mkdirs();
		return pathSave.append('\\').toString();
	}
	/** 获取保存路径*/
	public String getSavePathStr() {
		return this.savePathStr;
	}
	/**
	 * 获取文件并复制
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月18日  下午9:48:34
	 * @param svnPath
	 * </pre>
	 */
	public void copys(String[] svnPaths) {
		String svnPath;
		for (int i = 0; i < svnPaths.length; i++) {
			svnPath = svnPaths[i];
			String svnPathAfter = null;
			if (svnPath.startsWith(mavenProRule)) {
				svnPathAfter = svnPath.substring(mavenProRule.length());
			}
			if (null == svnPathAfter) {
				int ruleIndex = -1;
				String rule = proName + '/' + mavenProRule;
				if ((ruleIndex = svnPath.indexOf(rule)) == -1) {
					log.info("生成上线文件. 不属于当前项目. 路径: {}", svnPath);
					continue;
				}
				svnPathAfter = svnPath.substring(ruleIndex + rule.length());
			}
			
			if (!isGenerate(svnPathAfter)) {
				log.info("生成上线文件. 该文件不需要生成. 路径: {}", svnPath);
				continue;
			}
			
			File proCopyFile = new File(this.proPath + mavenProRule + svnPathAfter);
			if (proCopyFile.isDirectory()) {
				log.info("生成上线文件. 目录文件不生成. 路径: {}. ", svnPath);
				continue;
			}
			
			String saveFilePath;
			String splicePath = svnPathAfter.substring(svnPathAfter.indexOf("/") + 1);
			if (svnPathAfter.startsWith(STATIC_START_RULE)) {
				saveFilePath = savePathStatic + splicePath;
			} else {
				splicePath = splicePath.replaceAll("\\.java$", ".class");
				proCopyFile = new File(proPathClass + splicePath);
				saveFilePath = savePathClass + svnPathAfter;
			}
			if (!proCopyFile.exists()) {
				log.info("生成上线文件. 文件不存在. 路径: {}. ", svnPath);
				continue;
			}
			File targetFile = new File(saveFilePath);
			targetFile.getParentFile().mkdirs();
			log.info("生成上线文件. 拷贝: {}. 保存: {}. 初始: {}", proCopyFile.getAbsolutePath(), targetFile.getAbsolutePath(), svnPath);
			try {
				Files.copy(proCopyFile.toPath(), targetFile.toPath());
				if (proCopyFile.getName().endsWith(".class")) {
					String startName = proCopyFile.getName().replaceAll("\\.class$", "") + "$";
					File[] childFiles = proCopyFile.getParentFile().listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.startsWith(startName);
						}
					});
					String targetParent = targetFile.getParent() + "\\";
					for (int j = 0; j < childFiles.length; j++) {
						proCopyFile = childFiles[j];
						targetFile = new File(targetParent + proCopyFile.getName());
						log.info("生成上线文件. 拷贝内部类: {}. 保存: {}. 初始: {}", proCopyFile.getAbsolutePath(), targetFile.getAbsolutePath(), svnPath);
						Files.copy(proCopyFile.toPath(), targetFile.toPath());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 是否需要生成
	 *     暂时 resources 下所有文件不生成
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月18日  下午9:21:12
	 * @param path
	 * @return
	 * </pre>
	 */
	private boolean isGenerate(String path) {
		if (path.startsWith("resources")) {
			return false;
		}
		return true;
	}
}
