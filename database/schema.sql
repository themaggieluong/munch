BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS restaurant_category;
DROP TABLE IF EXISTS user_restaurant;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	zip_code varchar(10) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE category (
    category_id SERIAL,
    category_name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT PK_category PRIMARY KEY (category_id)
);

CREATE TABLE restaurant (
	restaurant_id SERIAL,
	restaurant_name varchar(50) NOT NULL,
	address varchar(100) NOT NULL,
	city varchar(50) NOT NULL,
	state varchar(50) NOT NULL,
	zip_code varchar(10) NOT NULL,
	description varchar(500) NOT NULL,
	phone_number bigint NOT NULL,
	rating float NOT NULL,
	img_src varchar(50),
	CONSTRAINT PK_restaurant PRIMARY KEY (restaurant_id)
);

CREATE TABLE restaurant_category (
	restaurant_id int NOT NULL,
	category_id int NOT NULL,
	CONSTRAINT PK_restaurant_category PRIMARY KEY (restaurant_id, category_id),
	CONSTRAINT FK_restaurant_category_restaurant FOREIGN KEY (restaurant_id) references restaurant (restaurant_id),
    CONSTRAINT FK_restaurant_category_category FOREIGN KEY (category_id) references category (category_id)
);

CREATE TABLE user_restaurant (
	user_id int NOT NULL,
	restaurant_id int NOT NULL,
	is_liked boolean,
	is_rejected boolean,
	visit_count int DEFAULT 0,
	CONSTRAINT PK_user_restaurant PRIMARY KEY (user_id, restaurant_id),
	CONSTRAINT FK_user_restaurant_users FOREIGN KEY (user_id) references users (user_id),
	CONSTRAINT FK_user_restaurant_restaurant FOREIGN KEY (restaurant_id) references restaurant (restaurant_id)

);

COMMIT TRANSACTION;

