package com.example.app.repository;

import java.util.Optional;

import com.example.app.entity.User;

public interface UserAccountDao {
	// Userを取得
    Optional<User> findUser(String userId);
}
