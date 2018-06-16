package com.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bean.FileType;
import com.bean.ImageBean;
import com.bean.OfficeBean;
import com.jacob.com.ComThread;
import com.service.inter.WordService;
import com.util.MSWordManager;
import com.util.SystemUtil;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import net.sf.json.JSONObject;

@Service
public class WordServiceImpl implements WordService{
	
	private Logger logger = LoggerFactory.getLogger(WordServiceImpl.class);
	
	@Value("#{configProperties['tempFile']}")
	private String tempFile;
	
	@Override
	public void save(FileSaver fs, String path) throws Exception {
		fs.saveToFile(path);
		fs.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String replace(OfficeBean bean) throws Exception {
		logger.info("start replace word :{}",JSONObject.fromObject(bean).toString());
		String suffix = SystemUtil.getFileSuffix(bean.getPath()).toLowerCase();
		if(!FileType.DOC.getName().equals(suffix) && !FileType.DOCX.getName().equals(suffix)) {
			throw new Exception("源文件只支持word文档");
		}
		MSWordManager ms= null;
    	ComThread.InitSTA(); 
    	String temp = null;
    	String result = "";
    	try {
			ms=new MSWordManager(false);  
			//获取临时文件  操作临时文件  返回临时文件全路径
			result = this.createTemp(bean.getPath(),FileType.DOCX,bean.getOutput());
			ms.openDocument(result);
			//循环参数
			for (Entry<String, Object> entry : bean.getData().entrySet()) {
				String key = entry.getKey();
				if(key.indexOf("Table_")>-1) {
					ms.replaceTable(key,(List<List<String>>) entry.getValue());
				}else if(key.indexOf("Image_")>-1) {
					ImageBean image = (ImageBean) JSONObject.toBean(JSONObject.fromObject(entry.getValue()), ImageBean.class);
					//设置临时文件夹
					image.setPath(tempFile);
					//转图片
					SystemUtil.generateImage(image);
					//如果返回路径不为空  则替换图片
					if(image.getPath()!=null && !"".equals(image.getPath())) {
						ms.replaceAllImage(key,image);
					}
				}else {
					ms.replaceAllText(key, entry.getValue()+"");
				}
			}
			ms.setSaveOnExit(false);
			ms.save();
		} catch (Exception e) {
			logger.error("replace word error:{}",e);
			throw e;
		} finally {
			if(ms!=null) {
				ms.closeDocument();
				ms.close();
			}
			ComThread.Release();
			if(temp!=null && !"".equals(temp))new File(temp).delete();
		}
    	logger.info("end replace word : {}",result);
		return result;
	}
	/**
	 * //创建临时文件  操作时都用临时文件
	 * @param path		源文件地址
	 * @param docType	转换文件类型（为空则不转换）
	 * @param fileName	临时文件名称
	 * @return
	 * @throws Exception
	 */
	private synchronized String createTemp(String path,FileType fileType,String output) throws Exception {
		logger.info("start createTemp word :{},{},{}",path,fileType,output);
    	MSWordManager ms= null;
    	try {
    		//判断临时文件夹是否存在 如果不存在创建
			this.createTempD(tempFile);
			if(output==null || "".equals(output)) {
				output = tempFile+SystemUtil.getFileName(path);
			}
			ms=new MSWordManager(false);  
			ms.openDocument(path);
			ms.setSaveOnExit(false);
			if(fileType!=null) {
				//根据要转换的类型  构造路径  文件名
				output = SystemUtil.getPathWithoutSuffix(output)+"."+fileType.getName();
				ms.saveAs(output,fileType.getType());
			}else {
				ms.saveAs(output);
			}
		} catch (Exception e) {
			logger.error("createTemp word error :{}",e);
			throw e;
		} finally {
			if(ms!=null) {
				ms.closeDocument();
				ms.close();
			}
		}
    	logger.info("end createTemp word :{}",output);
    	return output;
    }
	
	@Override
	public void clear(String path) throws Exception {
		
	}

	@Override
	public PageOfficeCtrl preview(PageOfficeCtrl poCtrl,String path) throws Exception {
		//设置保存页面
		poCtrl.setSaveFilePage("save");
		//打开Word文档
		poCtrl.webOpen(path,OpenModeType.docNormalEdit,"张佚名");
		return poCtrl;
	}

	@Override
	public PageOfficeCtrl edit(PageOfficeCtrl poCtrl,String path) throws Exception {
		poCtrl.addCustomToolButton("保存", "Save()", 1);
		poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		//设置保存页面
		poCtrl.setSaveFilePage("SaveFile.jsp");
		//打开Word文档
		poCtrl.webOpen("doc/test.doc",OpenModeType.docNormalEdit,"张佚名");
		return poCtrl;
	}

	@Override
	public String convert(OfficeBean bean) throws Exception {
		logger.info("start convert pdf :{},{}",JSONObject.fromObject(bean).toString());
		if(bean.getFileType()==null) {
			throw new Exception("转换类型不能为空");
		}
		String suffix = SystemUtil.getFileSuffix(bean.getPath()).toLowerCase();
		if(!FileType.DOC.getName().equals(suffix) && !FileType.DOCX.getName().equals(suffix)) {
			throw new Exception("源文件只支持word文档");
		}
		String result = null;
		ComThread.InitSTA(); 
		try {
			result = this.createTemp(bean.getPath(), bean.getFileType(), bean.getOutput());
		} catch (Exception e) {
			logger.error("convert pdf error :{}",e);
			throw e;
		} finally {
			ComThread.Release();
		}
		logger.info("end convert pdf :{}",result);
		return result;
	}
	
	@Override
	public String merge(OfficeBean bean) throws Exception {
		logger.info("start word merge :{}",JSONObject.fromObject(bean).toString());
		List<String> merge = bean.getMerge();
		String result = bean.getOutput();
		//验证文件格式
		for (String path : merge) {
			String suffix = SystemUtil.getFileSuffix(path).toLowerCase();
			if(!FileType.DOC.getName().equals(suffix) 
					&& !FileType.DOCX.getName().equals(suffix)
					&& !FileType.PDF.getName().equals(suffix)) {
				throw new Exception("源文件只支持word文档或pdf文档");
			}
		}
		//输出路径验证
		if(result==null || "".equals(result)) {
			throw new Exception("输出文件路径不能为空");
		}
		ComThread.InitSTA(); 
		List<String> tempFiles = null;//临时文件路径
		try {
			result = SystemUtil.getPathWithoutSuffix(result)+".pdf";
			//合并文档 先转pdf  合并pdf
			PDFMergerUtility mergePdf = new PDFMergerUtility();
			tempFiles = new ArrayList<String>();
			for (String path : merge) {
				//根据地址将文档转换成pdf
				String filePath = tempFile+System.currentTimeMillis()+"_merge.pdf";
				//如果为pdf文件  不需要转换直接复制到临时文件夹
				if(SystemUtil.getFileSuffix(path).toLowerCase().equals(FileType.PDF.getName())) {
					SystemUtil.copyFile(path, filePath);
				}else {
					this.createTemp(path, FileType.PDF,filePath);
				}
				mergePdf.addSource(filePath);
				tempFiles.add(filePath);
			}
			//合并pdf
			mergePdf.setDestinationFileName(result);
			mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
		} catch (Exception e) {
			logger.error("word merge error",e);
			throw e;
		} finally {
			ComThread.Release();
			//删除临时文件
			if(tempFiles!=null && !tempFiles.isEmpty()) {
				for (String name : tempFiles) {
					File file = new File(name);
					if(file.exists()) file.delete();
				}
			}
		}
		logger.info("end word merge :{}",result);
		return result;
	}

	@Override
	public PageOfficeCtrl extract() throws Exception {
		return null;
	}
	
	private void createTempD(String tempPath) {
		File tem = new File(tempPath);
		if(!tem.exists()) {
			tem.mkdirs();
		}
	}

}
