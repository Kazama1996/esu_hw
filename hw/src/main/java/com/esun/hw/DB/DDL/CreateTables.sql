CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users(
	user_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
	user_name VARCHAR(30) NOT NULL,
 phone varchar(13) NOT NULL UNIQUE,
	email VARCHAR(35) NOT NULL UNIQUE,
	pwd VARCHAR(72) NOT NULL,
	biography VARCHAR(250) DEFAULT ''
); 

CREATE TABLE posts(
	post_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
	user_id UUID NOT NULL,
	post_content VARCHAR(280),
	created_at TIMESTAMPTZ DEFAULT current_timestamp,
	CONSTRAINT fk_user_id_post
	FOREIGN KEY (user_id)
	REFERENCES users(user_id)
);

CREATE TABLE user_comments(
	comment_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
	user_id UUID NOT NULL,
	post_id UUID NOT NULL,
	comment_content VARCHAR(30) NOT NULL,
	created_at TIMESTAMPTZ DEFAULT current_timestamp,
	CONSTRAINT fk_user_id_comments
	FOREIGN KEY (user_id)
	REFERENCES users(user_id),
	CONSTRAINT fk_post_id_comments
	FOREIGN KEY (post_id)
	REFERENCES posts(post_id)
	
);

