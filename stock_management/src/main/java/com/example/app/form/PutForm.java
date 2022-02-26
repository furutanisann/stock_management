package com.example.app.form;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


//編集用
public class PutForm {

	private int id;
	private boolean updateFlag;
	private String categoryForm;
	private String contentForm;
	
	@NotNull(message = "日付を入力してください")
	private Date dateForm;
	
	@NotNull(message = "タイトルを入力してください")
	@Size(min = 1, max = 25, message = "２５文字以内で入力してください")
	private String titleForm;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}
	
	//これで新規登録か編集か分岐して同じFormページ使用
	public boolean getUpdateFlag() {
		return updateFlag;
	}

	public String getCategoryForm() {
		return categoryForm;
	}

	public void setCategoryForm(String categoryForm) {
		this.categoryForm = categoryForm;
	}

	public String getContentForm() {
		return contentForm;
	}

	public void setContentForm(String contentForm) {
		this.contentForm = contentForm;
	}

	public Date getDateForm() {
		return dateForm;
	}

	public void setDateForm(Date dateForm) {
		this.dateForm = dateForm;
	}

	public String getTitleForm() {
		return titleForm;
	}

	public void setTitleForm(String titleForm) {
		this.titleForm = titleForm;
	}

	
	
}
