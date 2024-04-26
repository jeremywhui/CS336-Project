CREATE DATABASE IF NOT EXISTS cs336Project;
USE cs336Project;

CREATE TABLE IF NOT EXISTS End_User(
    username VARCHAR(100) NOT NULL PRIMARY KEY, 
    password VARCHAR(100) NOT NULL, 
    role ENUM('Customer Representative', 'Admin') NULL
);

INSERT IGNORE INTO End_User(username, password, role)
VALUES ('test', 'test', NULL),
       ('admin', 'admin', 'Admin'),
       ('cr', 'cr', 'Customer Representative');
       
CREATE TABLE IF NOT EXISTS Question (
	question_id INT AUTO_INCREMENT,
	username varchar(100),
	answer VARCHAR(150) DEFAULT 'Waiting for Customer Rep Response',
	question VARCHAR(150) NOT NULL,
	PRIMARY KEY (question_id, username),
	FOREIGN KEY (username) REFERENCES End_User(username) ON DELETE CASCADE
);