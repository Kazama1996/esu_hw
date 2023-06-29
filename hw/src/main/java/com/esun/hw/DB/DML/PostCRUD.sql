-- CREATE post
CREATE OR REPLACE PROCEDURE  create_post(p_user_id UUID, p_post_content VARCHAR(255))
AS $$
BEGIN
    INSERT INTO posts (user_id, post_content) VALUES (p_user_id, p_post_content);
END;
$$ LANGUAGE plpgsql;


