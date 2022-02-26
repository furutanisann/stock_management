package com.example.app.entity;



import lombok.Data;


@Data
public class Item {

	private int id;
	private String name;
	private int expiration_date;
	private int factory_id;

}
