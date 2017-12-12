package com.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.work.entity.Employee;
import com.work.entity.OwnReport;
import com.work.util.BASE64;
import com.work.util.DBconnection;

/**
 * @author: jijiuxue
 * @date:2017-12-4 ����9:43:14
 * @version :1.0.0
 * 
 */
@Repository
public class OwnReportDaoImpl implements OwnReportDao {

	public void addOwnReport(OwnReport ownReport) {
		// TODO Auto-generated method stub
		Connection con = DBconnection.getConnection();
		String sql = "INSERT INTO ownreport(username,password,email,btime,wamount,wbusy,wdate1,wdesc,wlog,wplace,wplan,wprog,wquestion,wrepid,wstat,wsum,wtask,wtime,wtype,wunit) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement prep = null;
		try {
			String pass = null;
			try {
				//�����ݼ���
				pass = BASE64.encryptBASE64(ownReport.getPassword().getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prep = con.prepareStatement(sql);
			prep.setString(1, ownReport.getUsername());
			prep.setString(2, pass);
			prep.setString(3, ownReport.getEmail());
			prep.setString(4, ownReport.getBtime());
			prep.setString(5, ownReport.getWamount());
			prep.setString(6, ownReport.getWbusy());
			prep.setString(7, ownReport.getWdate1());
			prep.setString(8, ownReport.getWdesc());
			prep.setString(9, ownReport.getWlog());
			prep.setString(10, ownReport.getWplace());
			prep.setString(11, ownReport.getWplan());
			prep.setString(12, ownReport.getWprog());
			prep.setString(13, ownReport.getWquestion());
			prep.setString(14, ownReport.getWrepid());
			prep.setString(15, ownReport.getWstat());
			prep.setString(16, ownReport.getWsum());
			prep.setString(17, ownReport.getWtask());
			prep.setString(18, ownReport.getWtime());
			prep.setString(19, ownReport.getWtype());
			prep.setString(20, ownReport.getWunit());
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnection.close(con);
			DBconnection.close(prep);
		}
	}
   /**
    * ��ѯ��Ҫд�ձ�����Ա
    * @return
    */
	public List<OwnReport> getAllOwnReportByAutoReport() {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "SELECT id,username,password,email,btime,wamount,wbusy,wdate1,wdesc,wlog,wplace,wplan,wprog,wquestion,wrepid,wstat,wsum,wtask,wtime,wtype,wunit,wflag FROM ownreport where wflag=1";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<OwnReport> list = new ArrayList<OwnReport>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();				    //ִ�в�ѯ
			while(rs.next()) {
			OwnReport report=new OwnReport();
			report.setId(rs.getInt(1));
			report.setUsername(rs.getString(2));
			report.setPassword(rs.getString(3));
			report.setEmail(rs.getString(4));
			report.setBtime(rs.getString(5));
			report.setWamount(rs.getString(6));
			report.setWbusy(rs.getString(7));
			report.setWdate1(rs.getString(8));
			report.setWdesc(rs.getString(9));
			report.setWlog(rs.getString(10));
			report.setWplace(rs.getString(11));
			report.setWplan(rs.getString(12));
			report.setWprog(rs.getString(13));
			report.setWquestion(rs.getString(14));
			report.setWrepid(rs.getString(15));
			report.setWstat(rs.getString(16));
			report.setWsum(rs.getString(17));
			report.setWtask(rs.getString(18));
			report.setWtime(rs.getString(19));
			report.setWtype(rs.getString(20));
			report.setWunit(rs.getString(21));
			list.add(report);//���
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
	 /**
	    * ��ѯͬ�����ݵ���Ա
	    * @return
	    */
		public List<OwnReport> getAllOwnReportByAutoAsyn() {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL = "SELECT id,username,password,email,btime,wamount,wbusy,wdate1,wdesc,wlog,wplace,wplan,wprog,wquestion,wrepid,wstat,wsum,wtask,wtime,wtype,wunit,wflag FROM ownreport";
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			List<OwnReport> list = new ArrayList<OwnReport>();
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				rs = pstmt.executeQuery();				    //ִ�в�ѯ
				while(rs.next()) {
				OwnReport report=new OwnReport();
				report.setId(rs.getInt(1));
				report.setUsername(rs.getString(2));
				report.setPassword(rs.getString(3));
				report.setEmail(rs.getString(4));
				report.setBtime(rs.getString(5));
				report.setWamount(rs.getString(6));
				report.setWbusy(rs.getString(7));
				report.setWdate1(rs.getString(8));
				report.setWdesc(rs.getString(9));
				report.setWlog(rs.getString(10));
				report.setWplace(rs.getString(11));
				report.setWplan(rs.getString(12));
				report.setWprog(rs.getString(13));
				report.setWquestion(rs.getString(14));
				report.setWrepid(rs.getString(15));
				report.setWstat(rs.getString(16));
				report.setWsum(rs.getString(17));
				report.setWtask(rs.getString(18));
				report.setWtime(rs.getString(19));
				report.setWtype(rs.getString(20));
				report.setWunit(rs.getString(21));
				list.add(report);//���
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
	public Employee getLinkManByReportId(String username) {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "SELECT username FROM employee where userid=?";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		Employee employee=new Employee();	
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();				    //ִ�в�ѯ
			while(rs.next()) {
            		
			employee.setUsername(rs.getString(1));
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return employee;
	}
	/**
	 * �޸��û���ʶ ������Կ�������1
	 */
	public void modifyWflag(String username) {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "update ownreport  set wflag=1  where username=?";
		PreparedStatement pstmt = null;					//����Ԥ�������
      try{
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setString(1, username);
			 pstmt.executeUpdate();				        //ִ�в�ѯ
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
	
	}
	/**
	 * ÿ�춨ʱ����
	 * �޸��û���ʶ ������Կ�������0
	 */
	public void modifyWflagToZero() {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "update ownreport  set wflag=0";
		PreparedStatement pstmt = null;					//����Ԥ�������
      try{
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			 pstmt.executeUpdate();				        //ִ�в�ѯ
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
	
	}
	
	
	/**
	 * ��ѯ�ϰ���Ա
	 */
		public List<Employee> getAllUserWork() {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select t.username from employee t,OWNREPORT  o where t.userid=o.username and o.wflag=1";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<Employee> list = new ArrayList<Employee>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();				    //ִ�в�ѯ
			while(rs.next()) {
			Employee employee=new Employee();				
			employee.setUsername(rs.getString(1));
			list.add(employee);//���
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
	
	/**
	    * ��ѯ��Ҫ�޸ı�ʶ����Ա
	    * @return
	    */
		public List<OwnReport> getAllOwnReportByModifyFlag() {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL = "SELECT username FROM ownreport";
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			List<OwnReport> list = new ArrayList<OwnReport>();
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				rs = pstmt.executeQuery();				    //ִ�в�ѯ
				while(rs.next()) {
				OwnReport report=new OwnReport();				
				report.setUsername(rs.getString(1));
				list.add(report);//���
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
		/**
		 * ��ѯһ���û�����Ϣ
		 * @param username
		 * @return
		 */
		public OwnReport getAllOwnReportByUser(String username) {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL = "SELECT id, username,email,wdesc,wlog,wtask from ownreport where username=?";
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			OwnReport report = new OwnReport();
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				pstmt.setString(1, username);
				rs = pstmt.executeQuery();				    //ִ�в�ѯ
				while(rs.next()) {
				report.setId(rs.getInt(1));
				report.setUsername(rs.getString(2));
				report.setEmail(rs.getString(3));
				report.setWdesc(rs.getString(4));
				report.setWlog(rs.getString(5));								
				report.setWtask(rs.getString(6));
				}
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
		 * ��������
		 */
		public void updateOwnReportByUser(OwnReport report) {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL = "update ownreport set username=?,email=?,wdesc=?,wlog=?,wtask=?  where id=?";
			PreparedStatement pstmt = null;					//����Ԥ�������
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				pstmt.setString(1, report.getUsername());
				pstmt.setString(2, report.getEmail());
				pstmt.setString(3, report.getWdesc());
				pstmt.setString(4, report.getWlog());
				pstmt.setString(5, report.getWtask());
				pstmt.setInt(6,report.getId());
				pstmt.executeUpdate();		    //ִ�в�ѯ
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBconnection.close(pstmt);							//�ر�Ԥ�������
				DBconnection.close(conn);							//�ر����Ӷ���
			}
		
		}	
		public void deleteOwnReportByUser(int id) {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL = "delete from ownreport  where id=?";
			PreparedStatement pstmt = null;					//����Ԥ�������
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ			
				pstmt.setInt(1,id);
				pstmt.executeUpdate();		    //ִ�в�ѯ
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBconnection.close(pstmt);							//�ر�Ԥ�������
				DBconnection.close(conn);							//�ر����Ӷ���
			}
		
		}
		public List<Employee> getAllUserUnWork() {
			// TODO Auto-generated method stub
			Connection conn = DBconnection.getConnection();	//������Ӷ���
			String findSQL = "select t.username from employee t,OWNREPORT  o where t.userid=o.username and o.wflag=0";
			PreparedStatement pstmt = null;					//����Ԥ�������
			ResultSet rs = null;
			List<Employee> list = new ArrayList<Employee>();
			try {
				pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
				rs = pstmt.executeQuery();				    //ִ�в�ѯ
				while(rs.next()) {
				Employee employee=new Employee();				
				employee.setUsername(rs.getString(1));
				list.add(employee);//���
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
