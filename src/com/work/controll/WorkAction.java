package com.work.controll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.work.service.EmployeeService;
import com.work.service.OwnReportService;
import com.work.service.TimeTableService;

@Controller
public class WorkAction {
	@Autowired
	private TimeTableService timetableservice;
    @Autowired
    private EmployeeService employeeservice;
    @Autowired
    private OwnReportService ownReportService;
	@RequestMapping("work.do")
	public String addtime(HttpServletRequest request, Map<Object,String> model) {
		Date date = new Date();
		// ��ȡ����ip
		String ip = request.getRemoteAddr();
		if(employeeservice.getUserIp(ip).equals("1")){
			System.out.println(date + "-----" + ip + "��ʼ���ʷ�����");
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			String date_string1 = "08:30:00";
			String date_string2 = "12:00:00";
			String date_string3 = "14:30:00";
			String date_string4 = "20:00:00";
			String date_string5 = "17:30:00";
			// ����ʱ������
			Date date1 = null;
			Date date2 = null;
			Date date3 = null;
			Date date4 = null;
			Date date5 = null;
			try {
				date1 = format.parse(date_string1);
				date2 = format.parse(date_string2);
				date3 = format.parse(date_string3);
				date4 = format.parse(date_string4);
				date5 = format.parse(date_string5);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// ��ȡ��ǰʱ��
			String currentdate = format.format(date);
			Date currenttime = null;
			try {
				currenttime = format.parse(currentdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (currenttime.after(date1) && currenttime.before(date2)) {
				String flag = timetableservice.isHasTimeTable(ip);
				if (flag.equals("Y")) {
					model.put("flag", "�Ѿ��򿨳ɹ�,�벻Ҫ�ظ��򿨣�");
				} else {
					timetableservice.startwork(ip);
					model.put("flag", "�ϰ�򿨳ɹ�");
				}

			} else if (currenttime.after(date2) && currenttime.before(date3)) {
				model.put("flag", "��������Ϣʱ��");
			} else if (currenttime.after(date5) && currenttime.before(date4)) {
				if (timetableservice.isOverTimeTable(ip).equals("Yes")) {
					model.put("flag", "���մ������,����ǵð�ʱ�򿨰�");
				} else {
					timetableservice.endwork(ip);
					model.put("flag", "�°�򿨳ɹ�");
				}

			} else {
				model.put("flag", "��ʱ�β��ܴ��°�");
			}
			return "index";
		}else{
			model.put("flag", "��IPû��ע��,����ϵ����Ա���");
			return "index";
		}
		
	}
	@RequestMapping("online.do")
	public String online(){
		return "index";
	}
	//�жϵ�ǰ�û��Ƿ���ʹ��������޸ı�ʶ
	@RequestMapping("modifyflag.do")
	@ResponseBody
	public String modidyOwnFlag(HttpServletRequest request){
		String username=request.getParameter("username");
		ownReportService.modifyWflag(username);
		return "success";
	}

}
