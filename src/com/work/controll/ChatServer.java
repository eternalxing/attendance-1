package com.work.controll;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/*
 * �˷������ڳ�ʱ�����web��������
 * websocket ��������
 * author:jijiiuxue
 * date 2017-9-9
 * version :v0.0.0
 * desc :ʵ��ʵʱͨѶ
 */
@ServerEndpoint("/server")
public class ChatServer {
	//��¼��ǰ������
	private static int onlinecount=0;
    //concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
	private static CopyOnWriteArraySet<ChatServer> websocketset=new CopyOnWriteArraySet<ChatServer>();
	//��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	private Session session;
	//��ȡ����
	@OnOpen
    public void OnOpen(Session session){
    	this.session=session;
    	websocketset.add(this);
    	//�����Ӽ��� ������1
    	addOnlineCount();
    	System.out.println("�����˼���,welcome ��ǰ����������Ϊ"+getOnlineCount());
    }
    //���ӹر�
	@OnClose
	public void Close(){
		websocketset.remove(this);
		subOnlineCount();
		System.out.println("һ�˴�������״̬,��ǰ����������Ϊ"+getOnlineCount());
	}
	//��ȡ�ͻ�����Ϣ
	@OnMessage
	public void OnMessage(String message,Session session){
		System.out.println("�ͻ��˷�����Ϣ"+message);
		//Ⱥ����Ϣ
		for(ChatServer item: websocketset){
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		//��ʼ����html��ǩ��ȡ����
		//Document doc=Jsoup.parse(message);
		//Elements s= doc.getElementsByClass("msg own");
		//String content=s.text();
		//��������html��ǩ
//		if(content.contains("@С��")){
//			System.out.println(message);
//			System.out.println(message.substring(message.indexOf("��")).trim());
//			message=GetMessageFromInterface.getMessageTuLing(content);
//			message="rebot"+message;
//			//Ⱥ����Ϣ
//			for(ChatServer item: websocketset){
//				try {
//					item.sendMessage(message);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					continue;
//				}
//			}
//		}

	}
	//��������
	 @OnError
	    public void onError(Session session, Throwable error){
	        System.out.println("��������");
	        error.printStackTrace();
	    }
	//������������Ϣ
	/**
     * ������������漸��������һ����û����ע�⣬�Ǹ����Լ���Ҫ��ӵķ�����
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
	
   //��ȡ������
	public static synchronized int getOnlineCount() {
        return onlinecount;
    }
  //��������1
    public static synchronized void addOnlineCount() {
        ChatServer.onlinecount++;
    }
  //��������1
    public static synchronized void subOnlineCount() {
        ChatServer.onlinecount--;
    }
}
