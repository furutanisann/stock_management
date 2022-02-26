package com.example.app.entity;

import lombok.Data;

//ユーザー関係のエンティティ
@Data
public class User {

	private String staff_id;
	private String password;
	private String name;

}
