package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.entity.Item;
import com.example.app.form.GetForm;
import com.example.app.form.PostForm;
import com.example.app.form.PutForm;
import com.example.app.repository.ItemDao;
import com.example.app.repository.ItemDaoImp;

//ビジネスロジック　サービスクラス
@Service
@Transactional
public class ItemService {
	private final ItemDao dao;
	
	//データベース接続クラスのコンテナ
	@Autowired
	public ItemService(ItemDaoImp dao) {
		this.dao = dao;
	}
	
	//コンテナ(formタグ)から値を取得し返却
	public List<Item> findList(GetForm form) {
	       return dao.findList(form);
	}
	
	//値の登録
	public int insert(PostForm form) {
		return dao.insert(form);
	}
	
	//１件データ取得用（編集ページ確認）
	public Item findById(int id) {
    	try {
    		return dao.findByid(id);
    		//１件取得できなかったときの処理
    	} catch(IncorrectResultSizeDataAccessException e) {
    		return null;
    	}
	}
	
	//データ更新用
	public int update(PutForm form) {
		return dao.update(form);
	}
	
	//データ削除用
	public int delete(int id) {
		return dao.delete(id);
	}
}
