/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE IF EXISTS coupons_shops;
DROP TABLE IF EXISTS coupons;
DROP TABLE IF EXISTS shops;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
	CONSTRAINT email_not_empty CHECK (email <> ''),
	CONSTRAINT password_not_empty CHECK (password <> '')
);

CREATE TABLE shops (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
	CONSTRAINT name_not_empty CHECK (name <> '')
);

CREATE TABLE coupons (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    percentage INTEGER NOT NULL,
    CONSTRAINT name_not_empty CHECK (name <> ''),
	CONSTRAINT percentage_between_bounds CHECK (percentage >= 0 AND percentage <= 100)
);

CREATE TABLE coupons_shops (
    coupon_id INTEGER,
    shop_id INTEGER,
    PRIMARY KEY (coupon_id, shop_id),
    FOREIGN KEY (coupon_id) REFERENCES coupons(id),
    FOREIGN KEY (shop_id) REFERENCES shops(id)
);

INSERT INTO users (email, password) VALUES
	('user1@user1', 'user1'), -- 1
	('user2@user2', 'user2'), -- 2
	('user2@user3', 'user3'); -- 3

INSERT INTO shops (name) VALUES
	('SPAR'),   -- 1
	('Tesco'),  -- 2
	('Auchan'), -- 3
	('LIDL'),   -- 4
	('ALDI');   -- 5

INSERT INTO coupons (name, percentage) VALUES
	('Sausage discount', 10),           -- 1
	('Bread super-sale', 50),           -- 2
	('Bread super-sale', 40),           -- 3
	('20% off from EVERYTHING!', 20),   -- 4
	('1 product for FREE!', 100);       -- 5

INSERT INTO coupons_shops (coupon_id, shop_id) VALUES
    (1, 1), -- 1
    (1, 2),
    (1, 3),
    (2, 1), -- 2
    (2, 2),
    (2, 3),
    (2, 5),
    (3, 1), -- 3
    (3, 2),
    (3, 5),
    (4, 3), -- 4
    (5, 2), -- 5
    (5, 5);
