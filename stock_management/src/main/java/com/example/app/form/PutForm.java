package com.example.app.form;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


//編集用
@Data
public class PutForm {

	private int id;
	private String categoryForm;
	private String contentForm;
	
	@NotNull(message = "日付を入力してください")
	private Date dateForm;
	
	@NotNull(message = "内容を入力してください")
	@Size(min = 1, max = 25, message = "２５文字以内で入力してください")
	private String titleForm;
	

	
}
