package com.work.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 
 * @author jijiuxue
 * @version v0.0.1
 * @desc ����Ϊ��־��������� �����ǲ���spring sopʵ��
 */
public class Logger {
	//������־
	public static void log(String msg){
		File file=new File("attendance.log");
		try {
			PrintWriter out=new PrintWriter(new FileWriter(file,true));
			out.println(new Date()+":"+msg);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ����ִ��ǰ������־
	public static void before(String msg) {
		log(msg);
	}

}
