package com.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.work.entity.WebSite;
import com.work.util.DBconnection;

/**
 * @author: jijiuxue
 * @date:2017-12-4 ����9:43:14
 * @version :1.0.0
 * 
 */
@Repository
public class WebSiteDaoImpl implements WebSiteDao {

	public void addWebSite(WebSite webSite) {
		// TODO Auto-generated method stub
		
	}

	public List<WebSite> getAllWebSite() {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "SELECT id,agent,connection,contenttype,encoding,formurl,host,language,loginurl,pageurl, referer FROM WEBSITE ";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		List<WebSite> list = new ArrayList<WebSite>();
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			rs = pstmt.executeQuery();				    //ִ�в�ѯ
			while(rs.next()) {
				WebSite site=new WebSite();
             site.setId(rs.getInt(1));
             site.setAgent(rs.getString(2));
             site.setConnection(rs.getString(3));
             site.setContenttype(rs.getString(4));
             site.setEncoding(rs.getString(5));
             site.setFormurl(rs.getString(6));
             site.setHost(rs.getString(7));
             site.setLanguage(rs.getString(8));
             site.setLoginurl(rs.getString(9));
             site.setPageurl(rs.getString(10));
             site.setReferer(rs.getString(11));
			list.add(site);//���
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
	public WebSite getWebSiteById(int id) {
		// TODO Auto-generated method stub
		Connection conn = DBconnection.getConnection();	//������Ӷ���
		String findSQL = "SELECT id,agent,connection,contenttype,encoding,formurl,host,language,loginurl,pageurl, referer,listurl FROM WEBSITE where id=?";
		PreparedStatement pstmt = null;					//����Ԥ�������
		ResultSet rs = null;
		WebSite site=null;
		try {
			pstmt = conn.prepareStatement(findSQL);		//���Ԥ������󲢸�ֵ
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();				    //ִ�в�ѯ
			 site=new WebSite();
			 while(rs.next()) {	
             site.setId(rs.getInt(1));
             site.setAgent(rs.getString(2));
             site.setConnection(rs.getString(3));
             site.setContenttype(rs.getString(4));
             site.setEncoding(rs.getString(5));
             site.setFormurl(rs.getString(6));
             site.setHost(rs.getString(7));
             site.setLanguage(rs.getString(8));
             site.setLoginurl(rs.getString(9));
             site.setPageurl(rs.getString(10));
             site.setReferer(rs.getString(11));
             site.setListurl(rs.getString(12));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBconnection.close(rs);								//�رս��������
			DBconnection.close(pstmt);							//�ر�Ԥ�������
			DBconnection.close(conn);							//�ر����Ӷ���
		}
		return site;
	}

}
