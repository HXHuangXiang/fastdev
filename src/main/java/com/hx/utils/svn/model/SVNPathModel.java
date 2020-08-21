package com.hx.utils.svn.model;

import com.hx.utils.svn.model.enums.FileStatusEnums;

/**
 * 修改文件路径
 * @author hx
 * @version 创建时间：2017年8月28日  下午5:13:16
 */
public class SVNPathModel {
	/** 文件状态*/
	private FileStatusEnums type;
	/** 文件路径*/
	private String path;
	/**
	 * 获取 文件状态
	 * @return type
	 */
	public FileStatusEnums getType() {
		return type;
	}
	/**
	 * 设置 文件状态
	 * @param type 文件状态
	 */
	public void setType(FileStatusEnums type) {
		this.type = type;
	}
	/**
	 * 获取 文件路径
	 * @return path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置 文件路径
	 * @param path 文件路径
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月21日  下午10:30:53
	 * @return
	 * </pre>
	 */
	@Override
	public String toString() {
		return "{\"type\":\"" + type + "\", \"path\":\"" + path + "\"}";
	}
}
