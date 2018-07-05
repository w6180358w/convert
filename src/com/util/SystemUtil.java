package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.ImageBean;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import sun.misc.BASE64Decoder;

public class SystemUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SystemUtil.class);
	
	public static JSONObject request(int code,List<?> data,String msg){
		JSONObject result = new JSONObject();
		JsonConfig con = new JsonConfig();
		con.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		result.put("code", code);
		result.put("data", JSONArray.fromObject(data,con));
		result.put("msg", msg);
		logger.info("result :{}",result.toString());
		return result;
	}
	
	public static JSONObject request(int code,Object data,String msg){
		JSONObject result = new JSONObject();
		JsonConfig con = new JsonConfig();
		con.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		result.put("code", code);
		result.put("data", JSONObject.fromObject(data,con));
		result.put("msg", msg);
		logger.info("result :{}",result.toString());
		return result;
	}
	
	public static JSONObject request(int code,String data,String msg){
		JSONObject result = new JSONObject();
		JsonConfig con = new JsonConfig();
		con.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		result.put("code", code);
		result.put("data", data);
		result.put("msg", msg);
		logger.info("result :{}",result.toString());
		return result;
	}
	
	public static PageOfficeCtrl getCtrl(HttpServletRequest request) {
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		request.setAttribute("ctrl", poCtrl);
		return poCtrl;
	}
	
	public static PageOfficeCtrl open(PageOfficeCtrl poCtrl,String path) {
		//设置保存页面
		poCtrl.setSaveFilePage("save");
		//打开Word文档
		poCtrl.webOpen(path,OpenModeType.docNormalEdit,"张佚名");
		return poCtrl;
	}
	/**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
    /**
     * 获取加密路径和未加密路径的对象
     * @param path
     * @param desPath
     * @return
     */
    public static JSONObject getAllPath(String path,String desPath) {
    	JSONObject obj = new JSONObject();
    	obj.put("path", path);
    	obj.put("desPath", desPath);
    	return obj;
    }
    
    public static String getFileName(String path) throws Exception {
    	File file = valFile(path);
    	return file.getName();
    }
    
    //获取不带后缀名的文件名
    public static String getFileNameWithoutSuffix(String path) throws Exception {
        String file_name = getFileName(path);
        return file_name.substring(0, file_name.lastIndexOf("."));
    }
    
    //获取不带后缀名的路径
    public static String getPathWithoutSuffix(String path) throws Exception {
    	File file = new File(path);
    	if(file.isDirectory()) {
    		throw new Exception("该路径为文件夹："+path);
    	}
    	int last = path.lastIndexOf(".");
        return last==-1?path:path.substring(0, last);
    }
    //验证是否为文件
    public static File valFile(String path) throws Exception {
    	File file = new File(path);
    	if(!file.exists()) {
    		throw new Exception("文件不存在："+path);
    	}
    	if(file.isDirectory()) {
    		throw new Exception("该路径为文件夹："+path);
    	}
    	return file;
    }
    //获取文件后缀名
    public static String getFileSuffix(String path) throws Exception {
        String fileName = getFileName(path);
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }
    
    public static void copyFile(String oldPath,String newPath) throws Exception {
    	FileInputStream in = null;
    	FileOutputStream out = null;
    	try {
			File oldFile = new File(oldPath);
			File file = new File(newPath);
			in = new FileInputStream(oldFile);
			out = new FileOutputStream(file);;

			byte[] buffer=new byte[2097152];
			
			while((in.read(buffer)) != -1){
			    out.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(in!=null) in.close();
			if(out!=null) out.close();
		}
    }
    
    //base64字符串转化成图片  
    public static ImageBean generateImage(ImageBean bean) throws Exception  { 
    	logger.info("generateImage start :{}",JSONObject.fromObject(bean).toString());
    	String result = null;
    	//对字节数组字符串进行Base64解码并生成图片  
        if (bean.getBase64() == null || "".equals(bean.getBase64())) //图像数据为空  
        	return null;
        BASE64Decoder decoder = new BASE64Decoder();  
        OutputStream out = null;
        try {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(bean.getBase64());  
            for(int i=0;i<b.length;++i)  {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            result = bean.getPath()+System.currentTimeMillis()+"."+bean.getSuffix();//新生成的图片  
            out = new FileOutputStream(result);      
            out.write(b);  
            out.flush();  
            bean.setPath(result);
        }   
        catch (Exception e) {  
        	logger.info("generateImage error:{}",e);
        	throw e;
        }  finally {
        	if(out!=null)out.close();
        }
        logger.info("generateImage success:{}",JSONObject.fromObject(bean).toString());
        return bean;
    }  
}
