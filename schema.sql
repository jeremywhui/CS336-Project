CREATE DATABASE IF NOT EXISTS cs336Project;
USE cs336Project;

CREATE TABLE IF NOT EXISTS End_User(
    username VARCHAR(100) NOT NULL PRIMARY KEY, 
    password VARCHAR(100) NOT NULL, 
    role ENUM('Customer Representative', 'Admin') NULL
);

CREATE TABLE IF NOT EXISTS Shoes_Auction(
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

CREATE TABLE IF NOT EXISTS Boots_Auction(
    shoes_id INT, 
    height FLOAT, 
    PRIMARY KEY (shoes_id), 
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Sandals_Auction(
    shoes_id INT, 
    is_open_toed BOOLEAN,
    PRIMARY KEY(shoes_id),
    FOREIGN KEY(shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Sneakers_Auction(
    shoes_id INT, 
    sport VARCHAR(20),
    PRIMARY KEY (shoes_id),
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Bid(
    bid_id INT AUTO_INCREMENT,
    shoes_id INT,
    bidder_username VARCHAR(100),
    time_of_bid DATETIME NOT NULL,
    bid_amount DECIMAL(10, 2) NOT NULL,
    is_automatic BOOLEAN NOT NULL,
    PRIMARY KEY (bid_id, shoes_id, bidder_username),
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE,
    FOREIGN KEY (bidder_username) REFERENCES End_User (username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Auto_Bid(
    shoes_id INT,
    bidder_username VARCHAR(100),
    bid_increment DECIMAL(10, 2) NOT NULL,
    bid_maximum DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (shoes_id, bidder_username),
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE,
    FOREIGN KEY (bidder_username) REFERENCES End_User (username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Sale(
    shoes_id INT,
    buyer_username VARCHAR(100) NOT NULL,
    sell_price DECIMAL(10, 2),
    PRIMARY KEY (shoes_id, sell_price),
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE,
    FOREIGN KEY (buyer_username) REFERENCES End_User (username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Shoe_Preferences(
    name VARCHAR(50),
    brand VARCHAR(20),
    color VARCHAR(20),
    quality ENUM('New', 'Used', 'Refurbished'),
    size FLOAT,
    gender ENUM('M', 'F', 'U'),
    height FLOAT, 
    is_open_toed BOOLEAN,
    sport VARCHAR(20),
    preference_id INT AUTO_INCREMENT, 
    username VARCHAR(100) NOT NULL,
    PRIMARY KEY (preference_id),
    FOREIGN KEY (username) REFERENCES End_User (username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Alert_For_Auction(
    time_of_alert DATETIME,
    username VARCHAR(100),
    shoes_id INT,
    text VARCHAR(200) NOT NULL,
    PRIMARY KEY (time_of_alert, username, shoes_id),
    FOREIGN KEY (username) REFERENCES End_User(username) ON DELETE CASCADE,
    FOREIGN KEY (shoes_id) REFERENCES Shoes_Auction (shoes_id) ON DELETE CASCADE
);
       
CREATE TABLE IF NOT EXISTS Question (
	question_id INT AUTO_INCREMENT,
	username varchar(100),
	answer VARCHAR(150) DEFAULT 'Waiting for Customer Rep Response',
	question VARCHAR(150) NOT NULL,
	PRIMARY KEY (question_id, username),
	FOREIGN KEY (username) REFERENCES End_User(username) ON DELETE CASCADE
);

INSERT IGNORE INTO End_User(username, password, role) VALUES 
('test', 'test', NULL),
('user1', 'user1', NULL),
('user2', 'user2', NULL),
('user3', 'user3', NULL),
('admin', 'admin', 'Admin'),
('cr', 'cr', 'Customer Representative');

INSERT IGNORE INTO Shoes_Auction(shoes_id, seller_username, name, brand, color, quality, size, gender, deadline, min_bid_increment, secret_min_price, current_price) VALUES 
(100, 'user1','Air Max 90','Nike','Red','New',10,'M','2025-12-31 23:59:00',1.00,100.00,102.00),
(101, 'user1','Air Max 90','Nike','Black','New',11,'U','2025-12-31 23:59:00',1.00,100.00,102.00), 
(102, 'user2','Stan Smith','Adidas','White','Used',8,'F','2025-12-31 23:59:00',1.00,60.00,62.00),
(103, 'user2','Stan Smith','Adidas','White','Used',9,'U','2025-12-31 23:59:00',1.00,60.00,62.00), 
(104, 'user3','Classic Leather','Reebok','White','Refurbished',10,'M','2023-12-31 23:59:00',1.00,80.00,0.00),
(105, 'user3','Classic Leather','Reebok','White','New',11,'M','2023-12-31 23:59:00',1.00,80.00,0.00), 
(106, 'user1','Gel-Lyte III','Asics','Black','New',10,'U','2024-12-31 23:59:00',1.00,110.00,0.00),
(107, 'user1','Gel-Lyte III','Asics','Black','New',11,'M','2024-12-31 23:59:00',1.00,110.00,0.00), 
(108, 'user2','Old Skool','Vans','Red','Used',8,'F','2024-12-31 23:59:00',1.00,50.00,0.00),
(109, 'user2','Old Skool','Vans','Black','Used',9,'F','2024-12-31 23:59:00',1.00,50.00,0.00), 
(110, 'user3','Suede Classic','Puma','Black','New',10,'M','2025-12-31 23:59:00',1.00,70.00,0.00),
(111, 'user3','Suede Classic','Puma','Black','New',11,'M','2025-12-31 23:59:00',1.00,70.00,0.00), 
(112, 'user1','Chuck Taylor All Star','Converse','White','Refurbished',10,'M','2025-12-31 23:59:00',1.00,55.00,0.00),
(113, 'user1','Chuck Taylor All Star','Converse','White','New',11,'M','2025-12-31 23:59:00',1.00,55.00,0.00),
(114, 'user2','Authentic','Vans','Black','Used',8,'F','2025-12-31 23:59:00',1.00,45.00,0.00),
(115, 'user2','Authentic','Vans','Black','Used',9,'F','2024-12-31 23:59:00',1.00,45.00,0.00), 
(116, 'user3','Clyde','Puma','Red','Refurbished',10,'M','2024-12-31 23:59:00',1.00,75.00,0.00),
(117, 'user3','Clyde','Puma','White','New',11,'U','2024-12-31 23:59:00',1.00,75.00,0.00),
(118, 'user1','Superstar','Adidas','White','Refurbished',10,'M','2024-12-31 23:59:00',1.00,65.00,0.00),
(119, 'user1','Superstar','Adidas','White','New',11,'M','2024-12-31 23:59:00',1.00,65.00,0.00);

INSERT IGNORE INTO Boots_Auction(shoes_id, height) VALUES
(104, 5),
(105, 5),
(110, 8),
(111, 8),
(112, 6),
(113, 6),
(114, 6),
(115, 6);

INSERT IGNORE INTO Sandals_Auction(shoes_id, is_open_toed) VALUES
(102, 0),
(103, 0),
(106, 0),
(107, 0),
(118, 1),
(119, 1);

INSERT IGNORE INTO Sneakers_Auction(shoes_id, sport) VALUES
(100, 'Basketball'),
(101, 'Basketball'),
(108, 'Running'),
(109, 'Running'),
(116, 'Tennis'),
(117, 'Tennis');

INSERT IGNORE INTO Bid(shoes_id, bidder_username, time_of_bid, bid_amount, is_automatic) VALUES
(100, 'user2', DATE_SUB(NOW(), INTERVAL 8 MINUTE), 101.00, false),
(100, 'user3', DATE_SUB(NOW(), INTERVAL 7 MINUTE), 102.00, false),
(101, 'user2', DATE_SUB(NOW(), INTERVAL 6 MINUTE), 101.00, false),
(101, 'user3', DATE_SUB(NOW(), INTERVAL 5 MINUTE), 102.00, false),
(102, 'user1', DATE_SUB(NOW(), INTERVAL 4 MINUTE), 61.00, false),
(102, 'user3', DATE_SUB(NOW(), INTERVAL 3 MINUTE), 62.00, false),
(103, 'user1', DATE_SUB(NOW(), INTERVAL 2 MINUTE), 61.00, false),
(103, 'user2', DATE_SUB(NOW(), INTERVAL 1 MINUTE), 62.00, false);

INSERT IGNORE INTO Sale(shoes_id, buyer_username, sell_price) VALUES
(100, 'user3', 102.00),
(103, 'user2', 62.00);
