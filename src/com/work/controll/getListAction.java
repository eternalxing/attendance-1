package com.work.controll;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.work.entity.Employee;
import com.work.entity.TimeTable;
import com.work.service.EmployeeService;
import com.work.service.OwnReportService;
import com.work.service.TimeTableService;
import com.work.util.Page;
import com.work.util.PageUtil;
import com.work.util.SendEmail;

@Controller
public class getListAction {
	//��¼ϵͳ������
	@Autowired TimeTableService timetableservice;
	@Autowired EmployeeService employeeservice;
	@RequestMapping("getList.do")
	public String login(HttpServletRequest request, Map<Object,String> model) {

		String currentPage=request.getParameter("currentPage");
		int currentpage=0;
		if(currentPage==null ||"".equals(currentPage)){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(currentPage);
		}

		int totalCount=timetableservice.findAllCount();
		Page page=PageUtil.createPage(10, currentpage, totalCount);
		List<TimeTable> list=timetableservice.queryTimeTable(page);
		request.setAttribute("List", list);
		request.setAttribute("page", page);

		return "WEB-INF/admin/manage"; 
	}
	//����Ա��ҳ��ʾ
	@RequestMapping("main.do")
	public String main(HttpServletRequest request, Map<Object,String> model) {
		int admincount=timetableservice.findAllCount();
		request.setAttribute("admincount", admincount);
		return "WEB-INF/admin/main"; 	
	}
	//�û���ҳ
	@RequestMapping("usermain.do")
	public String usermain(HttpServletRequest request, Map<Object,String> model) {
		String username=(String) request.getSession().getAttribute("username");
		int usercount=timetableservice.findCurrentAllCountByUser(username);
		request.setAttribute("usercount", usercount);
		
		return "WEB-INF/user/usermain"; 	
	}
	//�û���Ϣ
	@RequestMapping("getUserMassage.do")
	public String userMassage(HttpServletRequest request, Map<Object,String> model){
		return "WEB-INF/user/userMassage";
	}
	//�ʼ�����
	@RequestMapping("sendMail.do")
	public String sendmail(HttpServletRequest request, Map<Object,String> model) {
		List<Employee> list=employeeservice.queryEmail();
		for(int i=0;i<list.size();i++){
			String username=list.get(i).getUsername();
			String email=list.get(i).getEmail();
			SendEmail.sendMail(email, username);
			System.out.println("��"+(i+1)+"���ʼ����ͳɹ������͸��û�"+username);
		}
		return "redirect:getList.do";	
	}
	//��ȡ�û����¼�¼
	@RequestMapping("getUserList.do")
	public String getUserList(HttpServletRequest request, Map<Object,String> model) {
		String currentPage=request.getParameter("currentPage");
		String username=(String) request.getSession().getAttribute("username");
        
		int currentpage=0;
		if(currentPage==null ||"".equals(currentPage)){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(currentPage);
		}
		int totalCount=timetableservice.findCurrentAllCountByUser(username);
		Page page=PageUtil.createPage(10, currentpage, totalCount);
		List<TimeTable> list=timetableservice.queryUserTimeTable(username, page);
		//��ȡȷ����Ա��û��ȷ����Ա
		List<String> sure=employeeservice.queryEmployeeSure();
		List<Employee> unsure=employeeservice.queryEmployeeUnSure();
		request.setAttribute("List", list);
		request.setAttribute("page", page);
		request.setAttribute("sure", sure);
		request.setAttribute("unsure", unsure);
		return "WEB-INF/user/user"; 	
	}
	
	//������ѯ�û�������ʼʱ�䣬����ʱ��
//	@RequestMapping("query.do")
//	public String query(HttpServletRequest request){
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String currentPage=request.getParameter("currentPage");
//		String username=request.getParameter("username");
//		String start_time=request.getParameter("starttime");
//		String end_time=request.getParameter("endtime");
//		int currentpage=0;
//		if(currentPage==null ||"".equals(currentPage)){
//			currentpage=1;
//		}else{
//			currentpage=Integer.parseInt(currentPage);
//		}
//		
//		int totalCount=timetableservice.findAllCountByUser(username, start_time, end_time);
//		Page page=PageUtil.createPage(10, currentpage, totalCount);
//		List<TimeTable> list=timetableservice.queryTimeTable(username, start_time, end_time, page);
//		request.setAttribute("List", list);
//		request.setAttribute("page", page);
//
//		return "WEB-INF/admin/query"; 
//		
//		
//	}
	//���ӿ���
	@RequestMapping("add.do")
	public String add(HttpServletRequest request, Map<Object,String> model) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name=request.getParameter("username");
		String date=request.getParameter("date");
		String start=request.getParameter("start-time");
		String end=request.getParameter("end-time");
		String week=request.getParameter("week");
		String holiday=request.getParameter("holi");
		String explain=request.getParameter("explain");
		TimeTable table = new TimeTable();
		table.setUsername(name);
		table.setDate(date);
		table.setStarttime(start);
		table.setEndtime(end);
		if(week.equals("1")){
        	table.setWeekend("��");
        }else{
        	table.setWeekend("");
        }
		if(holiday.equals("1")){
			table.setHoliday("��");
		}else{
			table.setHoliday("");
		}

