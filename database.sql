CREATE DATABASE shopapp;
USE shopapp;
CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '', 
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
AlTER TABLE users ADD COLUMN role_id INT;
CREATE TABLE roles(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);
AlTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);
-- t
CREATE TABLE tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    revoked TINYINT (1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- support for login by facebook and google
CREATE TABLE social_account(
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL COMMENT 'the name of social network',
    provider_id VARCHAR(20) VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'Email of user',
    name VARCHAR(100) NOT NULL COMMENT ' name of user',
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- table category
CREATE TABLE categories(
    id  INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'the name of the category'  
);

-- table for product
CREATE TABLE product(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) COMMENT 'NAME OF THE PRODUCT',
    price FLOAT NOT NULL CHECK(price >-0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- create table order
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK (total_money >= 0)
 ); 

AlTER TABLE orders ADD COLUMN 'shipping_method' VARCHAR(100);
AlTER TABLE orders ADD COLUMN 'shipping_address' VARCHAR(200);
AlTER TABLE orders ADD COLUMN 'shipping_date' DATE;
AlTER TABLE orders ADD COLUMN 'shipping_number' VARCHAR(100);
AlTER TABLE orders ADD COLUMN 'payment_method' VARCHAR(100);
--delete 1 order by delete soft
AlTER TABLE orders ADD COLUMN 'active' TINYINT(1);
-- status of the order must be 1 value. 
AlTER TABLE orders
MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled')
COMMENT 'Status of order';

CREATE TABLE order_detail(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    product_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    price FLOAT CHECK(price >= 0),
    number_of_product INT CHECK (number_of_product > 0),
    total_money FLOAT CHECK(total_money > 0),
    color VARCHAR(20) DEFAULT ''
);