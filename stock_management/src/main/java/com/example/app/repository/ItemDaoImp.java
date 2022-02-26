package com.example.app.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.app.entity.Item;
import com.example.app.form.GetForm;
import com.example.app.form.PostForm;
import com.example.app.form.PutForm;

//DBへ直接サクセス実装クラス
@Repository
public class ItemDaoImp implements ItemDao {

	//データベースアクセス用クラス
	private final NamedParameterJdbcTemplate jdbcTemplate;

	//コンテナにより自動生成
	//呼び出されるたびにこれを使用
	@Autowired
	public ItemDaoImp(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	//要求内容（すべての投稿内容を取得)
	public List<Item> findList(GetForm form) {
		StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * from item");


        // パラメータ設定用Map
        Map<String, String> param = new HashMap<>();

        // パラメータが存在した場合、where句にセット
        if(form.getFactory() != null && form.getFactory() != "") {
            sqlBuilder.append(" WHERE factory_id = :factory");
            param.put("factory", form.getFactory());
        }

        String sql = sqlBuilder.toString();

        //タスク一覧をMapのListで取得
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, param);
        //return用の空のListを用意
        List<Item> list = new ArrayList<Item>();

        //データをItemにまとめる(DaoからEntityクラスへ引き継ぎ）
        for(Map<String, Object> result : resultList) {
            Item item = new Item();
            item.setId((int)result.get("item_id"));
            item.setName((String)result.get("name"));
            item.setExpiration_date((int)result.get("expiration_date"));
            item.setFactory_id((int)result.get("factory_id"));
            list.add(item);
        }
        //まとめたものをリストとして返す
        //カラム : String　値 : Object 返却先で変換してください
        return list;
	}

	//登録用
	@Override
	public int insert(PostForm form) {
		//登録件数
		int count = 0;
		String sql = "INSERT INTO diary(category,title,content,date,update_datetime)"
				    +"VALUES(:category,:title,:content,:date,update_datetime)";
		//登録用データ
		Map<String, Object> param = new HashMap<>();
		param.put("category",form.getCategoryForm());
		param.put("title", form.getTitleForm());
		param.put("content", form.getContentForm());
		param.put("date",form.getDateForm());
		//アップデート時刻取得
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		param.put("update_time", timestamp);
		//SQLでデータベースヘアクセス（登録）
		count = jdbcTemplate.update(sql,param);
		return count;

	}

	//詳細ページ移動時のデータ取得
	@Override
	//データベースに１件もなかった場合、例外処理を行う
	public Item findByid(int id) throws  IncorrectResultSizeDataAccessException {
		String sql = "SELECT d.id, d.category, d.title, d.content, DATE_FORMAT(d.date, '%Y/%m/%d') AS date, d.update_datetime, c.name "
	            + "FROM diary AS d INNER JOIN category_code AS c ON d.category = c.cd "
	            + "WHERE d.id = :id";

	    // パラメータにいれてMapへ返す
	    Map<String, Object> param = new HashMap<>();
	    param.put("id", id);
	    // queryForMap（１つ取得するために）
	    Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);
	    Item item = new Item();


	    return item;
	}

	//更新用
	@Override
	public int update(PutForm form) {
		int count = 0;
	    String sql = "UPDATE diary "
	            + "SET category=:category, title=:title, content=:content, date=:date, update_datetime=:update_datetime "
	            + "WHERE id=:id";

		//URLからのパラメーター作成用
		Map<String, Object> param = new HashMap<>();
		//フォームにはいった値を取得しSQLに渡す
	    param.put("id", form.getId());
	    param.put("category", form.getCategoryForm());
	    param.put("title", form.getTitleForm());
	    param.put("content", form.getContentForm());
	    param.put("date", form.getDateForm());
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    param.put("update_datetime", timestamp);
	    count = jdbcTemplate.update(sql, param);
	    return count;
	}

	//削除用
	@Override
	public int delete(int id) {
		    int count = 0;
		    String sql = "DELETE FROM diary "
		            + "WHERE id = :id";
		    // パラメータ設定用Map
		    Map<String, Object> param = new HashMap<>();
		    param.put("id", id);
		    count = jdbcTemplate.update(sql, param);
		    return count;
	};

}
