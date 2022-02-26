package com.example.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.app.entity.MyUserDetails;
import com.example.app.entity.User;
import com.example.app.repository.UserAccountDaoImp;
 

@Service
public class MyUserService implements UserDetailsService {
 
    private final UserAccountDaoImp dao;
     
    @Autowired
    public MyUserService(UserAccountDaoImp dao) {
        this.dao = dao;
    }
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
         
        Optional<User> user = dao.findUser(userId);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException(userId + "が存在しません");
        }   
        return new MyUserDetails(user.get());
    }
}
