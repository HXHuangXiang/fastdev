# fastdev

#### 介绍


#### 使用说明

vm 可用属性  
>使用如: ${table.tableName}/${table.cols}...

* tableName: 表名
* tableRemark: 表备注
* tableNameAlias: 表别名/缩写. 如 table_detail td
* tableDot: 表名转换为带点. 如 table_detail table.detail
* tableTilt: 表名转换为带斜杠. 如 table_detail table/detail
* tableHump: 表名驼峰首字母小写. 如 table_detail tableDetail
* tableHumpBig: 表名驼峰首字母大写. 如 table_detail TableDetail
* tableKeyword: 上述字段转 map
* cols: 表字段列表
    * colName: 字段名称
    * colType: 字段数据库类型
    * colRemark: 字段注释
    * colNameHump: 字段驼峰
    * colNameHumpBig: 字段驼峰首字母大写
    * colTypeJava: 字段类型转 java 类型





