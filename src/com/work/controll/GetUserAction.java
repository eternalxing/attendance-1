package com.work.controll;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.work.entity.Employee;
import com.work.service.EmployeeService;
import com.work.util.Page;
import com.work.util.PageUtil;
import com.work.util.SendEmail;
@Controller
public class GetUserAction {

	/*������־���󣬷���ʹ��
	 * private static Logger log=Logger.getLogger(GetUserAction.class);
	 * ͨ��log�������ò�ͬ����־�������磺log.info(),log.debug()��
	 * ������־�����Ҫ���Էſ�
	 */
	@Autowired EmployeeService employeeservice;
	@RequestMapping("getUser.do")
     public String getUserList(HttpServletRequest request , Map<Object,String> model){
		String currentPage=request.getParameter("currentPage");
		int currentpage=0;
		if(currentPage==null ||"".equals(currentPage)){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(currentPage);
		}
		//��ѯ��ǰע���û���
		int totalCount=employeeservice.findAllCount();
		Page page=PageUtil.createPage(10, currentpage, totalCount);
		List<Employee> list=employeeservice.queryEmployee(page);
		request.setAttribute("List", list);
		request.setAttribute("page", page);
	    return "WEB-INF/admin/usermanager";
   }
	@RequestMapping("addUser.do") 
	@ResponseBody
	public String addUser(HttpServletRequest request){
	    String ip = request.getParameter("ip");
		String username= request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String userid = request.getParameter("name");
		String ro = request.getParameter("ro");
		int role=Integer.parseInt(ro);
		String st = request.getParameter("zt");
		int status =Integer.parseInt(st);
		String phone = request.getParameter("tel");
		String email = request.getParameter("eml");
		String iss = request.getParameter("js");
		int isswitch=Integer.parseInt(iss);
		String isf = request.getParameter("zd");
		int isflag=Integer.parseInt(isf);
		Employee employee=new Employee();
		employee.setIp(ip);;
		employee.setUsername(username);
		employee.setPassword(pwd);
		employee.setUserid(userid);
		employee.setRole(role);
		employee.setStatus(status);
		employee.setPhone(phone);
		employee.setEmail(email);
		employee.setIsswitch(isswitch);
		employee.setIsflag(isflag);
	    String info = employeeservice.AddEmployee(employee);
	    return info;
	}
	
	
	@RequestMapping("updateUser.do")
	public String edit(HttpServletRequest request, Map<Object,String> model) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		String ID=request.getParameter("id");
		int id=Integer.parseInt(ID);
		String ip = request.getParameter("ip");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String userid=request.getParameter("userid");
		String ro=request.getParameter("role");
		int role=Integer.parseInt(ro);
		String st=request.getParameter("status");
		int status =Integer.parseInt(st);
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String iss=request.getParameter("isswitch");
		int isswitch=Integer.parseInt(iss);
		String isf=request.getParameter("isflag");
		int isflag=Integer.parseInt(isf);
		//����emloyee
		Employee employee = new Employee();
		employee.setId(id);
		employee.setIp(ip);;
		employee.setUsername(username);
		employee.setPassword(password);
		employee.setUserid(userid);
		employee.setRole(role);
		employee.setStatus(status);
		employee.setPhone(phone);
		employee.setEmail(email);
		employee.setIsswitch(isswitch);
		employee.setIsflag(isflag);
		employeeservice.updateEmployee(employee);
	    return "redirect:getUser.do"; 
	
	}
	//ɾ���û�
		@RequestMapping("deleteuser.do")
		public String delete(HttpServletRequest request, Map<Object,String> model) {
	        String ID=request.getParameter("id");
	        int id=Integer.parseInt(ID);
			employeeservice.deleteEmployee(id);
			return "redirect:getUser.do"; 
		}
	//�޸�����modify.do
		@RequestMapping("modify.do")
		public String modify(HttpServletRequest request, Map<Object,String> model) {
	        String email=request.getParameter("email");
	        String pass=request.getParameter("password");	        
			int i=employeeservice.modifyPass(email, pass);
			if(i==1){
				return "WEB-INF/user/modifysuccess";
				
			}else{
				return "redirect:modifypass.jsp"; 
			}
			
		}
	//���������֤��
		@RequestMapping("sendCode.do")
		@ResponseBody 
		public String sendCode(HttpServletRequest request, Map<Object,String> model) {
	       String flag="";
			String email=request.getParameter("email");
	       int code_new=(int) (Math.random()*1000000);
	       String code=String.valueOf(code_new);
	       request.getSession().setAttribute("code", code);
	       //����֤�����session ��
	     
	       //������ʱ����10���Ӻ�codeʧЧ
	       //showTimer(request);
	       
	       try{
		   SendEmail.sendCode(email, code);
		   flag="1";	
	       }catch(Exception e){
	    	   e.printStackTrace();
	       flag="0";
	       }
	       return flag+code;
		}
		//��������Ƿ�ע��
		@RequestMapping("checkEmail.do")
		@ResponseBody 
		public String checkEmail(HttpServletRequest request, Map<Object,String> model) {
	        String flag="";
			String email=request.getParameter("email");
			flag=employeeservice.getUserEmail(email);
		    return flag;
		}		
		//�û�ȷ��
		@RequestMapping("userSure.do")
		@ResponseBody 
		public String userSure(HttpServletRequest request) {
			 String userid=request.getParameter("username");
		     employeeservice.modifyUserSure(userid);
		     return "1";
				}	
		
	   
}
