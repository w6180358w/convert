### 通用规则

1. POST请求必须为json格式  即header中Content-Type:application/json
2. /conf/conf.properties 为系统配置文件  
--------

	#### 转换文档接口中 fileType参数对照表
| Code		|    Msg |
| :-------- | :--------|
|	DOC		|	Word 97 - 2003 文档 (.doc)   |
|	DOT		|	Word 97 - 2003 模板 (.dot)    |
|	TEXT	|	文本文档 (.txt)    |
|	RFT		|	RTF 格式 (.rtf)  |
|	HTML	|	HTML 文档 (.htm)(带文件夹)    |
|	MHTML	|	MHTML 文档 (.mht)(单文件)  |
|	XML		|	XML 文档 (.xml)    |
|	DOCX	|	Microsoft Word 文档 (.docx)    |
|	DOCM	|	Microsoft Word 启用宏的文档 (.docm)    |
|	DOTX	|	Microsoft Word 模板 (.dotx)   |
|	DOTM	|	Microsoft Word 启用宏的模板 (.dotm) |
|	PDF		|	PDF 文件 (.pdf)    |
|	XPS		|	XPS 文档 (.xps)   |
|	ODT		|	OpenDocument 文本 (.odt)    |
|	WTF		|	WTF 文件 (.wtf)  |
	