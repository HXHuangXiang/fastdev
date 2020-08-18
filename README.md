# fastdev

## 介绍


## 使用说明
### 生成模板代码
#### 根据创建表语句生成
1. 在 properties/config.properties 中配置模板路径等
2. 在 Navicat 中查看表的 DDL 信息(选中表右键对象信息, 在右侧选择 DDL)并复制
3. 打开 com.hx.fastdev.generate.code.GenerateCodeUtils 运行即可

### vm 模板可用属性 
>使用如: ${table.tableName}/${table.cols}...

* tableName: 表名
* tableRemark: 表备注
* tableNameAlias: 表别名/缩写. 如 table_detail td
* tableDot: 表名转换为带点. 如 table_detail table.detail
* tableTilt: 表名转换为带斜杠. 如 table_detail table/detail
* tableHump: 表名驼峰首字母小写. 如 table_detail tableDetail
* tableHumpBig: 表名驼峰首字母大写. 如 table_detail TableDetail
* tableKeyword: 上述字段转 map
* primaryCol: 主键字段
* cols: 表字段列表
    * colName: 字段名称
    * colType: 字段数据库类型
    * colRemark: 字段注释
    * colNameHump: 字段驼峰
    * colNameHumpBig: 字段驼峰首字母大写
    * colTypeJava: 字段类型转 java 类型

路径可用属性
>使用如: ${tableName}

* tableName: 表名
* tableRemark: 表备注
* tableNameAlias: 表别名/缩写. 如 table_detail td
* tableDot: 表名转换为带点. 如 table_detail table.detail
* tableTilt: 表名转换为带斜杠. 如 table_detail table/detail
* tableHump: 表名驼峰首字母小写. 如 table_detail tableDetail
* tableHumpBig: 表名驼峰首字母大写. 如 table_detail TableDetail


