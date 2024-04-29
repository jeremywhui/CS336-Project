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

CREATE TABLE Shoes_Auction(
    shoes_id INT AUTO_INCREMENT,
    seller_username VARCHAR(100), 
    name VARCHAR(50) NOT NULL,
    brand VARCHAR(20) NOT NULL,
    color VARCHAR(20) NOT NULL,
    quality ENUM('New', 'Used', 'Refurbished') NOT NULL,
    size FLOAT NOT NULL,
    gender ENUM('M', 'F', 'U') NOT NULL,
    deadline DATETIME NOT NULL,
    min_bid_increment DECIMAL(10, 2) NOT NULL,
    secret_min_price DECIMAL(10, 2) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (shoes_id), 
    FOREIGN KEY (seller_username) REFERENCES End_User (username) ON DELETE CASCADE
);

CREATE TABLE Boots_Auction(
    shoes_id INT, 
    height FLOAT, 
    PRIMARY KEY (shoes_id), 
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);

CREATE TABLE Sandals_Auction(
    shoes_id INT, 
    is_open_toed BOOLEAN,
    PRIMARY KEY(shoes_id),
    FOREIGN KEY(shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);

CREATE TABLE Sneakers_Auction(
    shoes_id INT, 
    sport VARCHAR(20),
    PRIMARY KEY (shoes_id),
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);

CREATE TABLE Alert(
    gender CHAR(1),
    size FLOAT,
    brand VARCHAR(20),
    quality VARCHAR(10),
    name VARCHAR(50),
    color VARCHAR(20),
    alert_id INT AUTO_INCREMENT,
    username VARCHAR(100),
    PRIMARY KEY (alert_id, username),
    FOREIGN KEY (username) REFERENCES End_User (username) ON DELETE CASCADE
);

CREATE TABLE Alert_For_S(
    alert_id INT,
    username VARCHAR(100),
    shoe_id INT,
    PRIMARY KEY (alert_id, username, shoe_id),
    FOREIGN KEY (alert_id, username) REFERENCES Alert(alert_id, username) ON DELETE CASCADE,
    FOREIGN KEY (shoe_id) REFERENCES Shoe_Listing (shoe_id) ON DELETE CASCADE
);

       
CREATE TABLE IF NOT EXISTS Question (
	question_id INT AUTO_INCREMENT,
	username varchar(100),
	answer VARCHAR(150) DEFAULT 'Waiting for Customer Rep Response',
	question VARCHAR(150) NOT NULL,
	PRIMARY KEY (question_id, username),
	FOREIGN KEY (username) REFERENCES End_User(username) ON DELETE CASCADE
);