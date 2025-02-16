BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role,first_name,last_name,zip_code) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER','Din','Grogu','10027');
INSERT INTO users (username,password_hash,role,first_name,last_name,zip_code) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN','John','Doe','10025');

COMMIT TRANSACTION;
