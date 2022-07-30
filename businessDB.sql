-- Host: localhost:3306

show databases;
CREATE DATABASE IF NOT EXISTS business;
use business;


CREATE table IF NOT EXISTS `employee` (
  `fname` varchar(25) NOT NULL,
  `lname` varchar(25) NOT NULL,
  `bdate` date NOT NULL,
  `SIN` int NOT NULL,
  `Address` varchar(30),
  `Salary` int NOT NULL,
  `Mgr_sin` int,
  `D_no` int(4) NOT NULL,
  UNIQUE KEY `SIN_UNIQUE` (`SIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
;

INSERT INTO employee
VALUES ('John', 'Johnson', '1990-08-23', '101043726','735 North Road SW','150000','509652970',6),
	   ('James', 'Edison', '2000-01-11', '23054656','420 Tampa way Drive','70000','',2),
	   ('Gab', 'Jordan', '1990-08-23', '121443776','730 5th Ave S','76000','509652970',9),
	   ('Micheal', 'Bill', '2001-08-23', '230007895','415 W 3rd St','85000.753','',30),
	   ('Tony', 'Beal', '1970-03-14', '101043700','4497 Bent Brothers Blvd','100000','101043726',16),
	   ('Chris', 'Paul', '1995-05-25', '509652970','7 Calle Insurgentes 413A','150000','',9);