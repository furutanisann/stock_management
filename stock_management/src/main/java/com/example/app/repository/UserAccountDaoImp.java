package com.example.app.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.app.entity.User;


//ログイン関係
@Repository
public class UserAccountDaoImp implements UserAccountDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountDaoImp(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUser(String staff_id) {

        String sql = "SELECT * "
                + "FROM staff "
                + "WHERE staff_id = :staff_id";
        // パラメータ設定用Map
        Map<String, Object> param = new HashMap<>();
        param.put("staff_id", staff_id);

        User user = new User();
        // 一件取得
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);

            user.setStaff_id((String) result.get("staff_id"));
            user.setPassword((String)result.get("password"));
            user.setName((String)result.get("name"));
        }catch(EmptyResultDataAccessException e){
            Optional <User> userOpl = Optional.ofNullable(user);
            return userOpl;
        }

        // ラップする
        Optional <User> userOpl = Optional.ofNullable(user);
        return userOpl;
    }
}