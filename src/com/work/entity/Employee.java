package com.work.entity;

public class Employee {
	private int id;           //Ա�����
	private String ip;        //ip��ַ
	private String username;  //����
	private String password;  //����
	private String userid;    //�û�ID
	private int role;         //��ɫ
	private int status;       //״̬
	private String phone;     //�ֻ���
	private String email;     //����
	private int isswitch;     //�����ʼ����� 1����
	private int isflag;       //���ɹ������� 1����
	private String image;     //�û�ͷ������
	private int online;      //�ж��Ƿ�����
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public int getIsswitch() {
		return isswitch;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setIsswitch(int isswitch) {
		this.isswitch = isswitch;
	}
	public int getIsflag() {
		return isflag;
	}
	public void setIsflag(int isflag) {
		this.isflag = isflag;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
