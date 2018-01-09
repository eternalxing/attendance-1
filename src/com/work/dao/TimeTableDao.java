package com.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.work.entity.Report;
import com.work.entity.TimeTable;
import com.work.util.DBconnection;
import com.work.util.Page;

@Repository
public class TimeTableDao {
	//��ʽ������
	public String formatdate(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
		String currentdate=format.format(date);
		return currentdate;
	}
	
	//��ѯ�û��򿨼�¼
		public List<TimeTable> queryTimeTableByUserName(String username){
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			
			String findSQL = "select * from timetable " +
						" where username='"+username+"' order by  substr( substr(date,6),position('/',substr(date,6))+1)+0  asc";
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			List<TimeTable> list = new ArrayList<TimeTable>();
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				rs = pstmt.executeQuery();				//ִ�в�ѯ
				while(rs.next()) {
					TimeTable table = new TimeTable();
					table.setId(rs.getInt(1));
					table.setUsername(rs.getString(2));
					table.setDate(rs.getString(3));
					table.setStarttime(rs.getString(4));
					table.setEndtime(rs.getString(5));
					table.setWeekend(rs.getString(6));
					table.setHoliday(rs.getString(7));
					table.setExplain(rs.getString(8));
					list.add(table);//���
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBconnection.close(rs);								//�رս��������
				DBconnection.close(pstmt);							//�ر�Ԥ�������
				DBconnection.close(conn);							//�ر����Ӷ���
			}
			return list;
		}
	//��ѯ�û�ĳ���¼�¼���еĴ򿨼�¼
	public List<TimeTable> queryTimeTable(String username,String start,Page page){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String	sqlstring="";
		if(!start.equals("")){
		 sqlstring=" AND date like '"+start+"%'";
		}
		
		String findSQL = "select * from timetable " +
					" where username='"+username+"'"+sqlstring+" order by  substr( substr(date,6),position('/',substr(date,6))+1)+0 asc limit ?,?";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<TimeTable> list = new ArrayList<TimeTable>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setInt(1, page.getBeginIndex());		//��ѯ��ʼ��
			pstmt.setInt(2, page.getEveryPage());		//��ѯ��¼��
			rs = pstmt.executeQuery();				//ִ�в�ѯ
			while(rs.next()) {
				TimeTable table = new TimeTable();
				table.setId(rs.getInt(1));
				table.setUsername(rs.getString(2));
				table.setDate(rs.getString(3));
				table.setStarttime(rs.getString(4));
				table.setEndtime(rs.getString(5));
				table.setWeekend(rs.getString(6));
				table.setHoliday(rs.getString(7));
				table.setExplain(rs.getString(8));
				list.add(table);//���
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return list;
	}
	//��ѯiP��ַ��Ӧ�û���
	public String findNameByIp(String ip) {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select username from employee where ip='"+ip+"'";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		String name=null;
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();					//ִ�в�ѯ
			if(rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);						//�رս��������
			DBconnection.close(pstmt);					//�ر�Ԥ�������
			DBconnection.close(conn);					//�ر����Ӷ���
		}
		return name;
	}
	
