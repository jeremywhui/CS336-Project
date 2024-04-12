CREATE DATABASE IF NOT EXISTS cs336Project;
USE cs336Project;

CREATE TABLE IF NOT EXISTS End_User(
    user_id INT AUTO_INCREMENT, 
    username VARCHAR(100) NOT NULL UNIQUE, 
    password VARCHAR(100) NOT NULL, 
    email VARCHAR(50) NOT NULL UNIQUE, 
    isSeller BOOLEAN, 
    isBuyer BOOLEAN, 
    PRIMARY KEY (user_id)
);

INSERT IGNORE INTO End_User(username, password, email)
VALUES ('test', 'test', 'test@test.com');