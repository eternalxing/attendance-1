package com.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.dao.TimeTableDao;
import com.work.entity.Report;
import com.work.entity.TimeTable;
import com.work.util.Page;
@Service
public class TimeTableService {
	
	@Autowired private TimeTableDao timetabledao;
	public List<TimeTable> queryTimeTable(String username){
		 return null;
	 }
	public List<TimeTable> queryTimeTable(Page page){
		return timetabledao.queryTimeTable(page);
	}
	//��ѯ�û����¼�¼����
	public int findCurrentAllCountByUser(String username){
		return timetabledao.findCurrentAllCountByUser(username);
	}
	public int findCurrentAllCountByUsername(String username) {
		return timetabledao.findCurrentAllCountByUsername(username);
	}
	//��ѯ�û����¼�¼
		public List<TimeTable> queryUserTimeTable(String username,Page page){
			return timetabledao.queryUserTimeTable(username, page);
		}
	//���������û�����ѯ
	public List<TimeTable> queryTimeTable(String username,String start,Page page){
		return timetabledao.queryTimeTable(username, start, page);
	}
	//��ѯ�û�������¼����
	public int findAllCountByUser(String username,String start) {
		return timetabledao.findAllCountByUser(username,start);
	}
	public int findAllCount() {
		return timetabledao.findAllCount();
	}
	//�жϴ��Ƿ����
	public String isOverTimeTable(String ip){
		return timetabledao.isOverTimeTable(ip);
	}
	public String isHasTimeTable(String ip){
		
		String flag= timetabledao.isHasTimeTable(ip);
		
		return flag;
	}
	public void addTimeTable(TimeTable timetable){
		timetabledao.addTimeTable(timetable);
	 }
	 public void startwork(String ip){
		 timetabledao.startwork(ip);
	 }
	 public void endwork(String ip) {
		 timetabledao.endwork(ip);
	 }
	 public void updateTimeTable(TimeTable table){
		 timetabledao.updateTimeTable(table);
	 }
	 public void deleteTimeTable(int id){
		 timetabledao.deleteTimeTable(id);
	 }
	 public int findAllCountByLate(String username,String late) {
		return  timetabledao.findAllCountByLate(username, late);
	 }
	 public int findAllCountByEarly(String username,String late) {
	    return  timetabledao.findAllCountByEarly(username, late);

	 }
	 public void createUser(String username,String date){
		 timetabledao.createUser(username, date);
	 }
	 public String deleteData(){
	    return	 timetabledao.deleteData();
	 }
	 //����ɾ��
	 public void batchDeleteTimeTable(String[] id) {
		 timetabledao.batchDeleteTimeTable(id);
	 }
	//�����û�����ÿһ����ϰ��ʱ����°��ʱ�� ����ͼ����ʾ
		public Report getReport(String username){
			return timetabledao.getReport(username);
		}
		/**
		 * ����չʾ����չʾÿ���û������ϰ����
		 * @param null
		 * @return
		 */
		//�����û��������д򿨼�¼ ����ͼ����ʾ
		public List<Report> getAllUserReport(){
			return timetabledao.getAllUserReport();
		}
}
