package com.work.dao;

import java.util.List;

import com.work.entity.LinkMan;
import com.work.util.Page;

public interface LinkManDao {
  //��ѯ��ϵ��������
  public int queryLinkManCount();			
  //����
  public void addLinkMan(LinkMan linkman);
  //�޸�
  public void updateLinkMan(LinkMan linkman);
  //ɾ��
  public void deleteLinkMan(int id);
  //��ҳ��ѯ
  public List<LinkMan> queryLinkManByPage(Page page);
  //����ģ����ѯ
  public List<LinkMan> queryLinkManByName(String name,String depart,String key);
  //��ȡ����ģ������
  public List<String> queryLinkManByLikeName(String name);
	//����ɾ����ϵ�˼�¼
  public void batchDeleteLinkMan(String[] id);
  //ͨ����Ż�ȡ��ϵ����Ϣ
  public LinkMan queryLinkManById(int id);
}
  

