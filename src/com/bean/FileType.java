package com.bean;

public enum FileType {
	DOC("doc",0),//Word 97 - 2003 文档 (.doc)   
	DOT("dot",1),//Word 97 - 2003 模板 (.dot)    
	TEXT("text",3),//文本文档 (.txt)    
	RFT("rtf",6),//RTF 格式 (.rtf)  
	HTML("htm",8),//HTML 文档 (.htm)(带文件夹)    
	MHTML("mht",9),//MHTML 文档 (.mht)(单文件)  
	XML("xml",11),//XML 文档 (.xml)    
	DOCX("docx",12),//Microsoft Word 文档 (.docx)    
	DOCM("docm",13),//Microsoft Word 启用宏的文档 (.docm)    
	DOTX("dotx",14),//Microsoft Word 模板 (.dotx)   
	DOTM("dotm",15),//Microsoft Word 启用宏的模板 (.dotm) 
	PDF("pdf",17),//PDF 文件 (.pdf)    
	XPS("xps",18),//XPS 文档 (.xps)   
	ODT("odt",23),//OpenDocument 文本 (.odt)    
	WTF("wtf",24);//WTF 文件 (.wtf)   
	
	private FileType(String name,Integer type) {
		this.name = name;
		this.type = type;
	}
	private String name;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
