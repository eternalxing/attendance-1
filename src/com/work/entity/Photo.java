package com.work.entity;
//������ʵ����
public class Photo {
	private int id;        //���
	private String title;  //��ǩͷ
	private String content;//����
	private String path;   //·��
	private String wisdom; //����
	private String author; //����
	private String publishdate;//��������
	
	public String getWisdom() {
		return wisdom;
	}
	public void setWisdom(String wisdom) {
		this.wisdom = wisdom;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "PhotoDao [id=" + id + ", title=" + title + ", content="
				+ content + ", path=" + path + "]";
	}

}
