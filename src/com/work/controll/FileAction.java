package com.work.controll;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.work.service.EmployeeService;
import com.work.service.PhotoService;
import com.work.util.FileUpLoad;


//�й��ϴ������صĿ�������
//�ϴ�����upload()
//���ط���updown()
@Controller
public class FileAction {
	private static Logger log=Logger.getLogger(FileAction.class);
	@Autowired EmployeeService employeeservice;
	@Autowired PhotoService photoservice;
	@RequestMapping("upload.do")
	@ResponseBody
	public  String upload(@RequestParam("file") MultipartFile uploadfile, HttpSession session){
	 String filename= uploadfile.getOriginalFilename();
	 String leftPath= session.getServletContext().getRealPath("file");
	  String message=null;
	      File file=new File(leftPath,filename); 	     	     
	      if (!file.exists()) {
	    	  file.mkdirs();
	        }
	      try {
			uploadfile.transferTo(file);
			String path=file.getAbsolutePath();
			FileUpLoad.readExcel(path);
			if(file.isFile()){
				file.delete();
			}
			message="1";
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("�ļ��������");
			message="0";
		}
	      return message;
	}
	/*
	 * �ļ����ط���
	 * @param filename month
	 * @output file.xls
	 * @version 1.1.1
	 * @author JIJIUXUE
	 */
   @RequestMapping("download.do")
	public void updown(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws UnsupportedEncodingException {
      String fileName=request.getParameter("fileName");
      String month=request.getParameter("month");
      //��Ҫ�޸�
      //String submonth=month.substring(5, 6);
      String headfilename="���࿼��"+month+"�·�.xls";
      try {
		fileName= new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	   response.setCharacterEncoding("utf-8");
	   response.setContentType("multipart/form-data");
	   response.setHeader("Content-Disposition", "attachment;filename="+new String(headfilename.getBytes("utf-8"),"iso-8859-1"));
	   String leftPath= session.getServletContext().getRealPath("file");
	   File file=new File(leftPath,fileName); 
	   FileUpLoad.writeExcel(leftPath, month);

	  try {
		InputStream input=new FileInputStream(file);
		OutputStream os=response.getOutputStream();
		byte[] b=new byte[2048];
		int length;
		while((length=input.read(b))>0){
			os.write(b, 0, length);
		}
		os.close();//�ر���
		input.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	//�û�ͷ���ϴ�
   @RequestMapping("uploadImage.do")
   @ResponseBody
   public String uploadImage(@RequestParam("imagefile") MultipartFile uploadfile,HttpSession session,HttpServletRequest request ){
	  //��ȡ�û�id
	String userid=(String) request.getSession().getAttribute("username");
	  String info="";            //���������ϴ���� 
	  boolean isimage=false;     //�ж��Ƿ���ͼƬ
	  boolean filesizeisOK=false; //�ж��ļ���С�Ƿ����Ҫ��
	 String imagename=uploadfile.getOriginalFilename();
	 String path=  session.getServletContext().getRealPath("userimage");
     String type=uploadfile.getContentType();
     long size=uploadfile.getSize();
     //�ж��ļ������Ƿ�����ͼƬ����.gif",".png",".jpeg",".jpg",".bmp"
     String[] allowtype={"gif","png","jpeg","jpg","bmp"};
     for(int i=0;i<allowtype.length;i++){
    	 if(type.contains(allowtype[i])){
    		 isimage=true;
    	 }
     }
     //�ж��ļ���С���ܴ���500k
     if((size/1024)<500){
    	 filesizeisOK=true;
     }
     if(isimage==true && filesizeisOK==true){
    	 
    	 //�޸�ͷ�������Ϊ�û���
    	 String oldimagename=imagename.substring(0,imagename.lastIndexOf("."));
    	 //�滻�ļ���Ϊ�û���¼��
    	 imagename= imagename.replace(oldimagename, userid);
    	 //�����û��������ݿ�
    	 employeeservice.imagePath(userid, imagename);
    	 request.getSession().setAttribute("imagepath", imagename);
    	 File file=new File(path,imagename); 
    	 try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    		 try {
    			uploadfile.transferTo(file);
    		} catch (IllegalStateException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			log.error("��Ƭ�ϴ�ʧ��");
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    	  info="1";
     }else if(isimage==false){
    	 info="2";//�ļ����Ͳ�����
     }else if(filesizeisOK==false){
    	 info="3";//���ȳ���500K
    	 
     }else{
    	 info="4";//�ļ��ϴ�ʧ��
     }

	   return info;
   }
  
}
