package com.bean;

import java.util.List;
import java.util.Map;

public class OfficeBean {

	private String path;	//目录
	private String handle;	//操作  edit preview
	private String desPath;	//加密过后的文件路径
	private FileType fileType;//文件类型
	private String output;	//输出的文件路径 带文件名
	
	private Map<String,Object> data;	//要替换的数据
	
	private List<String> merge;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public List<String> getMerge() {
		return merge;
	}
	public void setMerge(List<String> merge) {
		this.merge = merge;
	}
	public String getDesPath() {
		return desPath;
	}
	public void setDesPath(String desPath) {
		this.desPath = desPath;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
}
