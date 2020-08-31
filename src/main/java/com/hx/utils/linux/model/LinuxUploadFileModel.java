package com.hx.utils.linux.model;

import java.io.File;

import com.hx.utils.linux.FileLinux;

/**
 * 上传文件 model
 * @author hx
 * @version 创建时间：2020年8月26日  下午11:05:35
 */
public class LinuxUploadFileModel {
	/** 本地文件*/
	private File localFile;
	/** 服务器文件*/
	private FileLinux serverFile;
	/**
	 * 获取 本地文件
	 * @return localFile
	 */
	public File getLocalFile() {
		return localFile;
	}
	/**
	 * 获取 服务器文件
	 * @return serverFile
	 */
	public FileLinux getServerFile() {
		return serverFile;
	}
	public static LinuxUploadFileModel newLinuxUploadFileModel(String localPath, String serverPath) {
		File localFile = new File(localPath);
		if (!localFile.isFile()) {
			throw new RuntimeException("本地路径不是文件请检查. localPath: " + localPath);
		}
		LinuxUploadFileModel model = new LinuxUploadFileModel();
		model.localFile = localFile;
		model.serverFile = new FileLinux(serverPath);
		return model;
	}
	@Override
	public String toString() {
		return "{\"localFile\":\"" + localFile + "\", \"serverFile\":\"" + serverFile + "\"}";
	}
}
