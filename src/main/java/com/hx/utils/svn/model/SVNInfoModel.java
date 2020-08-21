package com.hx.utils.svn.model;

import java.util.Date;
import java.util.List;

/**
 * 文件列表版本信息
 * @author hx
 * @version 创建时间：2017年8月28日  下午5:14:23
 */
public class SVNInfoModel {
	/** 提交的svn版本号*/
	private long version;
	/** 作者*/
	private String author;
	/** 提交日期*/
	private Date date;
	/** 提交信息*/
	private String message;
	/** 提交路径*/
	private List<SVNPathModel> pathModels;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SVNInfoModel [version=" + version + ", author=" + author + ", date=" + date + ", message=" + message + ", pathModels=" + pathModels + "]";
	}
	/**
	 * 获取 提交的svn版本号
	 * @return version
	 */
	public long getVersion() {
		return version;
	}
	/**
	 * 设置 提交的svn版本号
	 * @param version 提交的svn版本号
	 */
	public void setVersion(long version) {
		this.version = version;
	}
	/**
	 * 获取 作者
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置 作者
	 * @param author 作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取 提交日期
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * 设置 提交日期
	 * @param date 提交日期
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 获取 提交信息
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置 提交信息
	 * @param message 提交信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取 提交路径
	 * @return pathModels
	 */
	public List<SVNPathModel> getPathModels() {
		return pathModels;
	}
	/**
	 * 设置 提交路径
	 * @param pathModels 提交路径
	 */
	public void setPathModels(List<SVNPathModel> pathModels) {
		this.pathModels = pathModels;
	}
}
