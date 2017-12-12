package com.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.dao.PhotoDao;
import com.work.entity.Photo;
import com.work.util.Page;

@Service
public class PhotoService {
	@Autowired PhotoDao photodao;
	  //��ѯ��Ƭ
	public List<Photo> selectPhoto(Page page){
		return photodao.selectPhoto(page);
	}
	//��ѯ��Ƭ
	public List<Photo> selectPhoto(int begin){
		return photodao.selectPhoto(begin);
	}
	//��ѯ��������
		public int photoAllCount(){
			return photodao.photoAllCount();
	}
    //ɾ����Ƭ
	public void deletePhoto(int id){
		photodao.deletePhoto(id);
	}
	  //������Ƭ
		public void addPhoto(Photo photo){
			photodao.addPhoto(photo);
		}
		//�޸���Ƭ
		public void updatePhoto(Photo photo){
			photodao.updatePhoto(photo);
		
		}
}
