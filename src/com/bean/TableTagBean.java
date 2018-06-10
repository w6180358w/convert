package com.bean;

import com.jacob.com.Dispatch;

public class TableTagBean {

	private Dispatch table;	//要替换的表格
	
	private Dispatch row;	//要替换表格的开始行
	
	private int tableIndex;	//表格标记 第几个
	
	private int rowIndex;	//行标记  第几行

	public Dispatch getTable() {
		return table;
	}

	public void setTable(Dispatch table) {
		this.table = table;
	}

	public Dispatch getRow() {
		return row;
	}

	public void setRow(Dispatch row) {
		this.row = row;
	}

	public int getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
}
