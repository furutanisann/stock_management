package com.example.app.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//ユーザーログイン用のコントローラー
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//　ドメイン後の指定の名前から、---.htmlをよびだす
		registry.addViewController("/").setViewName("loginForm");
		registry.addViewController("/loginForm").setViewName("loginForm");
	}
}