	//��ѯ�û�������¼��
	public int findAllCountByUser(String username,String start) {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String sqlstring="";
		if(start!=""){
			 sqlstring=" AND date like '"+start+"%'";
			}
		String findSQL = "select count(*) from timetable where username='"+username+"'"+sqlstring+"";
		System.out.println("����"+findSQL);
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();					//ִ�в�ѯ
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);						//�رս��������
			DBconnection.close(pstmt);					//�ر�Ԥ�������
			DBconnection.close(conn);					//�ر����Ӷ���
		}
		return count;
	}
	//��ѯ�û�����������¼��
		public int findCurrentAllCountByUser(String username) {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���			
			String findSQL = "select count(*) from timetable where username=(select username from employee where userid='"+username+"')";
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			int count = 0;
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				rs = pstmt.executeQuery();					//ִ�в�ѯ
				if(rs.next()) {
					count = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBconnection.close(rs);						//�رս��������
				DBconnection.close(pstmt);					//�ر�Ԥ�������
				DBconnection.close(conn);					//�ر����Ӷ���
			}
			return count;
		}
		
		//��ѯ�û�����������¼��ͨ������
				public int findCurrentAllCountByUsername(String username) {
					// TODO Auto-generated method stub
					Connection conn = DBconnection.getConnection();	//������Ӷ���			
					String findSQL = "select count(*) from timetable where username='"+username+"'";
					PreparedStatement pstmt = null;					//����Ԥ�������
					ResultSet rs = null;
					int count = 0;
					try {
						pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
						rs = pstmt.executeQuery();					//ִ�в�ѯ
						if(rs.next()) {
							count = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally{
						DBconnection.close(rs);						//�رս��������
						DBconnection.close(pstmt);					//�ر�Ԥ�������
						DBconnection.close(conn);					//�ر����Ӷ���
					}
					return count;
				}
	//��ѯ���м�¼��
	public int findAllCount() {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select count(*) from timetable";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();					//ִ�в�ѯ
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);						//�رս��������
			DBconnection.close(pstmt);					//�ر�Ԥ�������
			DBconnection.close(conn);					//�ر����Ӷ���
		}
		return count;
	}
	
	public List<TimeTable> queryTimeTable(Page page){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select * from timetable " +
					"order by  substr( substr(date,6),position('/',substr(date,6))+1)+0 asc limit ?,?";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<TimeTable> list = new ArrayList<TimeTable>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setInt(1, page.getBeginIndex());		//��ѯ��ʼ��
			pstmt.setInt(2, page.getEveryPage());		//��ѯ��¼��
			rs = pstmt.executeQuery();				//ִ�в�ѯ
			while(rs.next()) {
				TimeTable table = new TimeTable();
				table.setId(rs.getInt(1));
				table.setUsername(rs.getString(2));
				table.setDate(rs.getString(3));
				table.setStarttime(rs.getString(4));
				table.setEndtime(rs.getString(5));
				table.setWeekend(rs.getString(6));
				table.setHoliday(rs.getString(7));
				table.setExplain(rs.getString(8));
				list.add(table);//���
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return list;
	}
	//��ѯ�û����¼�¼
	public List<TimeTable> queryUserTimeTable(String username,Page page){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select * from timetable " +
					" where username=(select username from employee where userid='"+username+"')  order by  substr( substr(date,6),position('/',substr(date,6))+1)+0 asc limit ?,?";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<TimeTable> list = new ArrayList<TimeTable>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setInt(1, page.getBeginIndex());		//��ѯ��ʼ��
			pstmt.setInt(2, page.getEveryPage());		//��ѯ��¼��
			rs = pstmt.executeQuery();				//ִ�в�ѯ
			while(rs.next()) {
				TimeTable table = new TimeTable();
				table.setId(rs.getInt(1));
				table.setUsername(rs.getString(2));
				table.setDate(rs.getString(3));
				table.setStarttime(rs.getString(4));
				table.setEndtime(rs.getString(5));
				table.setWeekend(rs.getString(6));
				table.setHoliday(rs.getString(7));
				table.setExplain(rs.getString(8));
				list.add(table);//���
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return list;
	}
	//�жϵ��մ��Ƿ��Ѿ����
	public String isOverTimeTable(String ip){
		SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
		Date date=new Date();
		String currentdate=format.format(date);
		Connection con=DBconnection.getConnection();
		String sql="select count(*) from timetable where username=(select username from employee where ip='"+ip+"') and date in ('"+currentdate+"') and starttime!='' and endtime!=''";		
		PreparedStatement prep=null;
		ResultSet result=null;
		String flag=null;
		try {
			prep=con.prepareStatement(sql);					
			result=prep.executeQuery();
			if(result.next()){
				if(result.getInt(1)>0){
					flag="Yes";
					
				}else{
					flag="No";
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconnection.close(con);
			DBconnection.close(prep);
		}
		return flag;
	}
	//��ѯ�����Ƿ��Ѿ��м�¼
	public String isHasTimeTable(String ip){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
		String currentdate=format.format(date);
		Connection con=DBconnection.getConnection();
		String sql="select count(*) from timetable where username=(select username from employee where ip='"+ip+"') and date in ('"+currentdate+"')";		
		PreparedStatement prep=null;
		ResultSet result=null;
		String flag=null;
		try {
			prep=con.prepareStatement(sql);					
			result=prep.executeQuery();
			if(result.next()){
				if(result.getInt(1)>0){
					flag="Y";
					
				}else{
					flag="N";
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconnection.close(con);
			DBconnection.close(prep);
		}
		return flag;
	}

    //��ʼ�ϰ��
	public void startwork(String ip) {
		Connection con = DBconnection.getConnection();
		String sql = "insert into timetable(username,date,starttime) values((select username from employee where ip='"
				+ ip + "'),?,?)";
		PreparedStatement prep = null;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
		SimpleDateFormat format1 = new SimpleDateFormat("H:mm:ss");
		String time = format.format(date);
		String start = format1.format(date);

		try {
			prep = con.prepareStatement(sql);

			prep.setString(1, time);
			prep.setString(2, start);

			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}
	}
    //�°��
	public void endwork(String ip) {
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
		SimpleDateFormat format1=new SimpleDateFormat("H:mm:ss");		
		String time=format.format(date);
		String endtime=format1.format(date);
		Connection con=DBconnection.getConnection();
		String sql="update timetable set endtime='"+endtime+"' where username=(select username from employee where ip='"+ip+"') and date in ('"+time+"')";
		String sql1 = "insert into timetable(username,date,starttime) values((select username from employee where ip='"
				+ ip + "'),?,?)";
		
		PreparedStatement prep=null;
		
		
		try {
			
			if( isHasTimeTable(ip).equals("N")){
				prep=con.prepareStatement(sql1);
				prep.setString(1, time);
				prep.setString(2, "8:56:12");
				prep.executeUpdate();
				prep=con.prepareStatement(sql);
				prep.executeUpdate();
			}else{
				 prep=con.prepareStatement(sql);
				 prep.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconnection.close(con);
			DBconnection.close(prep);
		}
	
	}
	//���Ӵ򿨼�¼
    public void addTimeTable(TimeTable timetable){
    	Connection con = DBconnection.getConnection();
		String sql = "insert into timetable(username,date,starttime,endtime,weekend,holiday,explain1) values(?,?,?,?,?,?,?)";
		
		PreparedStatement prep = null;		
		try {
			prep = con.prepareStatement(sql);
			prep.setString(1, timetable.getUsername());
			prep.setString(2, timetable.getDate());
			prep.setString(3, timetable.getStarttime());
			prep.setString(4, timetable.getEndtime());
			prep.setString(5, timetable.getWeekend());
			prep.setString(6, timetable.getHoliday());
			prep.setString(7, timetable.getExplain());
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}
    }
	//���¼�¼
	public void updateTimeTable(TimeTable timetable) {
		Connection con = DBconnection.getConnection();
		String sql = "update  timetable set username=?,date=?,starttime=?,endtime=?,weekend=?,holiday=?,explain1=?  where id="+timetable.getId()+"";
		PreparedStatement prep = null;		
		try {
			prep = con.prepareStatement(sql);

			prep.setString(1, timetable.getUsername());
			prep.setString(2, timetable.getDate());
			prep.setString(3, timetable.getStarttime());
			prep.setString(4, timetable.getEndtime());
			prep.setString(5, timetable.getWeekend());
			prep.setString(6, timetable.getHoliday());
			prep.setString(7, timetable.getExplain());
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}
	}
	
	//����ɾ����¼
		public void batchDeleteTimeTable(String[] id) {
			Connection con = DBconnection.getConnection();
			String sql="delete from timetable where id in(0"; 
			
			for(int i=0;i<id.length;i++) 
			{ 
			  sql+=","+id[i]; 
			} 
			sql+=")"; 						
			PreparedStatement prep = null;		
			try {
				prep = con.prepareStatement(sql);				
				prep.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBconnection.close(con);
				DBconnection.close(prep);
			}
		}
	
	
   //ɾ����¼
	public void deleteTimeTable(int id) {
		Connection con = DBconnection.getConnection();
		String sql = "delete from timetable where id=?";
		PreparedStatement prep = null;		
		try {
			prep = con.prepareStatement(sql);

			prep.setInt(1,id);
			
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}
	}
	public List<TimeTable> queryTimeTableByDate(String date){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select * from timetable where date like '"+date+"%'";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<TimeTable> list = new ArrayList<TimeTable>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();				//ִ�в�ѯ
			while(rs.next()) {
				TimeTable table = new TimeTable();
				table.setId(rs.getInt(1));
				table.setUsername(rs.getString(2));
				table.setDate(rs.getString(3));
				table.setStarttime(rs.getString(4));
				table.setEndtime(rs.getString(5));
				table.setWeekend(rs.getString(6));
				table.setHoliday(rs.getString(7));
				table.setExplain(rs.getString(8));
				list.add(table);//���
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return list;
	}
	//��ѯ�û��ٵ�����
	public int findAllCountByLate(String username,String late) {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL=null;
		if(late.equals("1")){
			 findSQL = "select count(*) from timetable where username=(select username from employee where userid='"+username+"') AND    str_to_date(starttime, '%h:%i:%s')>str_to_date('09:00:00', '%h:%i:%s')";
		}else{
			 findSQL = "select count(*) from timetable where  str_to_date(starttime, '%h:%i:%s')>str_to_date('09:00:00', '%h:%i:%s')";
		}
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();					//ִ�в�ѯ
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);						//�رս��������
			DBconnection.close(pstmt);					//�ر�Ԥ�������
			DBconnection.close(conn);					//�ر����Ӷ���
		}
		return count;
	}
	//��ѯ�û����˴���
	public int findAllCountByEarly(String username,String early) {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL=null;
			if(early.equals("1")){
				 findSQL = "select count(*) from timetable where username=(select username from employee where userid='"+username+"') AND    str_to_date(endtime, '%h:%i:%s')<str_to_date('18:00:00', '%h:%i:%s')  ";
			}else{
				 findSQL = "select count(*) from timetable where  str_to_date(endtime, '%h:%i:%s')<str_to_date('18:00:00', '%h:%i:%s') ";
			}
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			int count = 0;
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				rs = pstmt.executeQuery();					//ִ�в�ѯ
				if(rs.next()) {
					count = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBconnection.close(rs);						//�رս��������
				DBconnection.close(pstmt);					//�ر�Ԥ�������
				DBconnection.close(conn);					//�ر����Ӷ���
			}
			return count;
		}	

	/*һ�������û���¼,�÷����������������û���¼
	 * @param username date
	 * @return none
	 */
	public void createUser(String username,String date){
		Connection con = DBconnection.getConnection();
		String sql = "insert into timetable(username,date,starttime,endtime,weekend,holiday,explain1) values(?,?,?,?,?,?,?)";
		//��ȡ���¶�����
		int year=Integer.parseInt(date.substring(0,4));
		int month=Integer.parseInt(date.substring(5));
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.YEAR,year); 
		cal.set(Calendar.MONTH, month - 1);//Java�·ݴ�0��ʼ�� 
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		String str="";
		String str1="";//���ռӰ��ʶ
		PreparedStatement prep = null;		
		try {
			prep = con.prepareStatement(sql);
			for(int i=1;i<=dateOfMonth;i++){
				str1=getWeek(month,i);
				prep.setString(1, username);
				prep.setString(2, date+"/"+i);
				prep.setString(3, "8:"+String.valueOf((int)(Math.random()*29+26))+":"+String.valueOf((int)(Math.random()*20+32)));
				prep.setString(4, "18:"+String.valueOf((int)(Math.random()*44+10))+":"+String.valueOf((int)(Math.random()*37+10)));
				prep.setString(5, str1);
				prep.setString(6, str);
				prep.setString(7, str);
				prep.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}	
	}
	/* �÷�������ͬ���û�����
	 * @param username date
	 * @return none
	 */
	public void createUserReportBySynchro(String username,List<String> date){
		System.out.println();
		Connection con = DBconnection.getConnection();
		String sql = "insert into timetable(username,date,starttime,endtime,weekend,holiday,explain1) values(?,?,?,?,?,?,?)";
		String str="";
		String str1="";//���ռӰ��ʶ
		PreparedStatement prep = null;		
		try {
			prep = con.prepareStatement(sql);
			for(int i=0;i<date.size();i++){
				String tempDate=date.get(i);
	            int month=Integer.parseInt(tempDate.substring(5,tempDate.lastIndexOf("/")));
	 			int day=Integer.parseInt(tempDate.substring(tempDate.lastIndexOf("/")+1));
				str1=getWeek(month,day);
				prep.setString(1, username);
				prep.setString(2, date.get(i));
				prep.setString(3, "8:"+String.valueOf((int)(Math.random()*29+26))+":"+String.valueOf((int)(Math.random()*20+32)));
				prep.setString(4, "18:"+String.valueOf((int)(Math.random()*44+10))+":"+String.valueOf((int)(Math.random()*37+10)));
				prep.setString(5, str1);
				prep.setString(6, str);
				prep.setString(7, str);
				prep.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}	
	}
   //������ݿ��������ݣ����к���0
   public String deleteData(){
	   Connection conn = DBconnection.getConnection();	//������Ӷ���
		String deleteinfo=null;		
		String findSQL = "delete from timetable ";	
		//mysql���ݿ��﷨
		//String findSQL1="alter table timetable AUTO_INCREMENT=1";
		//h2���ݿ��﷨
		String findSQL1="ALTER sequence  SYSTEM_SEQUENCE_41649BB9_0259_40FC_BA91_222E3D82BF3A  RESTART WITH 1";
		PreparedStatement pstmt = null;					//����Ԥ�������
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ�������
			pstmt.executeUpdate();	
			pstmt = conn.prepareStatement(findSQL1);	
			pstmt.executeUpdate();
			deleteinfo="1";
		} catch (SQLException e) {
			e.printStackTrace();
			deleteinfo="0";
		} finally{
			DBconnection.close(pstmt);					//�ر�Ԥ�������
			DBconnection.close(conn);					//�ر����Ӷ���
		}
		return deleteinfo;
   }
 //�ж�ĳһ���Ƿ�����ĩ	
	public String getWeek(int month,int day){
		
		String str="";
		Calendar c=Calendar.getInstance();
		 if((c.MONTH-2)==0){
	        	c.set(Calendar.YEAR,c.get(Calendar.YEAR) - 1);
	        }
		c.set(Calendar.MONTH, month-1);	//�����·�
		c.set(Calendar.DAY_OF_MONTH,day);
		if(c.get(Calendar.DAY_OF_WEEK)==7){
			 str="��";
		}else if(c.get(Calendar.DAY_OF_WEEK)==1){
			 str="��";
		}else{
			 str="";
		}
		return str;
	}
	
	/**
	 * ����չʾ����
	 * @param username
	 * @return
	 */
	//�����û�����ÿһ����ϰ��ʱ����°��ʱ�� ����ͼ����ʾ
	public Report getReport(String username){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select date,starttime,endtime from timetable  where username=(select username from employee where userid=?) order by  substr( substr(date,6),position('/',substr(date,6))+1)+0 asc";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<String> datelist=new ArrayList<String>();
		List<String> startlist=new ArrayList<String>();
		List<String> endlist=new ArrayList<String>();
		Report report=new Report();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ	
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();				//ִ�в�ѯ
			String date=null;
			String start=null;
			String end=null;
			int index=0;
			int index1=0;
			int index2=0;
			
			while(rs.next()) {
				
				 date=rs.getString(1);
				 start=rs.getString(2);
				 end=rs.getString(3);
				 index=date.lastIndexOf("/");
			     index1=start.lastIndexOf(":");
				 index2=end.lastIndexOf(":"); 
				 datelist.add(date.substring(index+1)+"��");
				 startlist.add(start.substring(0, index1).replace(":", "."));
				 endlist.add(end.substring(0, index2).replace(":", "."));				
				 
			}
			report.setDate(datelist);
			report.setStart(startlist);
			report.setEnd(endlist);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return report;
	}
	
	/**
	 * ����չʾ����չʾÿ���û������ϰ����
	 * @param null
	 * @return
	 */
	//�����û��������д򿨼�¼ ����ͼ����ʾ
	public List<Report> getAllUserReport(){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "SELECT username,count(*) FROM TIMETABLE  group by username";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<Report> list=new ArrayList<Report>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ	
			rs = pstmt.executeQuery();				//ִ�в�ѯ			
			while(rs.next()) {							
				Report report=new Report();
				report.setName(rs.getString(1));
				report.setCount(rs.getString(2));
				list.add(report);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return list;
	}
	
	
	
	
	
}