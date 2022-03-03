
CREATE TABLE staff(
	staff_id     INT(11) AUTO_INCREMENT NOT NULL,
	password    VARCHAR(100) NOT NULL,
	name        VARCHAR(30) NOT NULL,
	age         INT(3)       NOT NULL,
	canna	    VARCHAR(50)  ,
	department_id  INT(2)	 NOT NULL,
	particulars_id    INT(5)   ,
	PRIMARY KEY (staff_id));

CREATE TABLE department(
	department_id  INT(2)    NOT NULL,
	name	    VARCHAR(30)  NOT NULL,
	PRIMARY KEY (department_id));

CREATE TABLE user(
	user_id INT(3) AUTO_INCREMENT NOT NULL,
	name        VARCHAR(30) NOT NULL,
	canna	    VARCHAR(50)  NOT NULL,
	area_id   INT(3),
	staff_id  INT(3),
	PRIMARY KEY (user_id));

CREATE TABLE area(
	area_id  INT(3) ,
	name     VARCHAR(30) NOT NULL,
	PRIMARY KEY(area_id)
);

CREATE TABLE factory(
	factory_id INT(3) ,
	name      VARCHAR(30) NOT NULL,
	PRIMARY KEY(factory_id)
);

CREATE TABLE item(
	item_id INT(5) AUTO_INCREMENT,
	name  VARCHAR(50) NOT NULL,
	expiration_date INT(2) NOT NULL,
	factory_id INT(3),
	create_at date;
	PRIMARY KEY(item_id)
);

CREATE stock(
	item_id INT(5) NOT NULL ,
	stock   INT(5) NOT NULL,
	expiration DATE
	PRIMARY KEY (item_id)
);

