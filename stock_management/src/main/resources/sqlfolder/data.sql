DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS area;
DROP TABLE IF EXISTS fatory;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS stock;



INSERT INTO  staff VALUES
	('$2a$10$5TjXcdaUROvyF7VF278PW.gfOQpikHp9EYQPT26TXBXxyL4nmhyZe',"古谷健",29,"フルタニケン",1,NULL);

INSERT INTO department VALUES
	(1,"営業部"),(2,"開発部");

INSERT INTO user VALUES
	("仲間食品","ナカマショクヒン",1,1),
	("武藤商社","ムトウショウシャ",2,2);

INSERT INTO area VALUES
	(1,関東),(2,関西);

INSERT INTO fatory VALUES
	(1,"丹波工場"),(2,"本社工場");

INSERT INTO item VALUES
	("和風だし",9,NULL,2),
	("トマトソース",6,1,1),
	("タルタルソース",6,2,1);

INSERT INTO stock VALUES
	(1,5000,'2022-10-21'),
	(2,1000,'2022-07-14');

