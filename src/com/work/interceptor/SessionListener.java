package com.work.interceptor;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	 private int userCounts=0;
   //�����Ự
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		userCounts++;
		event.getSession().getServletContext().setAttribute("userCounts", userCounts);  
	
	}
   //�Ự����
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		userCounts--;

	}

}
