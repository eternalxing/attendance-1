package com.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.dao.EmployeeDao;
import com.work.entity.Employee;
import com.work.util.Page;
@Service
public class EmployeeService {
	
	@Autowired private EmployeeDao employeedao;
	public String login(String username,String password){
		return employeedao.login(username, password);
	}
	public List<Employee> queryEmail(){
		return employeedao.queryEmail();
	}
	public String role(String username){
		return employeedao.role(username);
	}
	public Employee queryInfo(String userid){
		return employeedao.queryInfo(userid);
	}
	 //��ѯ�û�ip�Ƿ�ע��
	public String getUserIp(String ip){
		return employeedao.getUserIp(ip);
	}
	//�Զ����ɿ���
	public List<Employee> queryIsAutoCreate(){
		return employeedao.queryIsAutoCreate();
	}
	//�����û�ͷ������
	public void imagePath(String userid,String imagename){
		employeedao.imagePath(userid, imagename);
	}
	//��ѯ�����û�����
		public int findAllCount( ){
			return employeedao.findAllCount();
	}
		//�����û�
		public String AddEmployee(Employee employee){
			return employeedao.AddEmployee(employee);
	}
		//��ѯ�û�
		public List<Employee> queryEmployee(Page page){
			return employeedao.queryEmployee(page);
	}
		//�����û���Ϣ
		public void updateEmployee(Employee employee){
			 employeedao.updateEmployee(employee);
	}
		//ɾ���û���Ϣ
		public void deleteEmployee(int id){
			 employeedao.deleteEmployee(id);
	}
		//�޸�����
		public int modifyPass(String email,String password){
			return employeedao.modifyPass(email, password);
		}
		//��ѯ�û������Ƿ�ע��
		public String getUserEmail(String email){
			return employeedao.getUserEmail(email);
		}
		//��ѯ�û��Ƿ����ߺ�����
		public   List<Employee> queryEmployeeOnline(){
			return employeedao.queryEmployeeOnline();
		}
		//�޸��û�״̬(���ߺ�����)
		public void modifyOnline(String userid,int online){
			employeedao.modifyOnline(userid, online);
		}
		//�޸��û�ip
		public void modifyUserIp(String ip,String userid){
			employeedao.modifyUserIp(ip, userid);
		}
		public void modifyUserSure(String userid){
			employeedao.modifyUserSure(userid);
		}
		//�޸��û�ȷ��״̬����
		public void modifyUserSureToZero(){
			employeedao.modifyUserSureToZero();
		}
		//��ѯȷ�ϵ���Ա
		public   List<String> queryEmployeeSure(){
			return employeedao.queryEmployeeSure();
		}
		//��ѯû��ȷ�ϵ���Ա
		public   List<Employee> queryEmployeeUnSure(){
			return employeedao.queryEmployeeUnSure();
		}
}
