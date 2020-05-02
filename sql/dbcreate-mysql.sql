-- ==============================================================
-- ST4 DB creation script for MySQL
-- ==============================================================

-- encoding for messages from the client to the server
SET NAMES utf8;

DROP DATABASE IF EXISTS st4db;

-- COLLATE - a set of rules for comparing characters in a set
CREATE DATABASE st4db CHARACTER SET utf8 COLLATE utf8_bin;

USE st4db;

-- --------------------------------------------------------------
-- ROLES
-- users roles
-- --------------------------------------------------------------
CREATE TABLE roles (
-- id has the INTEGER type, it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,
	
-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
	name VARCHAR(50) NOT NULL UNIQUE,
	CHECK(name REGEXP '^[[:alpha:]_-]{3,50}$')
);

-- this three commands insert data into roles table
-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started 
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'dispatcher');
INSERT INTO roles VALUES(2, 'driver');

-- --------------------------------------------------------------
-- USERS
-- --------------------------------------------------------------

CREATE TABLE users (
-- id has the INTEGER type, auto_increment attribute(means automatically increasing on 1), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	
-- 'UNIQUE' means logins values should not be repeated in login column of table	
	login VARCHAR(50) NOT NULL UNIQUE,
	
-- not null string columns	
	password VARCHAR(50) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	
	role_id INTEGER NOT NULL,

	CHECK(login REGEXP '^[[:alnum:]_-]{3,50}$'),
	CHECK(password REGEXP '^.{5,50}$'),
	CHECK(first_name REGEXP '^[[:alpha:]]{3,255}$'),
	CHECK(last_name REGEXP '^[[:alpha:]]{3,255}$'),
	FOREIGN KEY(role_id) REFERENCES roles(id) 
-- removing a row with some ID from roles table implies removing 
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in 
-- users table with ROLES_ID=ID
		ON DELETE CASCADE 

-- rejects the update operation for the parent table
		ON UPDATE RESTRICT
);

-- id = 1
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Nikita', 'Shishov', 0);
-- id = 2
INSERT INTO users VALUES(DEFAULT, 'dispatcher', 'dispatcher', 'Petr', 'Petrov', 1);
-- id = 3
INSERT INTO users VALUES(DEFAULT, 'драйвер1', 'драйвер1', 'Иван', 'Иванов', 2);
-- id = 4
INSERT INTO users VALUES(DEFAULT, 'драйвер2', 'драйвер2', 'Сидор', 'Сидоров', 2);


-- place for trigger!!!!!!!!!!!

-- ---------------------------------------------------------------
-- CAR STATES
-- state of car
-- ---------------------------------------------------------------

CREATE TABLE car_states(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	CHECK(name REGEXP '^[[:lower:]_-]{3,50}$')
);

INSERT INTO car_states VALUES(0, 'serviceable');
INSERT INTO car_states VALUES(1, 'defective');

-- ---------------------------------------------------------------
-- CAR car_models
-- model of car
-- ---------------------------------------------------------------

CREATE TABLE car_models(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	CHECK(name REGEXP '^[[:lower:]-]{3,50}$')
);

INSERT INTO car_models VALUES(DEFAULT, 'mercedes');
INSERT INTO car_models VALUES(DEFAULT, 'bmw');
INSERT INTO car_models VALUES(DEFAULT, 'porsche');
INSERT INTO car_models VALUES(DEFAULT, 'kia');

-- ---------------------------------------------------------------
-- CAR car_engine_type
-- type of engine
-- ---------------------------------------------------------------
CREATE TABLE car_engine_types(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	CHECK(name REGEXP '^[[:lower:]_-]{3,50}$')
);

INSERT INTO car_engine_types VALUES(0, 'gasoline');
INSERT INTO car_engine_types VALUES(1, 'gas');
INSERT INTO car_engine_types VALUES(2, 'electricity');

-- ---------------------------------------------------------------
-- CARS
-- ---------------------------------------------------------------

