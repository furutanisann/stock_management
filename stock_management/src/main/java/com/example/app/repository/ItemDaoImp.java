package com.example.app.repository;

import java.sql.Date;
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
import com.example.app.form.ChangeStock;
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
		//StringBuilderでSQLを連結
		StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * from item");


        // パラメータ設定用Map
        Map<String, String> param = new HashMap<>();

        // パラメータが存在した場合、where句にセット
        if(form.getName() != null && form.getName() != "") {
        	param.put("name", (form.getName()));
            sqlBuilder.append(" WHERE name = :name");
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

	//詳細ページ移動時のデータ取得(１商品をまとめて取得）
	@Override
	//引数のID番号から取得
	//データベースに１件もなかった場合、例外処理を行う
	public List<Item> findByid(int id) throws  IncorrectResultSizeDataAccessException {
		//DATE_FORMATの返り値はString
		String sql = " select d.item_id,d.name, DATE_FORMAT(c.expiration,'%Y年%m月%d日') as date, c.stock,c.expiration "
					+ "from item as d inner join stock as c on d.item_id = c.item_id "
					+ "WHERE d.item_id = :id ;";
		
		// パラメータにいれてMapへ返す
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
        //タスク一覧をMapのListで取得
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, param);
        //return用の空のListを用意
        List<Item> list = new ArrayList<Item>();

        //データをItemにまとめる(DaoからEntityクラスへ引き継ぎ）
        for(Map<String, Object> result : resultList) {
            Item item = new Item();
            item.setId((int)result.get("item_id"));
            item.setName((String)result.get("name"));
            item.setDate((String)result.get("date"));
            item.setStock((int)result.get("stock"));
            item.setDate2((Date)result.get("expiration"));
            list.add(item);
        }
        
        //全件取得と取得方法が違うが中身を一部変更している
        return list;
	}
	
	//１つの商品の１つの賞味期限取得
	@Override
	public Item findbyoneitem(int id, String date) {
		String sql = "SELECT d.item_id,d.name, DATE_FORMAT(c.expiration,'%Y年%m月%d日') as date, c.stock "
				+ "from item as d inner join stock as c on d.item_id = c.item_id "
				+ "WHERE d.item_id = :id AND c.expiration = :date ;";
		
		// パラメータにいれてMapへ返す
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("date", date);
		
		Item item = new Item();
		
		Map<String,Object> result = jdbcTemplate.queryForMap(sql, param);
			item.setId((int)result.get("item_id"));
            item.setName((String)result.get("name"));
            item.setDate((String)result.get("date"));
            item.setStock((int)result.get("stock"));
        return item;
	};
	
	
	
	//１つの商品を在庫調整
	@Override
	public int updateoneitem(ChangeStock form) {
		int count = 0;
		String sql = "UPDATE stock SET stock = (stock - :changestock) "
				   + "WHERE item_id = :id AND expiration = :date";
		//URLからのパラメーター作成用
		Map<String, Object> param = new HashMap<>();
		//formで送られてきたデータを一つ一つ取り出す
		param.put("changestock", form.getChangestock());
		param.put("id", form.getId());
		param.put("date", form.getDate());
		
		//数の更新のためにデータベースへ
	    count = jdbcTemplate.update(sql, param);
	    return count;
	}
	
	
	//商品登録用
	@Override
	public int insert(PostForm form) {
		//登録件数
		int count = 0;
		String sql = "INSERT INTO item(name,expiration_date,factory_id,create_at) "
				    +"VALUES(:name,:expiration_date,:factory_id,:update_time)";
		//登録用データ
		Map<String, Object> param = new HashMap<>();
		param.put("name",form.getName());
		param.put("expiration_date", form.getExpiration_date());
		param.put("factory_id", form.getFactory_id());
		//アップデート時刻取得
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		param.put("update_time", timestamp);
		//SQLでデータベースヘアクセス（登録）
		count = jdbcTemplate.update(sql,param);
		return count;

	}
	
	//商品編集(商品１件分取得）用
	@Override
	public Item finditem(int id) {
		
	String sql = "SELECT * from item where item_id = :id";
	Map<String, Object> param = new HashMap<>();
	param.put("id",id);
	
	Item item = new Item();

	Map<String,Object> result = jdbcTemplate.queryForMap(sql, param);
	item.setId((int)result.get("item_id"));
    item.setName((String)result.get("name"));
    item.setFactory_id((int)result.get("factory_id"));
    item.setExpiration_date((int)result.get("expiration_date"));
    return item;
	}


	//更新用
	@Override
	public int update(PostForm form) {
		int count = 0;
	    String sql = "UPDATE item "
	            + "SET name=:name, factory_id=:factory_id, expiration_date=:expiration_date, update_at=:update_at "
	            + "WHERE item_id=:id";

		//URLからのパラメーター作成用
		Map<String, Object> param = new HashMap<>();
		//フォームにはいった値を取得しSQLに渡す
	    param.put("id", form.getId());
	    param.put("name",form.getName());
	    param.put("factory_id", form.getFactory_id());
	    param.put("expiration_date", form.getExpiration_date());
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    param.put("update_at", timestamp);
	    count = jdbcTemplate.update(sql, param);
	    return count;
	}

	//削除用
	@Override
	public int delete(int id) {
		    int count = 0;
		    String sql = "DELETE FROM item "
		            + "WHERE item_id = :id";
		    // パラメータ設定用Map
		    Map<String, Object> param = new HashMap<>();
		    param.put("id", id);
		    count = jdbcTemplate.update(sql, param);
		    return count;
	}





}
