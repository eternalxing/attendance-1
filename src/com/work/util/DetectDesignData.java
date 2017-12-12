package com.work.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.work.dao.OwnReportDaoDetailImpl;
import com.work.entity.OwnReport;
import com.work.entity.OwnReportDetail;
import com.work.entity.WebSite;

public class DetectDesignData {	
	/**
	 * 状态常量
	 */
	final private static int STATUS=200;	
  
   
	public static void imitateLogin(OwnReport ownReport,WebSite website) {
		/**
		 * 模拟登录开始
		 */
		HttpClient httpClient = new HttpClient();              //每次登录都创建一个新的对象
		StringBuffer tmpcookies = new StringBuffer();
		String loginUrl = website.getLoginurl();
		
		PostMethod postMethod = new PostMethod(loginUrl);
		
		byte[] b = null;
		try {
			b = BASE64.decryptBASE64(ownReport.getPassword());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String pass=new String(b);
		NameValuePair[] data = { new NameValuePair("uname", ownReport.getUsername()), new NameValuePair("pass", pass),
				new NameValuePair("xoops_redirect", "/in/"), new NameValuePair("op", "login") };
		
		postMethod.setRequestBody(data);
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		try {
			httpClient.executeMethod(postMethod);
			
			Cookie[] cookies = httpClient.getState().getCookies();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
			}	
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * 获取表单填写页面开始
		 */
		String getUrl = website.getPageurl();
		GetMethod getMethod = new GetMethod(getUrl);
		
		getMethod.setRequestHeader("cookie", tmpcookies.toString());
	
		getMethod.setRequestHeader("Referer", website.getReferer());
		getMethod.setRequestHeader("User-Agent",website.getAgent());
		getMethod.setRequestHeader("Content-Type", website.getContenttype());
		getMethod.setRequestHeader("Accept-Language", website.getLanguage());
		getMethod.setRequestHeader("Accept-Encoding", website.getEncoding());
		getMethod.setRequestHeader("Connection", website.getConnection());
		getMethod.setRequestHeader("Host", website.getHost());		
		String htmltext = null;
		try {
			httpClient.executeMethod(getMethod);
			htmltext = getMethod.getResponseBodyAsString();			
			//System.out.println(htmltext);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * 解析网页数据 返回未写次数
		 * 如果有两天未写则提醒发邮件
		 */
		Document doc=Jsoup.parse(htmltext); 
		Element element=doc.getElementById("content");
		Elements elements=element.getElementsByTag("a");
		String mainValue=elements.text();
		char[] c=mainValue.toCharArray();
		int flag=0;
		for(int i=0;i<c.length;i++){
			if(c[i]=='无'){
			flag++;
			}
		}
		/**
		 * 查询用户数据已经生成
		 */
		Elements elements1=element.getElementsByTag("font");
		
		if(flag==2){
			try {
				SendEmail.sendRemind(ownReport.getEmail(), ownReport.getUsername());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * 如果判断数据存在了就不会填写
		 */
		if(elements1.hasAttr("color")==true){
		//设置新的日期
		Element element1=doc.getElementById("date1[1]");
		String date1=element1.attr("value");
		ownReport.setWdate1(date1);
		//设置出勤地点，判断周末和工作日
		Date date=new Date();
		if(WeekedHoliday.isWeeked(date)==true){
			ownReport.setWplace("jb");
		}else{
			ownReport.setWplace("gc");
		}
		/**
		 * 保存数据到表中
		 */
		OwnReportDetail ownReportDetail=new OwnReportDetail();
		OwnReportDaoDetailImpl reportdaodetail=new OwnReportDaoDetailImpl();
	 	SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date datenew=new Date();
    	//设置变量
    	ownReportDetail.setUsername(ownReport.getUsername());
    	ownReportDetail.setBtime(format2.format(datenew));
    	ownReportDetail.setWtime(ownReport.getWtime());
    	ownReportDetail.setWdesc(ownReport.getWdesc());
    	ownReportDetail.setWlog(ownReport.getWlog());
    	ownReportDetail.setWtask(ownReport.getWtask());
    	reportdaodetail.addOwnReportDetail(ownReportDetail);
		
    	/**
		 * 解析填写表单自动写日报
		 * 
		 */
		String actionUrl =website.getFormurl();
		PostMethod postMethodForm = new EncodePostMethod(actionUrl);
		NameValuePair[] dataForm = { new NameValuePair("repid[1]", ownReport.getWrepid()), 
									new NameValuePair("date1[1]", ownReport.getWdate1()),
									new NameValuePair("time1[1]", ownReport.getBtime()),
									new NameValuePair("wtime[1]", ownReport.getWtime()),
									new NameValuePair("task_type[1]", ownReport.getWtask()),
									new NameValuePair("busy[1]", ownReport.getWbusy()),
									new NameValuePair("smile[1]", ownReport.getWstat()), 
									new NameValuePair("place[1]", ownReport.getWplace()),
									new NameValuePair("worktdesc[1]", ownReport.getWdesc()),
									new NameValuePair("taskid[1]",ownReport.getWtask()),
									new NameValuePair("log[1]", ownReport.getWlog()),
									new NameValuePair("arch[1]",ownReport.getWsum()),
									new NameValuePair("progress[1]", ownReport.getWprog()), 
									new NameValuePair("amount[1]", ownReport.getWamount()),
									new NameValuePair("unit_type[1]", ownReport.getWunit()),
									new NameValuePair("plan[1]", ownReport.getWplan()),
									new NameValuePair("question[1]", ownReport.getWquestion()),
									new NameValuePair("save", "保存") };
		postMethodForm.setRequestBody(dataForm);
		
		postMethodForm.setRequestHeader("cookie", tmpcookies.toString());
		try {
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			int stat = httpClient.executeMethod(postMethodForm);
			if (stat == STATUS) {
				System.out.println("save success");
			} else {
				System.out.println("save error!");
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}


/**
 * 获取用户数据列表	
 */
	public static List<String> getUserMonthReport(OwnReport ownReport,WebSite website,String time) {
		/**
		 * 模拟登录开始
		 */
		HttpClient httpClient = new HttpClient();              //每次登录都创建一个新的对象
		StringBuffer tmpcookies = new StringBuffer();
		String loginUrl = website.getLoginurl();
		
		PostMethod postMethod = new PostMethod(loginUrl);
		
		NameValuePair[] data = { new NameValuePair("uname", ownReport.getUsername()), new NameValuePair("pass", ownReport.getPassword()),
				new NameValuePair("xoops_redirect", "/in/"), new NameValuePair("op", "login") };
		
		postMethod.setRequestBody(data);
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		try {
			httpClient.executeMethod(postMethod);
			
			Cookie[] cookies = httpClient.getState().getCookies();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
			}	
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String listUrl = website.getListurl();
		listUrl=listUrl+"?time1="+time;
		GetMethod getMethod = new GetMethod(listUrl);
		System.out.println(listUrl);
		getMethod.setRequestHeader("cookie", tmpcookies.toString());
		getMethod.setRequestHeader("Referer", website.getReferer());
		getMethod.setRequestHeader("User-Agent",website.getAgent());
		getMethod.setRequestHeader("Content-Type", website.getContenttype());
		getMethod.setRequestHeader("Accept-Language", website.getLanguage());
		getMethod.setRequestHeader("Accept-Encoding", website.getEncoding());
		getMethod.setRequestHeader("Connection", website.getConnection());
		getMethod.setRequestHeader("Host", website.getHost());
		String htmltext = null;
		try {
			httpClient.executeMethod(getMethod);
			htmltext = getMethod.getResponseBodyAsString();
			//System.out.println(htmltext);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * 解析当前用户上月的日报数据
		 * 
		 */
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		List<String> list=new ArrayList<String>();
		Document doc=Jsoup.parse(htmltext); 
		Elements elements=doc.getElementsByClass("outer");
		Element element=elements.get(0);
		Elements e1=element.getElementsByTag("tr");
		for(int i=2;i<e1.size()-2;i++){
			Elements e2=e1.get(i).select("td");			
		    String value=e2.get(0).text();
		    value=value.substring(0, value.indexOf("("));
		    value=value.replace("-", "/");
	        String year= format.format(date);
		    value=year+"/"+value;
		    value=formatUserDate(value);		   		    	
		    list.add(value);
		  }
		
		return list;
		}


	/**
	 * 清洗获取的数据
	 * @param value
	 * @return
	 */
	public static String formatUserDate(String value){
		if(Integer.parseInt(value.substring(5,value.lastIndexOf("/")))<10){
			  value=value.substring(0,5)+value.substring(6);   			
		   }
		if(Integer.parseInt(value.substring(value.lastIndexOf("/")+1))<10){
			  value=value.substring(0,value.lastIndexOf("/")+1)+value.substring(value.lastIndexOf("/")+2);   
		   }
		return value;
	}
}   
	