package com.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.work.entity.Photo;
import com.work.util.DBconnection;
import com.work.util.Page;

//������ʵ����
@Repository
public class PhotoDao {
  //������Ƭ
	public void addPhoto(Photo photo){
		Connection con = DBconnection.getConnection();
    	String sql = "insert into photo(title,content,path,author,publishdate,wisdom) values(?,?,?,?,?,?)";
    	
    	PreparedStatement prep = null;		
    	try {
    		prep = con.prepareStatement(sql);
    		prep.setString(1,photo.getTitle());
    		prep.setString(2,photo.getContent() );
    		prep.setString(3,photo.getPath());
    		prep.setString(4,photo.getAuthor());
    		prep.setString(5,photo.getPublishdate());
    		prep.setString(6,photo.getWisdom());
    		prep.executeUpdate();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		DBconnection.close(con);
    		DBconnection.close(prep);
    	}
	}	
	//�޸���Ƭ
		public void updatePhoto(Photo photo){
			Connection con = DBconnection.getConnection();
	    	String sql = "update photo set title=?,content=?,author=?,publishdate=?,wisdom=? where id=?";
	    	
	    	PreparedStatement prep = null;		
	    	try {
	    		prep = con.prepareStatement(sql);
	    		prep.setString(1,photo.getTitle());
	    		prep.setString(2,photo.getContent() );
	    		prep.setString(3,photo.getAuthor());
	    		prep.setString(4,photo.getPublishdate());
	    		prep.setString(5,photo.getWisdom());
	    		prep.setInt(6,photo.getId());
	    		prep.executeUpdate();
	    	} catch (SQLException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} finally {
	    		DBconnection.close(con);
	    		DBconnection.close(prep);
	    	}
		}	

//��ѯ��������
	public int photoAllCount(){
		Connection conn = DBconnection.getConnection();	//������Ӷ���			
		String findSQL = "select count(*) from photo";
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
  //��ѯ��Ƭ
public List<Photo> selectPhoto(Page page){
	Connection conn = DBconnection.getConnection();	//������Ӷ���
	String findSQL = "select * from photo " +
				 "order by id asc limit ?,?";
	PreparedStatement pstmt = null;					//����Ԥ�������
	ResultSet rs = null;
	List<Photo> list = new ArrayList<Photo>();
	try {
		pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
		pstmt.setInt(1, page.getBeginIndex());		//��ѯ��ʼ��
		pstmt.setInt(2, page.getEveryPage());		//��ѯ��¼��
		rs = pstmt.executeQuery();				    //ִ�в�ѯ
		while(rs.next()) {
		Photo photo=new Photo();
		photo.setId(rs.getInt(1));
		photo.setTitle(rs.getString(2));
		photo.setWisdom(rs.getString(3));
		photo.setContent(rs.getString(4));
		photo.setPath(rs.getString(5));
		photo.setPublishdate(rs.getString(6));
		photo.setAuthor(rs.getString(7));
		list.add(photo);//���
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
//��ѯ��Ƭ
public List<Photo> selectPhoto(int begin){
	Connection conn = DBconnection.getConnection();	//������Ӷ���
	String findSQL = "select * from photo " +
				 "order by id desc limit ?,6";
	PreparedStatement pstmt = null;					//����Ԥ�������
	ResultSet rs = null;
	List<Photo> list = new ArrayList<Photo>();
	try {
		pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
		pstmt.setInt(1, begin);
		rs = pstmt.executeQuery();				    //ִ�в�ѯ
		while(rs.next()) {
		Photo photo=new Photo();
		photo.setId(rs.getInt(1));
		photo.setTitle(rs.getString(2));
		photo.setWisdom(rs.getString(3));
		photo.setContent(rs.getString(4));
		photo.setPath(rs.getString(5));
		photo.setPublishdate(rs.getString(6));
		photo.setAuthor(rs.getString(7));
		list.add(photo);//���
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
//ɾ����Ƭ
public void deletePhoto(int id){
	  Connection con = DBconnection.getConnection();
		String sql = "delete from photo where id=?";
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
