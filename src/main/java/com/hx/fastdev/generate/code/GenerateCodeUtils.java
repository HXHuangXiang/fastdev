package com.hx.fastdev.generate.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.fastdev.generate.code.defaults.TableGenerateDefault;
import com.hx.fastdev.generate.code.model.Table;
import com.hx.utils.CliUtils;
import com.hx.utils.properties.PropertiesUtils;

/**
 * 生成代码工具类
 * @author hx
 * @version 创建时间：2018年10月3日  下午1:10:12
 */
public class GenerateCodeUtils {
	final static Logger log = LoggerFactory.getLogger(GenerateCodeUtils.class);
	
	private static TableGenerateI tableGenerate = new TableGenerateDefault();
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	public static void main(String[] args) {
		cliDataGenerateCode();
	}
	/**
	 * 根据剪切板创建表内容生成代码
	 *     vm 模板路径及保存位置请在配置文件配置
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月18日  下午10:10:52
	 * @param args
	 * @return
	 * </pre>
	 */
	public static String cliDataGenerateCode() {
		String data = CliUtils.getCliDataString(true);
		String savePath = generateCode(data);
		return savePath;
	}
	/**
	 * 根据创建表内容生成代码
	 *     vm 模板路径及保存位置请在配置文件配置
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月3日  下午1:10:19
	 * @param tableStr
	 * @return
	 * </pre>
	 */
	public static String generateCode(String tableStr) {
		String savePath = getSavePath();
		Table table = tableGenerate.newTable(tableStr);
		String vmTempPath = PropertiesUtils.getConfigString("generate.code.temp.path");
		final File vmDirFile = new File(vmTempPath);
		List<File> listFiles = new ArrayList<>();
		getVMFiles(listFiles, vmDirFile);
		if (listFiles.isEmpty()) {
			throw new RuntimeException("未找到生成模板. file: " + vmDirFile.getAbsolutePath());
		}
		String nowDateStr = DATE_FORMAT.format(new Date());
		String author = PropertiesUtils.getConfigString("generate.code.author");
		
		Properties pro = new Properties();
		pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
		VelocityContext context = new VelocityContext();
		context.put("table", table);
		context.put("author", author);
		context.put("date", nowDateStr);
		VelocityEngine ve = new VelocityEngine(pro);
		for (int i = 0, len = listFiles.size(); i < len; i++) {
			File vmFile = listFiles.get(i);
			File saveFile = newFile(vmFile, vmDirFile, savePath, table.getTableKeyword());
			
			
			Template t = ve.getTemplate(vmFile.getAbsolutePath(), "UTF-8");
			
			try (FileOutputStream outStream = new FileOutputStream(saveFile);
				 OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
				 BufferedWriter sw = new BufferedWriter(writer)
			) {
				t.merge(context, sw);
			} catch (Exception e) {
				throw new RuntimeException("写入文件失败. file: " + saveFile.getAbsolutePath(), e);
			}
			log.info("成功生成文件: {}", saveFile.getAbsoluteFile());
		}
		log.info("成功生成代码. filePath: {}", savePath);
		return savePath;
	}
	/**
	 * 创建生成文件并返回
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月3日  下午1:11:01
	 * @param vmFile
	 * @param vmDirFile
	 * @param savePath
	 * @param tableKeyword
	 * @return
	 * </pre>
	 */
	private static File newFile(File vmFile, File vmDirFile, String savePath, Map<String, String> tableKeyword) {
		final String vmFilePath = vmFile.getAbsolutePath();
		String generateFilePath = vmFilePath;
		for (Map.Entry<String, String> entry : tableKeyword.entrySet()) {
			generateFilePath = generateFilePath.replace(String.format("${%s}", entry.getKey()), entry.getValue());
		}
		generateFilePath = generateFilePath.replaceAll(".vm$", "").replace(vmDirFile.getAbsolutePath(), savePath);
		File file = new File(generateFilePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException("创建文件失败. file: " + file.getAbsolutePath(), e);
		}
		return file;
	}
	/**
	 * 获取 vm 文件列表
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月3日  下午1:11:12
	 * @param listFiles
	 * @param file
	 * </pre>
	 */
	private static void getVMFiles(List<File> listFiles, File file) {
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith(".vm");
			}
		});
		if (null == files || files.length == 0) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getVMFiles(listFiles, files[i]);
			} else {
				listFiles.add(files[i]);
			}
		}
	}
	/**
	 * 获取保存位置
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月3日  下午1:11:22
	 * @return
	 * </pre>
	 */
	private static String getSavePath() {
		// 配置文件路径 + 日期 + 时间. E:\\xjx_online\\20190115_\\
		String pathSaveStatic = PropertiesUtils.getConfigString("generate.code.save.path") + new SimpleDateFormat("yyyyMMdd").format(new Date());
		while (true) {
			if (!new File(pathSaveStatic).exists()) {
				break;
			}
			pathSaveStatic += "_";
		}
		return pathSaveStatic + "\\";
	}
}
