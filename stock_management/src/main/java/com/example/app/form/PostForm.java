package com.example.app.form;


import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


//新規投稿用
@Data
public class PostForm {
	
	@NotNull
	private int id;
 
	@NotNull
	@Size(min = 1, max = 25, message="25文字以内で入力してください。")
	private String name;
	
	@NotNull
	private int expiration_date;

	@NotNull
	private int factory_id;
	
}