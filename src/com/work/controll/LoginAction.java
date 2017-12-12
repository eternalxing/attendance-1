package com.work.controll;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONArray;
import com.work.entity.Employee;
import com.work.entity.Notice;
import com.work.entity.OperationLog;
import com.work.entity.Report;
import com.work.service.EmployeeService;
import com.work.service.NoticeService;
import com.work.service.OperationService;
import com.work.service.TimeTableService;

@Controller
public class LoginAction {
	//��¼ϵͳ������
	@Autowired EmployeeService employeeservice;
	@Autowired TimeTableService timetableservice;
	@Autowired NoticeService noticeservice;
	@Autowired OperationService operationservice;
	@RequestMapping("login.do")
	public String login(HttpServletRequest request,HttpServletResponse response,String username, String password, Map<Object,String> model) {
   
		if (employeeservice.login(username, password).equals("YES")) {
			//System.out.println(username+" :"+"login success");
			String ip=request.getRemoteAddr();			
			//����Ա��¼����
			int admincount=timetableservice.findAllCount();
			//����Ա�ٵ�����
			//int admincountlate=timetableservice.findAllCountByLate(username, "2");
			//����Ա��������
			//int admincountearly=timetableservice.findAllCountByEarly(username, "2");
			//�û�����
			int usercount=timetableservice.findCurrentAllCountByUser(username);
			//�û��ٵ�����
			//int usercountlate=timetableservice.findAllCountByLate(username, "1");
			//�û���������
			//int usercountearly=timetableservice.findAllCountByEarly(username, "1");
			//���ӵ�¼��־
			OperationLog log=new OperationLog();
			log.setType(1);//����1Ϊ��¼�ɹ�  2Ϊ��¼ʧ�� 3Ϊ�˺�δ����
			log.setContent("�˺ŵ�¼�ɹ�");
			log.setIp(ip);
			log.setUsername(username);
			String time=formatdate();
			log.setTime(time);
			operationservice.addLog(log);

			Employee employee=employeeservice.queryInfo(username);
			//��¼��վ�޸��û�ipʵʱ����
			employeeservice.modifyUserIp(ip, username);
			List<Notice> list=noticeservice.queryNotice();
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("employee", employee);
			request.getSession().setAttribute("imagepath", employee.getImage());
			request.getSession().setAttribute("ip", ip);
			if(employeeservice.role(username).equals("1")){
				//request.getSession().setAttribute("admincount", admincount);
				request.setAttribute("admincount", admincount);
				//request.getSession().setAttribute("admincountlate", admincountlate);
				//request.getSession().setAttribute("admincountearly", admincountearly);
				request.getSession().setAttribute("list", list);
				return "forward:main.do";
				
			}else{
				//request.getSession().setAttribute("usercount", usercount);
				request.setAttribute("usercount", usercount);
				request.getSession().setAttribute("list", list);
				//request.getSession().setAttribute("usercountlate", usercountlate);
				//request.getSession().setAttribute("usercountearly", usercountearly);
				return "forward:usermain.do";
			}
			

		} else if(employeeservice.login(username, password).equals("ineffect")){
			String ip=request.getRemoteAddr();
			OperationLog log=new OperationLog();
			log.setType(2);//����1Ϊ��¼�ɹ�  2Ϊ��¼ʧ��
			log.setContent("�˺ż���ʧ��");
			log.setIp(ip);
			log.setUsername(username);
			String time=formatdate();
			log.setTime(time);
			operationservice.addLog(log);	
			request.setAttribute("errorinfo", "�˺�δ���� ����ϵ����Ա");
			return "forward:login.jsp";
		}
		
		else {
			String ip=request.getRemoteAddr();
			OperationLog log=new OperationLog();
			log.setType(2);//����1Ϊ��¼�ɹ�  2Ϊ��¼ʧ��
			log.setContent("�û��������������");
			log.setIp(ip);
			log.setUsername(username);
			String time=formatdate();
			log.setTime(time);
			operationservice.addLog(log);		
			request.setAttribute("errorinfo", "�û������������");
			return "forward:login.jsp";

		}
	}
	//�˳���¼
	@RequestMapping("loginout.do")
		public String loginout(HttpServletRequest request,HttpServletResponse response){
		//���session
		HttpSession session=request.getSession();
		response.setHeader("Cache-Control","no-cache");
    	response.setHeader("Cache-Control","no-store"); 
    	response.setDateHeader("Expires", 0); 
    	response.setHeader("Pragma","no-cache");
    	session.removeAttribute("username");
    	session.removeAttribute("employee");
		session.invalidate();
		return "redirect:login.jsp";
	}
	public String formatdate(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String currentdate=format.format(date);
		return currentdate;
	}
	//��ȡ��������
	
	@RequestMapping("getReport.do")
	public  void getreport(HttpServletRequest request,HttpServletResponse response){
		String username=(String)request.getSession().getAttribute("username");
		Report report=timetableservice.getReport(username);
		List<Object> list=new ArrayList<Object>();
		list.add(report.getDate());
		list.add(report.getStart());
		list.add(report.getEnd());		
		//listתjson
		JSONArray array=new JSONArray(list);
		try {
			response.getWriter().println(array);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�˺�ע��
	@RequestMapping("register.do")
	public  String registerCount(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username=request.getParameter("username");
		String userid=request.getParameter("userid");
		String tel=request.getParameter("tel");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		Employee employee=new Employee();
		employee.setUsername(username);
		employee.setPhone(tel);
		employee.setPassword(password);
		employee.setEmail(email);
		employee.setImage("headimage.jpg");//����Ĭ��ͷ��
		employee.setRole(0); //��ɫΪ�û�
		employee.setIsswitch(1);
		employee.setIsflag(0);
		employee.setIp("0.0.0.0");
		employee.setUserid(userid);
		employee.setStatus(1);//״̬����
		String flag=employeeservice.AddEmployee(employee);
		if(flag.equals("1")){
			request.setAttribute("errorinfo", "ע��ɹ�");
			return "redirect:login.jsp";
			
		}else{
			request.setAttribute("errorinfo", "ע��ʧ��");
			return "redirect:register.jsp";
		}
	}
}
