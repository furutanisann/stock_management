package com.example.app.form;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PostStockForm {

	@NotNull
	private int id;
	
	@NotNull
	private int stock;
	
	@NotNull
	private Date date;
	
	@NotNull
	private int factory_id;

}