		table.setExplain(explain);
		timetableservice.addTimeTable(table);
	 String username=(String) request.getSession().getAttribute("username");
		if(employeeservice.role(username).equals("1")){
			return "redirect:getList.do"; 
		}else{
			return "redirect:getUserList.do"; 
		}
			
	}
	//�༭����
	@RequestMapping("update.do")
	public String edit(HttpServletRequest request, Map<Object,String> model) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currentPage=request.getParameter("currentPage");
		int currentpage=0;
		if(currentPage==null ||"".equals(currentPage)){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(currentPage);
		}
		String ID=request.getParameter("id");
		int id=Integer.parseInt(ID);
		String name=request.getParameter("username");
		String date=request.getParameter("date");
		String start=request.getParameter("starttime");
		String end=request.getParameter("endtime");
		String week=request.getParameter("week");
		String holiday=request.getParameter("holi");
		String explain=request.getParameter("explain");
        
		TimeTable table = new TimeTable();
		table.setId(id);
		table.setUsername(name);
		table.setDate(date);
		table.setStarttime(start);
		table.setEndtime(end);
        if(week.equals("1")){
        	table.setWeekend("��");
        }else{
        	table.setWeekend("");
        }
		if(holiday.equals("1")){
			table.setHoliday("��");
		}else{
			table.setHoliday("");
		}
		
		table.setExplain(explain);
		timetableservice.updateTimeTable(table);
		String username=(String) request.getSession().getAttribute("username");
		if(employeeservice.role(username).equals("1")){
			return "redirect:getList.do?currentPage="+currentpage; 
		}else{
			return "redirect:getUserList.do?currentPage="+currentpage; 
		} 	
	}
	//����ɾ�����ڼ�¼
	@RequestMapping("batchdelete.do")
	public String batchDelete(HttpServletRequest request, Map<Object,String> model) {
		String currentPage=request.getParameter("currentPage");
		int currentpage=0;
		if(currentPage==null ||"".equals(currentPage)){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(currentPage);
		}
        String allid=request.getParameter("allid");
        String[] id=allid.split(",");
		timetableservice.batchDeleteTimeTable(id);
		String username=(String) request.getSession().getAttribute("username");
		if(employeeservice.role(username).equals("1")){
			return "redirect:getList.do?currentPage="+currentpage; 
		}else{
			return "redirect:getUserList.do?currentPage="+currentpage; 
		} 	
	}	
	//ɾ������
	@RequestMapping("delete.do")
	public String delete(HttpServletRequest request, Map<Object,String> model) {
		String currentPage=request.getParameter("currentPage");
		int currentpage=0;
		if(currentPage==null ||"".equals(currentPage)){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(currentPage);
		}
        String ID=request.getParameter("id");
        int id=Integer.parseInt(ID);
		timetableservice.deleteTimeTable(id);
		String username=(String) request.getSession().getAttribute("username");
		if(employeeservice.role(username).equals("1")){
			return "redirect:getList.do?currentPage="+currentpage; 
		}else{
			return "redirect:getUserList.do?currentPage="+currentpage; 
		} 	
	}
	//һ�������û���¼
	@RequestMapping("createUser.do")
	public String createUser(HttpServletRequest request){
		String createusername=request.getParameter("username");
		String date=request.getParameter("date");
		timetableservice.createUser(createusername, date);
		String username=(String) request.getSession().getAttribute("username");
		request.setAttribute("info", "�����ɹ�");
		if(employeeservice.role(username).equals("1")){
			return "redirect:getList.do"; 
		}else{
			return "redirect:getUserList.do"; 
		} 	
		
	}
	//һ�����������û���¼
	@RequestMapping("createAllUser.do")
	public String createAllUser(HttpServletRequest request){
		String date=request.getParameter("date");
		String username=(String) request.getSession().getAttribute("username");
	List<Employee> userlist=employeeservice.queryIsAutoCreate();
	if(userlist.size()!=0){
		for(int i=0;i<userlist.size();i++){
			System.out.println(userlist.get(i).getUsername());
			timetableservice.createUser(userlist.get(i).getUsername(), date);
		}
		request.setAttribute("info", "�����ɹ�");
		
	}else{
		request.setAttribute("info", "���û���Ҫ�Զ�����");
	}
		if(employeeservice.role(username).equals("1")){
			return "redirect:getList.do"; 
		}else{
			return "redirect:getUserList.do"; 
		} 	
	}
	//�������
	@RequestMapping("deleteData.do")
	public String deleteData(){
		employeeservice.modifyUserSureToZero();
		return timetableservice.deleteData();
	}
}
