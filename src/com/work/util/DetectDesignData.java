package com.work.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HeaderIterator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
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
	 * ״̬����
	 */
	final private static int STATUS=200;	
  
   
	public static void imitateLogin(OwnReport ownReport,WebSite website) {
		/**
		 * ģ���¼��ʼ
		 */
		HttpClient httpClient = new HttpClient();              //ÿ�ε�¼������һ���µĶ���
		StringBuffer tmpcookies = new StringBuffer();
		String loginUrl = website.getLoginurl();
		
		
		PostMethod postMethod = new EncodePostMethod(loginUrl);
		
		
		byte[] b = null;
		try {
			b = BASE64.decryptBASE64(ownReport.getPassword());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String pass=new String(b);
		System.out.println(ownReport.getUsername()+"  "+pass);
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
		 * ��ȡ����дҳ�濪ʼ
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
		 * ������ҳ���� ����δд����
		 * ���������δд�����ѷ��ʼ�
		 */
		Document doc=Jsoup.parse(htmltext); 
		Element element=doc.getElementById("content");
		Elements elements=element.getElementsByTag("a");
		String mainValue=elements.text();
		char[] c=mainValue.toCharArray();
		int flag=0;
		for(int i=0;i<c.length;i++){
			if(c[i]=='��'){
			flag++;
			}
		}
		/**
		 * ��ѯ�û������Ѿ�����
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
		 * ����ж����ݴ����˾Ͳ�����д
		 */
		if(elements1.hasAttr("color")==true){
		//�����µ�����
		Element element1=doc.getElementById("date1[1]");
		String date1=element1.attr("value");
		ownReport.setWdate1(date1);
		//���ó��ڵص㣬�ж���ĩ�͹�����
		Date date=new Date();
		if(WeekedHoliday.isWeeked(date)==true){
			ownReport.setWplace("jb");
		}else{
			ownReport.setWplace("gc");
		}
		/**
		 * �������ݵ�����
		 */
		OwnReportDetail ownReportDetail=new OwnReportDetail();
		OwnReportDaoDetailImpl reportdaodetail=new OwnReportDaoDetailImpl();
	 	SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date datenew=new Date();
    	//���ñ���
    	ownReportDetail.setUsername(ownReport.getUsername());
    	ownReportDetail.setBtime(format2.format(datenew));
    	ownReportDetail.setWtime(ownReport.getWtime());
    	ownReportDetail.setWdesc(ownReport.getWdesc());
    	ownReportDetail.setWlog(ownReport.getWlog());
    	ownReportDetail.setWtask(ownReport.getWtask());
    	reportdaodetail.addOwnReportDetail(ownReportDetail);
		
    	/**
		 * ������д���Զ�д�ձ�
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
									new NameValuePair("save", "����") };
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
 * ��ȡ�û������б�	
 * 
 * @throws HttpException 
 */
	@SuppressWarnings("deprecation")
	public static List<String> getUserMonthReport(OwnReport ownReport,WebSite website,String time) throws HttpException, IOException {
	//��ȡhttpclient����
	CloseableHttpClient httpClient=	HttpClients.custom().setDefaultCookieStore(new BasicCookieStore()).setRedirectStrategy(new LaxRedirectStrategy()).build();
	//���ݿ��ȡ��¼��ҳ����
	String indexUrl = website.getListurl();	
	//���ݿ��ȡ��¼����
	String loginUrl = website.getLoginurl();	
	//���ݿ��ȡ��¼����
	String loginName = ownReport.getUsername();
	//���ݿ��ȡ��¼����
	String loginPass = ownReport.getPassword();
	//���ݿ��ȡ�б�����
	String listUrl = website.getReferer();
	//����������ܹ���
	String newpass=null;
	try {
	byte[] b=BASE64.decryptBASE64(loginPass);
	 newpass=new String(b);
	} catch (IOException e3) {
		// TODO Auto-generated catch block
		e3.printStackTrace();
	}	
    //������ҳ����ȡcookies,������httpclient
	HttpPost httpPost=new HttpPost(indexUrl);	
	httpClient.execute(httpPost);	
	//��ʼģ���¼	
	HttpUriRequest login=RequestBuilder.post().setUri(loginUrl)
			.addParameter("uname",loginName )
            .addParameter("pass", newpass)
            .addParameter("xoops_redirect", "/in/")
            .addParameter("op", "login")
            .build();	
	CloseableHttpResponse response =httpClient.execute(login);
	//String content = EntityUtils.toString(response.getEntity());
    EntityUtils.consume(response.getEntity());
    HeaderIterator redirect = response.headerIterator("location");
    while (redirect.hasNext()) {
        // ʹ��get���󣬷��ʵ�½���ҳ��
        HttpGet getMethod = new HttpGet(redirect.next().toString());
        CloseableHttpResponse response2 = httpClient.execute(getMethod);
    }
	//��ȡ�����б�
	listUrl=listUrl+"?time1="+time;
	 HttpGet getList=new HttpGet(listUrl);
     CloseableHttpResponse response4= httpClient.execute(getList);
     String htmltext = null;
     htmltext=EntityUtils.toString(response4.getEntity(),"UTF-8");	
		/**
		 * ������ǰ�û����µ��ձ�����
		 * 
		 */
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); 
        if((calendar.MONTH-2)==0){
        	calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) - 1);
        }
        Date upmonth = calendar.getTime();
		String year=format.format(upmonth);
         System.out.println(year);
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
		    value=year+"/"+value;
		    value=formatUserDate(value);		   		    	
		    list.add(value);
		  }
		
		return list;
		}


	/**
	 * ��ϴ��ȡ������
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
	