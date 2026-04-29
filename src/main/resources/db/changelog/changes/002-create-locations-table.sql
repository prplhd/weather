CREATE TABLE locations (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id int NOT NULL,
    latitude DECIMAL(8, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,

    CONSTRAINT fk_locations_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);