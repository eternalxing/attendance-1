package com.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.work.entity.Notice;
import com.work.util.DBconnection;
import com.work.util.Page;
@Repository
public class NoticeDao {
	//��ʾ���¹���
	public List<Notice> queryNotice(){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "SELECT * FROM notice order by id desc limit 0,6";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<Notice> list = new ArrayList<Notice>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();				    //ִ�в�ѯ
			while(rs.next()) {
			Notice notice=new Notice();
			notice.setId(rs.getInt(1));
			notice.setTitle(rs.getString(2));
			notice.setContent(rs.getString(3));
			notice.setAuthor(rs.getString(4));
			notice.setPublishdate(rs.getString(5));
			list.add(notice);//���
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
	
	//��ѯ��������
	public int noticeAllCount(){
		Connection conn = DBconnection.getConnection();	//������Ӷ���			
		String findSQL = "select count(*) from notice";
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
	
	//��ѯ�����б�
	public List<Notice> queryNotice(Page page){
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "select * from notice " +
					 "order by id asc limit ?,?";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<Notice> list = new ArrayList<Notice>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setInt(1, page.getBeginIndex());		//��ѯ��ʼ��
			pstmt.setInt(2, page.getEveryPage());		//��ѯ��¼��
			rs = pstmt.executeQuery();				//ִ�в�ѯ
			while(rs.next()) {
			Notice notice=new Notice();
			notice.setId(rs.getInt(1));
			notice.setTitle(rs.getString(2));
			notice.setContent(rs.getString(3));
			notice.setAuthor(rs.getString(4));
			notice.setPublishdate(rs.getString(5));
			list.add(notice);//���
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
	
	
	
	//���ӹ���
    public void addNotice(Notice notice){
    	Connection con = DBconnection.getConnection();
    	String sql = "insert into notice(title,content,author,publishdate) values(?,?,?,?)";
    	
    	PreparedStatement prep = null;		
    	try {
    		prep = con.prepareStatement(sql);
    		prep.setString(1, notice.getTitle());
    		prep.setString(2, notice.getContent());
    		prep.setString(3,notice.getAuthor());
    		prep.setString(4, notice.getPublishdate() );

    		prep.executeUpdate();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		DBconnection.close(con);
    		DBconnection.close(prep);
    	}
    }
	//���¹���
    public void updateNotice(Notice notice){
    	Connection con = DBconnection.getConnection();
    	String sql = "update  notice set title=?,content=?,author=?,publishdate=?  where id=?";
    	PreparedStatement prep = null;		
    	try {
    		prep = con.prepareStatement(sql);

    		prep.setString(1, notice.getTitle());
    		prep.setString(2, notice.getContent());
    		prep.setString(3,notice.getAuthor());
    		prep.setString(4, notice.getPublishdate() );
    		prep.setInt(5, notice.getId());
    		prep.executeUpdate();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		DBconnection.close(con);
    		DBconnection.close(prep);
    	}	
    }
    //ɾ������
  public void deleteNotice(int id){
	  Connection con = DBconnection.getConnection();
		String sql = "delete from notice where id=?";
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
}
