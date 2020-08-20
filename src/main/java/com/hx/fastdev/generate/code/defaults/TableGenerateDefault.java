package com.hx.fastdev.generate.code.defaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.fastdev.generate.code.TableGenerateI;
import com.hx.fastdev.generate.code.model.Col;
import com.hx.fastdev.generate.code.model.Table;
import com.hx.utils.properties.PropertiesUtils;

/**
 * 生成表信息
 * @author hx
 * @version 创建时间：2018年10月2日  下午3:20:12
 */
public class TableGenerateDefault implements TableGenerateI {
	final static Logger log = LoggerFactory.getLogger(TableGenerateDefault.class);
	/** 表*/
	final static Pattern REG_TABLE = Pattern.compile("CREATE +TABLE +`(?<tableName>.*?)`.*, +(?:PRIMARY KEY \\(`(?<primaryKeyCol>.*?)`\\)).* COMMENT *= *'(?<tableRemark>.*?)'");
	/** 字段*/
	final static Pattern REG_COL = Pattern.compile("`(?<colName>.*?)` +(?<colType>.*?)(?:\\(| ).*COMMENT +'(?<colRemark>.*)'");
	
	/**
	 * 获取表信息
	 * <pre>
	 * @author hx
	 * @version 创建时间：2018年10月2日  下午3:22:32
	 * @param tableStr
	 * @return
	 * </pre>
	 */
	@Override
	public Table newTable(String tableStr) {
		Table table = new Table();
		
		String tableStrTemp = tableStr.replaceAll("(\r\n|\n)", "");
		log.info("原始表信息: {}. 去除换行后的数据: {}", tableStr, tableStrTemp);
		Matcher matcher = REG_TABLE.matcher(tableStrTemp);
		if (!matcher.find()) {
			throw new RuntimeException("表名/主键/备注不存在. 请添加");
		}
		
		String primaryKeyCol = matcher.group("primaryKeyCol");
		Col primaryCol = null;
		
		table.setTableName(matcher.group("tableName"));
		table.setTableRemark(matcher.group("tableRemark"));
		tableStrTemp = tableStrTemp.replaceAll("CREATE +TABLE +`(.*?)`", "");
		log.info("tableName: {}. tableRemark: {}", table.getTableName(), table.getTableRemark());
		
		String[] colStrs = tableStrTemp.split("',  ");
		
		List<String> excludeCol = Arrays.asList(PropertiesUtils.getConfigClass("generate.code.exclude.col", String[].class));
		List<Col> cols = new ArrayList<>();
		Col col;
		String colStr;
		for (int i = 0, len = colStrs.length; i < len; i++) {
			colStr = colStrs[i] + '\'';
			matcher = REG_COL.matcher(colStr);
			if (!matcher.find()) {
				continue;
			}
			String colName = matcher.group("colName");
			if (colName.equals(primaryKeyCol)) {
				col = new Col();
				col.setColName(colName);
				col.setColType(matcher.group("colType"));
				col.setColRemark(matcher.group("colRemark"));
				primaryCol = col;
			}
			if (excludeCol.contains(colName)) {
				continue;
			}
			col = new Col();
			col.setColName(colName);
			col.setColType(matcher.group("colType"));
			col.setColRemark(matcher.group("colRemark"));
			log.info("colName: {}. colType: {}. colRemark: {}", col.getColName(), col.getColType(), col.getColRemark());
			cols.add(col);
		}
		if (cols.isEmpty()) {
			new RuntimeException("未找到字段. ");
		}
		table.setCols(cols, primaryCol);
		
		return table;
	}
}