CREATE TABLE cars (
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	model_id INTEGER NOT NULL,
	engine_power INTEGER NOT NULL,
	num_of_seats INTEGER NOT NULL,
	engine_type_id INTEGER NOT NULL,
	state_id INTEGER NOT NULL,
	CHECK(engine_power >= 100 AND engine_power <= 10000),
	CHECK(num_of_seats >= 2 AND num_of_seats <= 6),
	FOREIGN KEY(model_id) REFERENCES car_models(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(engine_type_id) REFERENCES car_engine_types(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(state_id) REFERENCES car_states(id) ON DELETE CASCADE ON UPDATE RESTRICT
);


-- place for trigger!!!!!!!!!!!

-- id = 1 model_id = 1 engine_power=250 num_of_seats=4 engine_type_id=2 state_id = 0
INSERT INTO cars VALUES(DEFAULT, 1, 250, 4, 1, 0);
-- id = 2
INSERT INTO cars VALUES(DEFAULT, 3, 200, 2, 2, 0);
-- id = 3
INSERT INTO cars VALUES(DEFAULT, 2, 180, 2, 1, 1);
-- id = 4
INSERT INTO cars VALUES(DEFAULT, 4, 166, 5, 1, 0);
-- id = 5
INSERT INTO cars VALUES(DEFAULT, 2, 205, 4, 0, 0);
-- id = 6
INSERT INTO cars VALUES(DEFAULT, 3, 187, 3, 2, 1);

-- --------------------------------------------------------------
-- STATUSES
-- statuses for flights
-- --------------------------------------------------------------

CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	CHECK(name REGEXP '^[[:lower:]_-]{3,50}$')
);

INSERT INTO statuses VALUES(0, 'open');
INSERT INTO statuses VALUES(1, 'in_progress');
INSERT INTO statuses VALUES(2, 'closed');
INSERT INTO statuses VALUES(3, 'canceled');

-- ---------------------------------------------------------------
-- CITIES
-- ---------------------------------------------------------------

CREATE TABLE cities(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	CHECK(name REGEXP '^[:alpha:]{3,100}$')
);

INSERT INTO cities VALUES(DEFAULT, 'Kharkov');
INSERT INTO cities VALUES(DEFAULT, 'Dnepr');
INSERT INTO cities VALUES(DEFAULT, 'Kiev');
INSERT INTO cities VALUES(DEFAULT, 'Lvov');

-- ---------------------------------------------------------------
-- FLIGHTS
-- ---------------------------------------------------------------

CREATE TABLE flights(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	from_city_id INTEGER NOT NULL, 
	to_city_id INTEGER NOT NULL,
	date_of_creation TIMESTAMP(0) NOT NULL,
	status_id INTEGER NOT NULL,
	
	user_id INTEGER,
	car_id INTEGER,
	FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	FOREIGN KEY(car_id) REFERENCES cars(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	FOREIGN KEY(from_city_id) REFERENCES cities(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(to_city_id) REFERENCES cities(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(status_id) REFERENCES statuses(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

-- ---------------------------------------------------------------
-- FLIGHT TRIGGERS
-- ---------------------------------------------------------------

-- BEFORE

DELIMITER $$
CREATE TRIGGER before_flights_update
BEFORE UPDATE 
ON flights FOR EACH ROW
BEGIN
	DECLARE errorMessage1 VARCHAR(255);
	DECLARE errorMessage2 VARCHAR(255);
	SET errorMessage1 = CONCAT('User with id -> ', NEW.user_id, ' did not do request on flight with id -> ', OLD.id);
	SET errorMessage2 = CONCAT('Car with id -> ', NEW.car_id, ' is assigned.');

	-- if no user request for flight 
	IF OLD.status_id=0 AND NOT EXISTS (SELECT * FROM requests r WHERE (r.user_id=NEW.user_id AND r.flight_id=OLD.id))
		THEN SIGNAL SQLSTATE '45000' 
			SET MESSAGE_TEXT = errorMessage1;
	END IF;

	-- if car is already assigned for another flight
	IF OLD.status_id=0 AND NEW.car_id IN (SELECT f.car_id FROM flights f WHERE f.status_id=1 AND f.car_id IS NOT NULL)
		THEN SIGNAL SQLSTATE '45000' 
			SET MESSAGE_TEXT = errorMessage2;
	END IF;
END $$
DELIMITER ;

-- AFTER

DELIMITER $$
CREATE TRIGGER after_flights_update
AFTER UPDATE 
ON flights FOR EACH ROW
BEGIN
	-- delete all requests for this flight if user is assigned for it
	IF NEW.status_id=1
		THEN DELETE FROM requests WHERE flight_id=OLD.id;
	END IF;
END $$
DELIMITER ;



-- unconfirmed open flights
INSERT INTO flights VALUES(DEFAULT, 2, 1, '2020-03-10 10:10:10', 0, NULL, NULL);
INSERT INTO flights VALUES(DEFAULT, 3, 4, '2020-03-02 14:34:40', 0, NULL, NULL);
INSERT INTO flights VALUES(DEFAULT, 4, 2, '2020-03-07 08:20:36', 0, NULL, NULL);

-- confirmed by user with id=3 flight in progress 
INSERT INTO flights VALUES(DEFAULT, 2, 2, '2020-03-12 23:54:32', 1, 3, 1);

-- ---------------------------------------------------------------
-- REQUEST
-- ---------------------------------------------------------------

CREATE TABLE requests(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	user_id INTEGER NOT NULL,
	flight_id INTEGER NOT NULL,
	car_model_id INTEGER NOT NULL,
	car_engine_power INTEGER NOT NULL,
	car_num_of_seats INTEGER NOT NULL,
	car_engine_type_id INTEGER NOT NULL,
	UNIQUE(user_id, flight_id),
	CHECK(car_engine_power >= 100 AND car_engine_power <= 10000),
	CHECK(car_num_of_seats >= 2 AND car_num_of_seats <= 6),
	FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(flight_id) REFERENCES flights(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(car_model_id) REFERENCES car_models(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY(car_engine_type_id) REFERENCES car_engine_types(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

-- id = 1 model_id = 3 engine_power=250 
INSERT INTO requests VALUES(DEFAULT, 3, 2, 1, 250, 4, 2);
INSERT INTO requests VALUES(DEFAULT, 4, 1, 4, 225, 2, 1);
INSERT INTO requests VALUES(DEFAULT, 3, 3, 2, 200, 5, 0);

SELECT * FROM requests ORDER BY id;
SELECT * FROM flights ORDER BY id;
SELECT * FROM cities ORDER BY id;
SELECT * FROM statuses ORDER BY id;
SELECT * FROM cars ORDER BY id;
SELECT * FROM car_engine_types ORDER BY id;
SELECT * FROM car_models ORDER BY id;
SELECT * FROM car_states ORDER BY id;
SELECT * FROM users ORDER BY id;
SELECT * FROM roles ORDER BY id;


