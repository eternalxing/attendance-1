package com.work.test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.work.dao.OwnReportDaoImpl;
import com.work.dao.WebSiteDaoImpl;
import com.work.entity.OwnReport;
import com.work.entity.WebSite;
import com.work.util.DetectDesignData;
import com.work.util.SendEmail;

/**
 * @author: jijiuxue
 * @date:2017-12-4 ����1:13:30
 * @version :1.0.0
 * 
 */
public class DateTest {
	public static void main(String[] args){
		OwnReportDaoImpl dao=new OwnReportDaoImpl();
		WebSiteDaoImpl sitedao=new WebSiteDaoImpl();
    	List<OwnReport> list=dao.getAllOwnReportByAutoReport();      
    	WebSite website=new WebSite();
	    website= sitedao.getWebSiteById(1);
    	Iterator<OwnReport> it=list.iterator();       	
//    	while(it.hasNext()){
//    	OwnReport ownReport=new OwnReport(); 
//    	ownReport=it.next();
//    	//��¼��վ
//    	DetectDesignData.imitateLogin(ownReport, website);
//    	}
       //��ȡ��ǰ��������
    	//String date1=DetectDesignData.parseCurrentDate(DetectDesignData.getPageData(website));
    	//ownReport.setWdate1(date1);
    	//���ñ�����    	
		//DetectDesignData.designData(ownReport, website);
		
		//System.out.println("yemiafdfdfdf:"+DetectDesignData.getPageData(website));
		//System.out.println("****************************************");
    	//��ȡδ�����ձ�����
     	//int i=DetectDesignData.parseHtmlData(DetectDesignData.getPageData(website));
     	//if(i==2){
        //	try {
		//		SendEmail.sendRemind(ownReport.getEmail(),ownReport.getUsername());
		//		System.out.println(ownReport.getUsername()+"�ʼ����ͳɹ�");
		//	} catch (Exception e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
    //   	}
    //	}
    	//ͬ������
	    website= sitedao.getWebSiteById(1);
    	while(it.hasNext()){
        	OwnReport ownReport=new OwnReport(); 
        	ownReport=it.next();
        	List<String> list1=DetectDesignData.getUserMonthReport(ownReport, website, "17-09-01");
        	System.out.println(ownReport.getUsername());
        	for(String val : list1){
        		System.out.println(val);
        	}

            
             //String value="2017/12/1";
             //int month=Integer.parseInt(date1.substring(5,date1.lastIndexOf("/")));
			//int day=Integer.parseInt(date1.substring(date1.lastIndexOf("/")+1));
			//	System.out.println(month+" "+day);
				
				
    	}
    	
    	
	}	

}
