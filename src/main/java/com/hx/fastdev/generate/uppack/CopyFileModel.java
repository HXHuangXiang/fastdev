package com.hx.fastdev.generate.uppack;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.utils.properties.PropertiesUtils;

/**
 * 升级文件信息处理
 * @author hx
 * @version 创建时间：2019年5月28日  上午11:59:56
 */
public class CopyFileModel {
	final static Logger log = LoggerFactory.getLogger(CopyFileModel.class);
	
	/** 文件目录规则*/
	private static String mavenProRule = "src/main/";
	
	/** 保存文件地址*/
	private String savePathStr;
		
	/** 项目目录*/
	private String[] proPaths;
	
	/** class 文件所在目录*/
	private String[] proPathClasss;
	/** 静态文件所在目录*/
	private String[] proPathStatics;
	
	/** class 文件保存目录*/
	private String[] savePathClasss;
	/** 静态文件保存目录*/
	private String[] savePathStatics;
	
	/** 项目规则*/
	private String[] proPathRules;
	
	private final static String WEBAPP = "webapp"; 
	
	private CopyFileModel() {
		this.proPaths = PropertiesUtils.getConfigClass("generate.uppack.pro.paths", String[].class);
		this.savePathStr = getPathSave();
		
		this.proPathStatics = new String[this.proPaths.length];
		this.proPathClasss = new String[this.proPaths.length];
		this.savePathClasss = new String[this.proPaths.length];
		this.savePathStatics = new String[this.proPaths.length];
		this.proPathRules = new String[this.proPaths.length];
		for (int i = 0, len = this.proPaths.length; i < len; i++) {
			this.proPaths[i] += "\\"; 
			this.proPathStatics[i] = this.proPaths[i] + mavenProRule + "webapp\\";
			this.proPathClasss[i] = this.proPaths[i] + "target\\classes\\";
			
			String proName = "";
			if (len != 1) {
				proName = "/" + new File(this.proPaths[i]).getName() + "/";
			}
			this.proPathRules[i] = proName + mavenProRule;
			this.savePathStatics[i] = this.savePathStr + proName + PropertiesUtils.getConfigString("generate.uppack.home.path") + '\\';
			this.savePathClasss[i] = this.savePathStatics[i] + "WEB-INF\\classes\\";
		}
	}
	private static class CopyFileModelPrivate {
		private static CopyFileModel copyFileModel = new CopyFileModel();
	}
	public static CopyFileModel getDefaultCopyFileModel() {
		return CopyFileModelPrivate.copyFileModel;
	}
	/**
	 * 获取文件保存路径
	 * @author hx
	 * @return
	 */
	private String getPathSave() {
		// 配置文件路径 + 日期 + 时间. E:\\xjx_online\\20190115_\\
		String homePath = PropertiesUtils.getConfigString("generate.uppack.save.path");
		StringBuilder pathSave = new StringBuilder(homePath.length() + 25);
		pathSave.append(homePath)
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
	/**
	 * 获取保存路径
	 * <pre>
	 * @author hx
	 * @version 创建时间：2019年5月28日  下午5:38:15
	 * @return
	 * </pre>
	 */
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
	public void copy(final String svnPath) {
		int ruleNum = -1;
		int ruleIndex = -1; 
		for (int i = 0; i < proPathClasss.length; i++) {
			ruleIndex = svnPath.indexOf(proPathRules[i]);
			if (-1 != ruleIndex) {
				ruleNum = i;
				break;
			}
		}
		if (-1 == ruleNum) {
			log.info("生成上线文件. 未找到文件规则. 路径: {}", svnPath);
			return;
		}
		String proPath = proPaths[ruleNum];
		String proPathRule = proPathRules[ruleNum];
		String svnPathAfter = svnPath.substring(ruleIndex + proPathRule.length());
		if (!isGenerate(svnPathAfter)) {
			log.info("生成上线文件. 该文件不需要生成. 路径: {}. ", svnPath);
			return;
		}
		String proFileInitPath = proPath + mavenProRule + svnPathAfter;
		if (new File(proFileInitPath).isDirectory()) {
			log.info("生成上线文件. 目录文件不生成. 路径: {}. ", svnPath);
			return;
		}
		
		String proFilePath;
		String saveFilePath;
		if (svnPathAfter.startsWith(WEBAPP)) {
			proFilePath = proFileInitPath;
			saveFilePath = savePathStatics[ruleNum] + svnPathAfter.substring(svnPathAfter.indexOf("/") + 1);
		} else {
			svnPathAfter = svnPathAfter.substring(svnPathAfter.indexOf("/") + 1).replaceAll("\\.java$", ".class");
			proFilePath = proPathClasss[ruleNum] + svnPathAfter;
			saveFilePath = savePathClasss[ruleNum] + svnPathAfter;
		}
		File sourceFile = new File(proFilePath);
		if (!sourceFile.exists()) {
			log.info("生成上线文件. 文件不存在. 路径: {}. ", svnPath);
			return;
		}
		File targetFile = new File(saveFilePath);
		targetFile.getParentFile().mkdirs();
		log.info("生成上线文件. 拷贝: {}. 保存: {}. 初始: {}", sourceFile.getAbsolutePath(), targetFile.getAbsolutePath(), svnPath);
		try {
			Files.copy(sourceFile.toPath(), targetFile.toPath());
			if (sourceFile.getName().endsWith(".class")) {
				String startName = sourceFile.getName().replaceAll("\\.class$", "") + "$";
				File[] childFiles = sourceFile.getParentFile().listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.startsWith(startName);
					}
				});
				String targetParent = targetFile.getParent() + "\\";
				for (int i = 0; i < childFiles.length; i++) {
					sourceFile = childFiles[i];
					targetFile = new File(targetParent + sourceFile.getName());
					log.info("生成上线文件. 拷贝内部类: {}. 保存: {}. 初始: {}", sourceFile.getAbsolutePath(), targetFile.getAbsolutePath(), svnPath);
					Files.copy(sourceFile.toPath(), targetFile.toPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
