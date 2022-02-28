package com.example.app.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.entity.Item;
import com.example.app.form.ChangeStock;
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
	
	//１つの商品（編集ページ確認）
	public List<Item> findById(int id) {
    	try {
    		return dao.findByid(id);
    		//１件取得できなかったときの処理
    	} catch(IncorrectResultSizeDataAccessException e) {
    		return null;
    	}
	}
	
	//１つの商品1件分（在庫）
	public Item findByOnItem(int id, String date) {
		return dao.findbyoneitem(id, date);
	}
	
	//1つの商品1件分（在庫調整）
	public int updateonitem(ChangeStock form){
		return dao.updateoneitem(form);
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
