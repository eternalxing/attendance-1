package com.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.dao.OperationDao;
import com.work.entity.OperationLog;

//������־
@Service
public class OperationService {
	@Autowired
	private OperationDao operationdao;
	//��ҳ��ѯ��־
	//������ѯ��־
	//����ɾ����־
	//�����־
    //������־
	public void addLog(OperationLog log){
		operationdao.addLog(log);
	}
}
