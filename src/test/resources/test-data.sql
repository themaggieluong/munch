BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role,first_name,last_name,zip_code) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER','Din','Grogu','10027');
INSERT INTO users (username,password_hash,role,first_name,last_name,zip_code) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN','John','Doe','10025');

INSERT INTO restaurant (restaurant_name, address, city, state, zip_code, description, phone_number, rating, img_src) VALUES ('Flip Sigi', '131 7th Ave S', 'New York', 'NY', '10014', 'Casual, chef-owned spot for Filipino-style tacos, burritos & sandwiches, plus beer, wine & punch.', 8333547744, 4.4, 'fong-on');
INSERT INTO restaurant (restaurant_name, address, city, state, zip_code, description, phone_number, rating, img_src) VALUES ('Wah Fung No.1 Fast Food', '79 Chrystie St', 'New York', 'NY', '100002', 'Roast pork is the specialty at this casual Chinese restaurant for street-style food.', 2129255175, 4.6, 'milu');
INSERT INTO restaurant (restaurant_name, address, city, state, zip_code, description, phone_number, rating, img_src) VALUES ('Da Andrea','35 W 13th St','New York', 'NY', '10011', 'Fresh-made pastas and home-style meat dishes are the hallmark of this budget Northern Italian spot.', '2123671979', 4.3, 'da-andrea');

INSERT INTO category (category_name) VALUES ('American');
INSERT INTO category (category_name) VALUES ('Chinese');

INSERT INTO restaurant_category (restaurant_id, category_id) VALUES (2, 2);

INSERT INTO user_restaurant(user_id, restaurant_id, is_liked, is_rejected, visit_count) VALUES (2,1,true, false, 0);
INSERT INTO user_restaurant(user_id, restaurant_id, is_liked, is_rejected, visit_count) VALUES (2,2,false, true, 0);

COMMIT TRANSACTION;
