package com.work.service;

import java.util.List;

import com.work.entity.Employee;
import com.work.entity.OwnReport;

/**
 * @author: jijiuxue
 * @date:2017-12-4 ����9:43:14
 * @version :1.0.0
 * 
 */
public interface OwnReportService {
	void addOwnReport(OwnReport ownReport);
    List<OwnReport>  getAllOwnReportByAutoReport();
    List<OwnReport>  getAllOwnReportByAutoAsyn();
    //�޸��û��ı�ʶ
    void modifyWflag(String username);
	public OwnReport getAllOwnReportByUser(String username);
	public void updateOwnReportByUser(OwnReport report);
	public void deleteOwnReportByUser(int id);
	public List<Employee> getAllUserWork();
	public List<Employee> getAllUserUnWork();
}
