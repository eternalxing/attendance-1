package com.work.util;
//��ҳ������
public class PageUtil {
	public static Page createPage(int everyPage,int currentPage,int totalCount){
		int everypage=getEveryPage(everyPage);
		int currentpage=getCurrentPaage(currentPage);
		int totalpage=getTotalPage(everyPage,totalCount);
		int index=getIndex(everyPage,currentPage);
		boolean prepage=getPrePage(currentPage);
		boolean nextpage=getNextPage(totalpage,currentPage);
		return new Page(everypage,totalCount,totalpage,currentpage,index,prepage,nextpage);
	}
	//��ȡÿҳ��¼
	public static int getEveryPage(int everyPage){
		return everyPage==0?10:everyPage;
	}
	//��ȡ��ǰҳ
	public static int getCurrentPaage(int currentPage){
		return currentPage==0?1:currentPage;
	}
	//��ȡ��ҳ��
	public static int getTotalPage(int everyPage,int totalCount){
		int totalpage=0;
		if(totalpage!=0 && totalCount%everyPage==0){
			totalpage=totalCount/everyPage;
		}else{
			totalpage=totalCount/everyPage+1;
		}
		
		return totalpage;
	}
	//��ȡ��ʼλ��
	public static int getIndex(int everyPage,int currentPage){
		return (currentPage-1)*everyPage;
	}
	//�Ƿ�����һҳ
	public static boolean getPrePage(int currentPage){
		return currentPage==1?false:true;
	}
	//�Ƿ�����һҳ
	public static boolean getNextPage(int totalPage,int currentPage){
		return currentPage==totalPage||totalPage==0?false:true;
	}
}
