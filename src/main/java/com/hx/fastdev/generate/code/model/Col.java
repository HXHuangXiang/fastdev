package com.hx.fastdev.generate.code.model;

import com.hx.utils.StringUtils;

/**
 * 字段
 * @author hx
 * @version 创建时间：2018年10月2日  下午2:33:10
 */
public class Col {
	/** 字段名称*/
	private String colName;
	/** 字段数据库类型*/
	private String colType;
	/** 字段注释*/
	private String colRemark;
	
	/** 字段驼峰*/
	private String colNameHump;
	/** 字段驼峰首字母大写*/
	private String colNameHumpBig;
	/** 字段类型转 java类型*/
	private String colTypeJava;
	
	/**
	 * 获取 字段名称
	 * @return colName
	 */
	public String getColName() {
		return colName;
	}
	/**
	 * 设置 字段名称
	 * @param colName 字段名称
	 */
	public void setColName(String colName) {
		this.colName = colName;
		
		colNameHump = StringUtils.lineToDot(this.colName);
		char[] cs = colNameHump.toCharArray();
		cs[0] -= 32;
		colNameHumpBig = String.valueOf(cs);
	}
	/**
	 * 获取 字段数据库类型
	 * @return colType
	 */
	public String getColType() {
		return colType;
	}
	/**
	 * 设置 字段数据库类型
	 * @param colType 字段数据库类型
	 */
	public void setColType(String colType) {
		this.colType = colType;
		
		if ("int".equals(colType)) {
			this.colTypeJava = "Integer";
			return;
		}
		if ("varchar".equals(colType) || "char".equals(colType)) {
			this.colTypeJava = "String";
			return;
		}
		if ("timestamp".equals(colType) || "date".equals(colType) || "datetime".equals(colType)) {
			this.colTypeJava = "java.util.Date";
			return;
		}
		if ("decimal".equals(colType)) {
			this.colTypeJava = "java.math.BigDecimal";
			return;
		}
	}
	/**
	 * 获取 字段注释
	 * @return colRemark
	 */
	public String getColRemark() {
		return colRemark;
	}
	/**
	 * 设置 字段注释
	 * @param colRemark 字段注释
	 */
	public void setColRemark(String colRemark) {
		this.colRemark = colRemark;
	}
	/**
	 * 获取 字段驼峰
	 * @return colNameHump
	 */
	public String getColNameHump() {
		return colNameHump;
	}
	/**
	 * 设置 字段驼峰
	 * @param colNameHump 字段驼峰
	 */
	public void setColNameHump(String colNameHump) {
		this.colNameHump = colNameHump;
	}
	/**
	 * 获取 字段驼峰首字母大写
	 * @return colNameHumpBig
	 */
	public String getColNameHumpBig() {
		return colNameHumpBig;
	}
	/**
	 * 设置 字段驼峰首字母大写
	 * @param colNameHumpBig 字段驼峰首字母大写
	 */
	public void setColNameHumpBig(String colNameHumpBig) {
		this.colNameHumpBig = colNameHumpBig;
	}
	/**
	 * 获取 字段类型转 java类型
	 * @return colTypeJava
	 */
	public String getColTypeJava() {
		return colTypeJava;
	}
	/**
	 * 设置 字段类型转 java类型
	 * @param colTypeJava 字段类型转 java类型
	 */
	public void setColTypeJava(String colTypeJava) {
		this.colTypeJava = colTypeJava;
	}
}
