package com.example.app.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 

//SpringのUserDetailsクラスを継承
//名前とパスワードが存在するか確かめる
public class MyUserDetails implements UserDetails {
     
	
    private static final long serialVersionUID = 1L;
    private final User user;
     
    public MyUserDetails(User user) {
        this.user = user;
    }
    
    //今回使用しないメソッドはtrueにしている
    
    
    
    //権限情報を返却
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    //パスワードを返す
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    //ユーザー名を返却
    @Override
    public String getUsername() {
        return user.getName();
    }
    
    //ユーザーの有効期限の確認
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    //ユーザーがロック状態か確認
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    //資格情報の有効期限内か確認
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    //アカウントが有効なユーザーかを返却
    
    @Override
    public boolean isEnabled() {
        return true;
    }
     
}