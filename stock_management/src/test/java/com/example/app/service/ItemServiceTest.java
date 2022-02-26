package com.example.app.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.example.app.entity.Item;

import org.junit.jupiter.api.Test;

@SpringBootTest
@ActiveProfiles("unit")
class ItemServiceTest {

	@Test
	void myFirstTest() {
		assertEquals(2, 1 + 1);
	}

	@Autowired
	ItemService service;
	
	@Nested
	@Sql("/data-unit.sql")
	class findById{
		@Test
		void oneParameterNoemal() {
			//テスト用パラメータ（ユーザ１でテスト）
			int id = 1;
			Item result = service.findById(id);
			//テスト内容
			assertEquals(1,result.getId());
			asssertAll("取得結果")
		}
	}
	

}
