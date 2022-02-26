package com.example.app.entity;



import java.sql.Date;

import lombok.Data;

//ゲッター・セッター自動生成
@Data
public class Item {

	private int id;
	private String name;
	private int expiration_date;
	private int factory_id;
	//以下詳細で使用する部分
	private String date;
	private int stock;
	private Date date2;
}
