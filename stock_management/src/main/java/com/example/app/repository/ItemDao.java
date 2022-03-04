package com.example.app.repository;


import java.util.List;

import com.example.app.entity.Item;
import com.example.app.form.ChangeStock;
import com.example.app.form.GetForm;
import com.example.app.form.PostForm;
import com.example.app.form.PutForm;

//データベース接続用インターフェースクラス
public interface ItemDao {
	


	//登録されているものを取得メソッド
	List<Item> findList(GetForm form);
	
	
	//idを指定して１つ商品の在庫内容を表示
	List<Item> findByid(int id);
	
	
	//商品在庫更新用
	int update(PostForm form);
	
	//商品削除用
	int delete(int id);
	
	//商品在庫1つを更新するために取得
	Item findbyoneitem(int id, String date);
	
	//商品１つの在庫（出荷表へ登録も含む）
	int updateoneitem(ChangeStock form);

	//商品登録用
	int insert(PostForm form) ;
	
	//商品内容編集の為、取得
	Item finditem(int id);
}
