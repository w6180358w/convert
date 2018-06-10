package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.OfficeBean;
import com.service.inter.WordService;
import com.util.DesUtil;
import com.util.SystemUtil;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/word")
public class WordController {
	
	private Logger logger = LoggerFactory.getLogger(WordController.class);
	
	@Autowired
	WordService wordService;
	@Value("#{configProperties['server']}")
	private String server;
	@Value("#{configProperties['tempFile']}")
	private String tempFile;
	
	private static final String KEY = "convert";
	
	/**
	 * @api {POST} /word/des 1.   地址加密
	 * @apiName /word/des
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String} path 路径+文件名（带后缀）
	 * @apiParam {String} handle 操作符 如果有值则返回一个可以访问的url  如果为空则返回加密过后的文件路径字符串
	 * 								允许值  edit:修改文档 preview:预览文档
	 *
	 * @apiSuccess {Number} code 0:失败 1:成功
	 * @apiSuccess {String} msg 提示消息
	 * @apiSuccess {String} data 加密过后的文件路径字符串
	 * @apiParamExample {json} Request-Example:
	 * {
	 *	"path":"f:\\"
	 *	}
	 *{
	 *	"path":"f:\\",
	 *	"handle":"preview"
	 *	}
	 * @apiSuccessExample {json} Success-Response:
	 *	{
	 *	    "code": 1,
	 *	    "data": "ENZr78FpjyWxiSnBMcetWQ%3D%3D",
	 *	    "msg": "加密成功"
	 *	}
	 *	{
	 *	    "code": 1,
	 *	    "data": "http://127.0.0.1:8080/convert/word/preview?path=ENZr78FpjyWxiSnBMcetWQ%3D%3D",
	 *	    "msg": "加密成功"
	 *	}
	 */
	@RequestMapping(value = "/des" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject des(HttpServletRequest request,
			HttpServletResponse response,@RequestBody OfficeBean bean) throws Exception{
		String filePath = DesUtil.encrypt(bean.getPath(), KEY);
		String result = URLEncoder.encode(filePath,"UTF-8");
		if(bean.getHandle()!=null && !"".equals(bean.getHandle())) {
			result = server+"word/"+bean.getHandle()+"?path="+result;
		}
		return SystemUtil.request(1, result, "加密成功");
	}
	/**
	 * @api {POST} /word/clear  2.   清空临时文件夹
	 * @apiName /word/clear
	 * @apiGroup word
	 *
	 * @apiSuccess {Number} code 0:失败 1:成功
	 * @apiSuccess {String} msg 提示消息
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *	{
	 *	    "code": 1,
	 *	    "data": "",
	 *	    "msg": "清空临时文件夹成功"
	 *	}
	 */
	@RequestMapping(value = "/clear" , method = RequestMethod.GET)
	@ResponseBody
	public JSONObject clear(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		logger.info("clear temp directory ");
		String msg = "清空临时文件夹成功";
		int code = 1;
		try {
			if(new File(tempFile).getParent()==null) {
				throw new Exception("根目录不允许删除："+tempFile);
			}
			SystemUtil.deleteDirectory(tempFile);
		} catch (Exception e) {
			logger.error("clear temp directory error :{} ",e);
			code=0;
			msg = "清空临时文件夹失败,msg:"+e.getMessage();
		}
		logger.info("clear temp directory success:{} ");
		return SystemUtil.request(code, "", msg);
	}
	
	@RequestMapping("/save")
	public String save(HttpServletRequest request,
			HttpServletResponse response,String path) throws Exception{
		logger.info("save word:{}:",path);
		path = getPath(path);
		FileSaver fs=new FileSaver(request,response);
		this.wordService.save(fs, path);
		logger.info("save word success:{}:",path);
		return null;
	}
	/**
	 * @api {GET} /word/preview  3.   文件预览页面
	 * @apiName /word/preview
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String} path 加密过后的路径路径 
	 *
	 */
	@RequestMapping("/preview")
	public String previewShow(HttpServletRequest request,
			HttpServletResponse response,String path) throws Exception{
		logger.info("show preview word page:{}",path);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");

		poCtrl.setTitlebar(false);//隐藏标题栏
		poCtrl.setAllowCopy(false);//禁止拷贝
		poCtrl.setMenubar(false);//隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(false);//隐藏自定义工具栏

		poCtrl.webOpen(getPath(path),OpenModeType.docNormalEdit,"张佚名");
		
		request.setAttribute("ctrl", poCtrl);
		return "show";
	}
	/**
	 * @api {GET} /word/edit  4.   文件修改页面
	 * @apiName /word/edit
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String} path 加密过后的路径路径 
	 *
	 */
	@RequestMapping("/edit")
	public String editShow(HttpServletRequest request,
			HttpServletResponse response,String path) throws Exception{
		logger.info("show edit word page:{}",path);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		//添加自定义按钮
		poCtrl.addCustomToolButton("保存","Save()",1);
		poCtrl.addCustomToolButton("关闭","CloseFile()",21);
		//设置保存页面
		poCtrl.setSaveFilePage("save?path="+URLEncoder.encode(path,"UTF-8"));
		poCtrl.webOpen(getPath(path),OpenModeType.docNormalEdit,"张佚名");
		
		request.setAttribute("ctrl", poCtrl);
		return "show";
	}
	
	private String getPath(String path) {
		String url = "";
		logger.info("start des path:{}",path);
		try {
			url = DesUtil.decrypt(path, KEY);
		} catch (Exception e) {
			logger.error("des path error:{}",e);
		}
		logger.info("end des path:{}",url);
		return url;
	}
	
	/**
	 * @api {GET} /word/get  5.   下载文件
	 * @apiName /word/get
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String} path 加密过后的路径路径 
	 *
	 */
	@RequestMapping(value = "/get" , method = RequestMethod.GET)
	public ResponseEntity<byte[]> get(HttpServletRequest request,
			HttpServletResponse response,String path) throws Exception{
	    java.io.BufferedInputStream bis = null;  
	    java.io.BufferedOutputStream bos = null;  
	    logger.info("download file :{}",path);
	    try {  
	    	path = getPath(path);
	    	String[] temp = path.split("\\\\");
	    	String filename = temp[temp.length-1];
	        long fileLength = new File(path).length();  
	        response.setContentType("application/x-msdownload;");  
	        response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1"));  
	        response.setHeader("Content-Length", String.valueOf(fileLength));  
	        bis = new BufferedInputStream(new FileInputStream(path));  
	        bos = new BufferedOutputStream(response.getOutputStream());  
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	    } catch (Exception e) {  
	    	logger.info("download file error:{}",e);
	        e.printStackTrace();  
	    } finally {  
	        if (bis != null)  
	            try {  
	                bis.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        if (bos != null)  
	            try {  
	                bos.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	    }  
	    return null; 
	}
	
	/**
	 * @api {POST} /word/merge  6.   合并文档(强制生成pdf文档)
	 * @apiName /word/merge
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String[]} merge 加密文件路径数组
	 * @apiParam {String} output 输出文件全路径+文件名+后缀  后缀可加可不加  强制转换为.pdf 
	 *
	 * @apiSuccess {Number} code 0:失败 1:成功
	 * @apiSuccess {String} msg 提示消息
	 * @apiSuccess {String} data 合并后的加密文件路径字符串
	 * @apiParamExample {json} Request-Example:
	 *	{
	 *	"merge":["F:\\1c","F:\\1cx","F:\\testc","F:\\测试c"],
	 *	"output":"output":"f:\\测试新版.pdf",
	 *	}
	 * @apiSuccessExample {json} Success-Response:
	 *	{
	 *	    "code": 1,
	 *	    "data": "FI3J2EPD%2BB%2FQqcJTe5Zlm7JqudbhVB71Po5zUtOQ%2Fxg%3D",
	 *	    "msg": "合并成功"
	 *	}
	 */
	@RequestMapping(value = "/merge" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject merge(HttpServletRequest request,
			HttpServletResponse response,@RequestBody OfficeBean bean) throws Exception{
		logger.info("merge word:{}");
		String msg = "合并成功";
		int code = 1;
		JSONObject allPath = new JSONObject();
		try {
			String file = this.wordService.merge(bean);
			String result = getDownloadKey(file);
			allPath = SystemUtil.getAllPath(file, result);
		} catch (Exception e) {
			logger.error("merge word error:{}",e);
			msg = "合并失败,msg:"+e.getMessage();
			code = 0;
		}
		logger.info("merge word success:{}",allPath);
		return SystemUtil.request(code, allPath, msg);
	}
	
	/**
	 * @api {POST} /word/replace  7.   替换文档内容(强制生成docx文档)
	 * @apiName /word/replace
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String} path 文件路径+文件名（带后缀）
	 * @apiParam {String} output 输出文件全路径+文件名+后缀 为空则保存到临时文件夹中
	 * @apiParam {Data} data 需要替换的内容对象，key值为替换关键字，value为内容 如果替换内容为表格 key值必须以Table为开头 以【_】下划线为连接 表格标记为结尾 
	 * 							例如：Table_1 表示替换第一个表格的内容  Table_2替换第二个表格的内容
	 * @apiParam {String[][]} data.Table_*  需要替换表格数据  一个二维数组  一维代表行  二维代表列
	 *
	 * @apiSuccess {Number} code 0:失败 1:成功
	 * @apiSuccess {String} msg 提示消息
	 * @apiSuccess {String} data 替换后的加密文件路径字符串
	 * @apiParamExample {json} Request-Example:
	 *	{
	 *	"path":"f:\\测试.doc",
	 *	"data":{"com":"测试com","name":"测试name","Table_1":[["a","b","c"],["c","d","e"]]},
	 *	"output":"f:\\测试新版.docx"
	 *	}
	 * @apiSuccessExample {json} Success-Response:
	 *	{
	 *	    "code": 1,
	 *	    "data": "FI3J2EPD%2BB%2FQqcJTe5Zlm7JqudbhVB71Po5zUtOQ%2Fxg%3D",
	 *	    "msg": "替换成功"
	 *	}
	 */
	@RequestMapping(value = "/replace" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject replace(HttpServletRequest request,
			HttpServletResponse response,@RequestBody OfficeBean bean) throws Exception{
		logger.info("replace word:{}");
		String msg = "替换成功";
		int code = 1;
		JSONObject allPath = new JSONObject();
		try {
			String file = this.wordService.replace(bean);
			String result = getDownloadKey(file);
			allPath = SystemUtil.getAllPath(file, result);
		} catch (Exception e) {
			logger.error("replace word error:{}",e);
			msg = "替换失败,msg:"+e.getMessage();
			code = 0;
		}
		logger.info("replace word success :{}",allPath);
		return SystemUtil.request(code, allPath, msg);
	}
	
	/**
	 * @api {POST} /word/convert  8.   转换文档类型
	 * @apiName /word/convert
	 * @apiGroup word
	 *
	 * @apiHeader Content-Type=application/json
	 * 
	 * @apiParam {String} path 文件路径明文（全地址） 转换模板文件时用到 与desPath参数互斥
	 * @apiParam {String} desPath 加密文件路径 与path参数互斥
	 * @apiParam {String} fileType 要转换的文件类型 详见base
	 * @apiParam {String} output 输出文件全路径+文件名+后缀 为空则保存到临时文件夹中
	 *
	 * @apiSuccess {Number} code 0:失败 1:成功
	 * @apiSuccess {String} msg 提示消息
	 * @apiSuccess {String} data 转换后的加密文件路径字符串
	 * @apiParamExample {json} Request-Example:
	 *	{
	 *	"desPath":"FI3J2EPD%2BB%2FZmoj0Ns2gds%2F%2FllBbX7e1wO5nvObtFTk%3D",
	 *	"output":"f:\\测试新版.docx"
	 *	}
	 *	{
	 *	"path":"f:\\测试c",
	 *	"output":"f:\\测试新版.docx"
	 *	}
	 *	
	 * @apiSuccessExample {json} Success-Response:
	 *	{
	 *	    "code": 1,
	 *	    "data": "FI3J2EPD%2BB%2FQqcJTe5Zlm7JqudbhVB71Po5zUtOQ%2Fxg%3D",
	 *	    "msg": "转换成功"
	 *	}
	 */
	@RequestMapping(value = "/convert" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject convert(HttpServletRequest request,
			HttpServletResponse response,@RequestBody OfficeBean bean) throws Exception{
		logger.info("convert file :{}",JSONObject.fromObject(bean).toString());
		String msg = "转换成功";
		int code = 1;
		JSONObject allPath = new JSONObject();
		try {
			//如果是加密路径  则解密
			if(bean.getDesPath()!=null && !"".equals(bean.getDesPath())) {
				bean.setPath(this.getPath(URLDecoder.decode(bean.getDesPath(), "UTF-8")));
			}
			String file = this.wordService.convert(bean);
			String result = getDownloadKey(file);
			allPath = SystemUtil.getAllPath(file, result);
		} catch (Exception e) {
			logger.error("convert file error:{}",e);
			msg = "转换失败,msg:"+e.getMessage();
			code = 0;
		}
		logger.info("convert file success :{}",allPath);
		return SystemUtil.request(code, allPath, msg);
	}
	//地址加密并转换编码
	private String getDownloadKey(String path) throws Exception {
		return URLEncoder.encode(DesUtil.encrypt(path, KEY),"UTF-8");
	}
}