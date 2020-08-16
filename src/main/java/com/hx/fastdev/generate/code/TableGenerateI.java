package com.hx.fastdev.generate.code;

import com.hx.fastdev.generate.code.model.Table;

public interface TableGenerateI {
	/**
	 * 获取表信息
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年7月28日  下午4:00:13
	 * @param tableStr
	 * @return
	 * </pre>
	 */
	Table newTable(String tableStr);
}
