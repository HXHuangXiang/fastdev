package com.hx.fastdev.generate.code.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hx.utils.StringUtils;


/**
 * 表
 * @author hx
 * @version 创建时间：2018年10月2日  下午2:40:10
 */
public class Table {
	/** 表名*/
	private String tableName;
	/** 表注释*/
	private String tableRemark;
	
	/** 表名别名/缩写. 如 table_detail td*/
	private String tableNameAlias;
	/** 表名转换为带点. 如 table_detail table.detail*/
	private String tableDot;
	/** 表名转换为带斜杠. 如 table_detail table/detail*/
	private String tableTilt;
	/** 表名驼峰首字母小写. 如 table_detail tableDetail*/
	private String tableHump;
	/** 表名驼峰首字母大写. 如 table_detail TableDetail*/
	private String tableHumpBig;
	/** 表字段*/
	private List<Col> cols;
	/** 主键列*/
	private Col primaryCol;
	/** 表关键字*/
	private Map<String, String> tableKeyword = new HashMap<>();
	
	/**
	 * 获取 表名
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * 设置 表名
	 * @param tableName 表名
	 */
	public void setTableName(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			new RuntimeException("表名不能为空");
		}
		this.tableName = tableName;
		String lowerCase = this.tableName.toLowerCase();
		tableDot = lowerCase.replaceAll("_", ".");
		tableTilt = lowerCase.replaceAll("_", "/");
		tableHump = StringUtils.lineToDot(lowerCase);
		char[] cs = tableHump.toCharArray();
		cs[0] -= 32;
		tableHumpBig = String.valueOf(cs);
		tableNameAlias = StringUtils.lineToFirstWord(lowerCase);
		this.tableName = tableName;
		
		tableKeyword.put("tableName", tableName);
		tableKeyword.put("tableNameAlias", tableNameAlias);
		tableKeyword.put("tableDot", tableDot);
		tableKeyword.put("tableTilt", tableTilt);
		tableKeyword.put("tableHump", tableHump);
		tableKeyword.put("tableHumpBig", tableHumpBig);
	}
	/**
	 * 获取 表注释
	 * @return tableRemark
	 */
	public String getTableRemark() {
		tableKeyword.put("tableRemark", tableRemark);
		return tableRemark;
	}
	/**
	 * 设置 表注释
	 * @param tableRemark 表注释
	 */
	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}
	/**
	 * 获取 表名别名缩写. 如 table_detail td
	 * @return tableNameAlias
	 */
	public String getTableNameAlias() {
		return tableNameAlias;
	}
	/**
	 * 设置 表名别名缩写. 如 table_detail td
	 * @param tableNameAlias 表名别名缩写. 如 table_detail td
	 */
	public void setTableNameAlias(String tableNameAlias) {
		this.tableNameAlias = tableNameAlias;
	}
	/**
	 * 获取 表名转换为带点. 如 table_detail table.detail
	 * @return tableDot
	 */
	public String getTableDot() {
		return tableDot;
	}
	/**
	 * 设置 表名转换为带点. 如 table_detail table.detail
	 * @param tableDot 表名转换为带点. 如 table_detail table.detail
	 */
	public void setTableDot(String tableDot) {
		this.tableDot = tableDot;
	}
	/**
	 * 获取 表名转换为带斜杠. 如 table_detail tabledetail
	 * @return tableTilt
	 */
	public String getTableTilt() {
		return tableTilt;
	}
	/**
	 * 设置 表名转换为带斜杠. 如 table_detail tabledetail
	 * @param tableTilt 表名转换为带斜杠. 如 table_detail tabledetail
	 */
	public void setTableTilt(String tableTilt) {
		this.tableTilt = tableTilt;
	}
	/**
	 * 获取 表名驼峰首字母小写. 如 table_detail tableDetail
	 * @return tableHump
	 */
	public String getTableHump() {
		return tableHump;
	}
	/**
	 * 设置 表名驼峰首字母小写. 如 table_detail tableDetail
	 * @param tableHump 表名驼峰首字母小写. 如 table_detail tableDetail
	 */
	public void setTableHump(String tableHump) {
		this.tableHump = tableHump;
	}
	/**
	 * 获取 表名驼峰首字母大写. 如 table_detail TableDetail
	 * @return tableHumpBig
	 */
	public String getTableHumpBig() {
		return tableHumpBig;
	}
	/**
	 * 设置 表名驼峰首字母大写. 如 table_detail TableDetail
	 * @param tableHumpBig 表名驼峰首字母大写. 如 table_detail TableDetail
	 */
	public void setTableHumpBig(String tableHumpBig) {
		this.tableHumpBig = tableHumpBig;
	}
	/**
	 * 获取 表字段
	 * @return cols
	 */
	public List<Col> getCols() {
		return cols;
	}
	/**
	 * 设置 表字段
	 * @param cols 表字段
	 */
	public void setCols(List<Col> cols, Col primaryCol) {
		this.cols = cols;
		this.primaryCol = primaryCol;
	}
	/**
	 * 获取 主键列
	 * @return
	 */
	public Col getPrimaryCol() {
		return primaryCol;
	}
	/**
	 * 获取 表关键字
	 * @return tableKeyword
	 */
	public Map<String, String> getTableKeyword() {
		return tableKeyword;
	}
}
