package com.example.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.app.service.MyUserService;

//DIコンテナ化
//Spring Security 有効化
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserService userService;

    @Autowired
    public WebSecurityConfig (MyUserService userService) {
        this.userService = userService;
    }

    // URLパス毎に制御
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
        	//リクエスト時の認証か定義
            .authorizeRequests()
            //リクエスト時に認証を必要ないよう設定
            .antMatchers("/js/**", "/css/**", "/loginForm").permitAll()
            //ここから以降認証を必要とする
            .anyRequest().authenticated()
            .and()
            .formLogin()
            //ログインフォーム指定
            .loginPage("/loginForm")
            .loginProcessingUrl("/login")
            .usernameParameter("staff_id")
            .passwordParameter("password")
            //ログイン成功時移動
            .defaultSuccessUrl("/stocklist", true)
            .failureUrl("/loginForm?error=true")
            //ログアウト部分（全ユーザーに適応）
            .and()
            .logout()
            .permitAll();
    }

    // ユーザ情報の取得
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    // パスワードハッシュ化する
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    	//これはハッシュ化済みの値をDBに登録する確認用に出力させるコード//
    	String password = "12345";
            String digest = bcpe.encode(password);
            System.out.println("ハッシュ値 = " + digest);
            return bcpe;
    }
}