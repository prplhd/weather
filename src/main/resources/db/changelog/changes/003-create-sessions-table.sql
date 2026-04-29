CREATE TABLE sessions (
    id UUID PRIMARY KEY,
    user_id int NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_sessions_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);