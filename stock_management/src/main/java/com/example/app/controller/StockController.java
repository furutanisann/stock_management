package com.example.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.entity.Item;
import com.example.app.form.ChangeStock;
import com.example.app.form.GetForm;
import com.example.app.form.PostForm;
import com.example.app.form.PutForm;
import com.example.app.service.ItemService;

@Controller
@RequestMapping("/stocklist")
public class StockController {

	private final ItemService itemservice;

	@Autowired
	public StockController(ItemService itemservice) {
		this.itemservice = itemservice;
	}


	/**
     * 在庫一覧画面を表示
     * modelクラスで送信
     * th:object:"${getForm}"などの形で受け取ってください
     * @param model
     * @return resources/templates/stocklist.html
     */
    @GetMapping
    public String stockList(
    	@ModelAttribute GetForm form,
    	Model model
    	){
    	List<Item> list = itemservice.findList(form);
    	model.addAttribute("list",list);
    	model.addAttribute("getform",form);
    	return "stock_list";
    }


    /**
     * 一件タスクデータを取得し、詳細ページ表示
     * @param id
     * @param model
     * @return resources/templates/detail.html
     */
    // '/1'などのURLをパラメータ名として取得
    @GetMapping("/{id}")
    public String detail(
        @PathVariable int id,
        Model model) {

        //商品取得(NULL（空）かどうかNULLチェック）
        Optional<List<Item>> itemOpl = Optional.ofNullable(itemservice.findById(id));

        //NULLであればメッセージのみ、データが有れば詳細画面
        if(itemOpl.isPresent()) {
            model.addAttribute("items", itemOpl.get());
            return "item";
        } else {
            model.addAttribute("error", "対象データが存在しません");
            return "item";
        }
    }
    
    
    /**
     * 詳細ページ変更画面表示
     * @param id
     * @param model
     * @return resources/templates/adjustent.html
     */
    // '/1'などのURLをパラメータ名として取得
    @GetMapping("/{id}/adjustment")
    public String adjustment(
    	@RequestParam String date,
        @PathVariable int id,
        Model model) {
    	
    	Item item = itemservice.findByOnItem(id,date);
    	model.addAttribute("item",item);
    	return "adjustment";

    }
    
    
    /**
     * 詳細ページから数変更
     * @param id
     * @param model
     * @return resources/templates/adjustent.html
     */
    // '/1'などのURLをパラメータ名として取得
    @PostMapping("/{id}/adjustment")
    public String updateAdjustment(
            @ModelAttribute ChangeStock form,
            @PathVariable int id
        ) {

            int count = itemservice.updateonitem(form);
            return "redirect:/stocklist/" + id;
        }

    /**
     * 「一覧へ」選択時、一覧画面へ（戻る）
     * @param model
     * @return resources/templates/stock_list.html
     */
    //ここの指定で複数のパス指定が可能
    @PostMapping(path={"/insert", "/form","/update"}, params="back")
    public String backPage(
        Model model
    ) {
        return "redirect:/stocklist";
    }




    /**
     * 新規登録へ
     * @param model
     * @return resources/templates/form.html
     */
    @GetMapping("/form")
    public String formPage(
    	@ModelAttribute PutForm form,
        Model model
    ) {

        return "form";
    }





    /**
     * データ新規登録
     * @param postForm
     * @param model
     * @return
     */
    @PostMapping(path="/insert", params="insert")
    public String insert(
    	//バリデーションの確認
        @Valid @ModelAttribute PostForm form,
        //バリデーションの値,これの値により条件分岐
        BindingResult result,
        Model model
    ) {
        if(result.hasErrors()) {
            model.addAttribute("error", "登録情報の一部に誤りがあります");
            return "form";
        }
        int count = itemservice.insert(form);
        model.addAttribute("postForm", form);
        return "redirect:/startpage";
    } 
    
 	/**
      * 編集のため商品一覧画面を表示
      * modelクラスで送信
      * 全体的には在庫一覧と同じ
      * th:object:"${getForm}"などの形で受け取ってください
      * @param model
      * @return resources/templates/itemlist.html
      */
     @GetMapping("/itemlist")
     public String itemList(
     	@ModelAttribute GetForm form,
     	Model model
     	){
     	List<Item> list = itemservice.findList(form);
     	model.addAttribute("list",list);
     	model.addAttribute("getform",form);
     	return "item_list";
     }
     
     /**
      * 詳細ページ変更画面表示
      * @param id
      * @param model
      * @return resources/templates/edit.html
      */
     // '/1'などのURLをパラメータ名として取得
     @GetMapping("/{id}/edit")
     public String itemedit(
         @PathVariable int id,
         Model model) {
     	
     	Item item = itemservice.findItem(id);
     	model.addAttribute("item",item);
     	return "edit";

     }
     
     /**
      * 編集
      * @param putForm
      * @param model
      * @return
      */
     @PostMapping("/{id}/edit")
      public String update(
    		@PathVariable int id,
    	   	//バリデーションの確認
    	    @Valid @ModelAttribute PostForm form,
    	    //バリデーションの値,これの値により条件分岐
    	      BindingResult result,
    	      Model model
    	  ) {
    	       if(result.hasErrors()) {
    	          model.addAttribute("error", "登録情報の一部に誤りがあります");
    	           return "form";
    	       }
    	       int count = itemservice.update(form);
    	       model.addAttribute("postForm", form);
    	       return "redirect:/stocklist/itemlist";
      } 


    /**
     * 削除用
     * @param id
     * @param model
     * @return
     */
    @PostMapping("{id}/delete")
     public String delete(
    		 //パラメータをURLから受け取る
    		 @PathVariable int id,
    		 Model model) {
    	 int count = itemservice.delete(id);
    	 return "redirect:/stocklist/itemlist";
     }



}
