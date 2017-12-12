package com.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.dao.NoticeDao;
import com.work.entity.Notice;
import com.work.util.Page;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticedao;

	// ��ʾ���¹���
	public List<Notice> queryNotice() {
		return noticedao.queryNotice();
	}

	// ��ѯ��������
	public int noticeAllCount() {
		return noticedao.noticeAllCount();
	}

	// ��ѯ�����б�
	public List<Notice> queryNotice(Page page) {
		return noticedao.queryNotice(page);
	}

	// ���ӹ���
	public void addNotice(Notice notice) {
		noticedao.addNotice(notice);
	}

	// ���¹���
	public void updateNotice(Notice notice) {
		noticedao.updateNotice(notice);
	}

	// ɾ������
	public void deleteNotice(int id) {
		noticedao.deleteNotice(id);
	}
}
