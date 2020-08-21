package com.hx.utils.svn.model.enums;

import com.hx.utils.StringUtils;

/**
 * 文件状态
 * @author hx
 * @version 创建时间：2017年8月28日  下午5:16:25
 */
public enum FileStatusEnums {

	/** 修改*/
	M("修改"),
	/** 增加*/
	A("增加"),
	/** 删除*/
	D("删除"),
	/** 替换/更换-对象首先被删除，然后是另一个同名的对象添加，所有在一个单一的修订*/
	R("替换")
	;
	
	/**中文名称*/
	public String chName;
	private FileStatusEnums(String chName) {
		this.chName = chName;
	}
	/**
	 * 转枚举
	 * @author hx
	 * @param str
	 * @return
	 */
	public final static FileStatusEnums toEnum(String str){
		if(StringUtils.isBlank(str)) return null;
		for (FileStatusEnums enumStr : values()) {
			if(enumStr.name().equals(str)) return enumStr;
		}
		return null;
	}
}
