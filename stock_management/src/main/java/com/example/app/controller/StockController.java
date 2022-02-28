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
     * 一覧画面を表示
     * modelクラスで送信
     * th:object:"${getForm}"などの形で受け取ってください
     * @param model
     * @return resources/templates/stocklist.html
     */
    @GetMapping
    public String itemList(
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
    @PostMapping(path="/{id}/adjustment", params="update")
    public String updateAdjustment(
            @ModelAttribute ChangeStock form,
            @PathVariable int id,
            BindingResult result,
            Model model
        ) {
    	//バリデーションで問題があった場合エラーで戻る
            if(result.hasErrors()) {
                model.addAttribute("error", "パラメータエラーが発生しました。");
                return "redirect:/stocklist/" + id + "/adjustment";
            }
            int count = itemservice.updateonitem(form);
            return "redirect:/stocklist";
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
    @PostMapping("/form")
    public String formPage(
    	@ModelAttribute PutForm form,
        Model model
    ) {
    	model.addAttribute("putForm", form);
    	//新規登録か編集なのかで分岐
    	//true or false は　HTMLで設定
    	if (form.getUpdateFlag()) {
            model.addAttribute("update", true);
        } else {
            model.addAttribute("update", false);
        }
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
            model.addAttribute("error", "パラメータエラーが発生しました。");
            return "form";
        }
        int count = itemservice.insert(form);
        model.addAttribute("postForm", form);
        return "redirect:/stocklist";
    }



    /**
    * 編集
    * @param putForm
    * @param model
    * @return
    */
    @PostMapping(path="/update", params="update")
    public String update(
        @ModelAttribute PutForm form,
        BindingResult result,
        Model model
    ) {
        if(result.hasErrors()) {
            model.addAttribute("error", "パラメータエラーが発生しました。");
            return "form";
        }
        int count = itemservice.update(form);
        return "redirect:/stocklist";
    }


    /**
     * 削除用
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/delete")
     public String delete(
    		 //パラメータをURLから受け取る
    		 @RequestParam int id,
    		 Model model) {
    	 int count = itemservice.delete(id);
    	 return "redirect:/stocklist";
     }



}
