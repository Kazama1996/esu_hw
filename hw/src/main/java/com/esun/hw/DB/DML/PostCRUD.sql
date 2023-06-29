-- CREATE post
CREATE OR REPLACE PROCEDURE  create_post(p_user_id UUID, p_post_content VARCHAR(255))
AS $$
BEGIN
    INSERT INTO posts (user_id, post_content) VALUES (p_user_id, p_post_content);
END;
$$ LANGUAGE plpgsql;

-- UPDATE post
CREATE OR REPLACE PROCEDURE  update_post( p_post_id UUID ,p_post_content VARCHAR(255) , p_created_at TIMESTAMP WITH TIME ZONE)
AS $$
BEGIN
	UPDATE posts SET post_content = p_post_content , created_at=p_created_at  WHERE post_id = p_post_id;
END;
$$ LANGUAGE plpgsql;

-- DELETE post
CREATE OR REPLACE PROCEDURE  update_post( p_post_id UUID , p_user_id UUID)
AS $$
BEGIN
	DELETE FROM posts WHERE post_id = p_post_id AND user_id = p_user_id;
END;
$$ LANGUAGE plpgsql;
