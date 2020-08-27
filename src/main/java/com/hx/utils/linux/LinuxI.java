package com.hx.utils.linux;

import java.util.List;

import com.hx.utils.linux.model.LinuxUploadFileModel;

/**
 * linux 上传文件等操作
 * @author hx
 * @version 创建时间：2020年8月26日  下午11:12:04
 */
public interface LinuxI {
	/**
	 * 上传文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:05:09
	 * @param linuxUploadFileModels
	 * </pre>
	 */
	void uploadFile(List<LinuxUploadFileModel> linuxUploadFileModels);
	/**
	 * 备份文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:18:17
	 * @param path
	 * @param backPath
	 * </pre>
	 */
	void backFile(String path, String... backPath);
}
