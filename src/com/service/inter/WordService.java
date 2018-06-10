package com.service.inter;

import com.bean.OfficeBean;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

public interface WordService {
	/**
	 * 保存文件
	 * @param fs
	 * @param path
	 * @throws Exception
	 */
	public void save(FileSaver fs,String path) throws Exception;
	/**
	 * 数据替换
	 * @return
	 */
	public String replace(OfficeBean bean) throws Exception;
	/**
	 * 清空文件夹或文件
	 * @throws Exception
	 */
	public void clear(String path) throws Exception;
	/**
	 * 在线预览
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public PageOfficeCtrl preview(PageOfficeCtrl poCtrl,String path) throws Exception;
	/**
	 * 在线编辑
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public PageOfficeCtrl edit(PageOfficeCtrl poCtrl,String path) throws Exception;
	/**
	 * 转pdf
	 * @return
	 * @throws Exception
	 */
	public String convert(OfficeBean bean) throws Exception;
	/**
	 * 合并word
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String merge(OfficeBean bean) throws Exception;
	/**
	 * 提取数据
	 * @return
	 * @throws Exception
	 */
	public PageOfficeCtrl extract() throws Exception;
}
