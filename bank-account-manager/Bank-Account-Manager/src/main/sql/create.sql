drop database if exists `batdb`;
create database `batdb`;

use `batdb`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
       `userid` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       `firstName` VARCHAR(45) NOT NULL,
       `lastName` VARCHAR(45) NOT NULL,
       `userName` VARCHAR(45) NOT NULL,
       `password` VARCHAR (45) NOT NULL,
       `email` VARCHAR (45) NOT NULL,
       `address` VARCHAR (45) NOT NULL
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 ;

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
       `accountid` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       `accountName` VARCHAR (45) UNIQUE NOT NULL,
       `accountBalance` DOUBLE NOT NULL,
       `accountCurrency` VARCHAR(45) NOT NULL,
       `accountUserid` INT(11) NOT NULL
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
       `transactionid` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       `sourceAccount` VARCHAR(45) NOT NULL,
       `destinationAccount` VARCHAR(45) NOT NULL,
       `amount` DOUBLE NOT NULL,
       `currency` VARCHAR(45) NOT NULL,
       `transactionDate` VARCHAR(45) NOT NULL
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- Insert dummy data for `users`
INSERT INTO `users` (`userid`, `firstName`, `lastName`, `userName`, `password`, `email`, `address`)
       VALUES (1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "123 Main St, Cityville");
INSERT INTO `users` (`userid`, `firstName`, `lastName`, `userName`, `password`, `email`, `address`)
       VALUES (2, "Jane", "Smith", "janesmith", "password456", "janesmith@example.com", "456 Oak St, Townsville");
INSERT INTO `users` (`userid`, `firstName`, `lastName`, `userName`, `password`, `email`, `address`)
       VALUES (3, "Michael", "Johnson", "michaeljohnson", "password789", "michaeljohnson@example.com", "789 Pine St, Villagetown");

-- Insert dummy data for `accounts`
INSERT INTO `accounts` (`accountid`, `accountName`, `accountBalance`, `accountCurrency`, `accountUserid`)
       VALUES (1, "ACC123456789", 3173.58, "RON", "1");
INSERT INTO `accounts` (`accountid`, `accountName`, `accountBalance`, `accountCurrency`, `accountUserid`)
       VALUES (2, "ACC987654321", 1003.25, "USD", "1");
INSERT INTO `accounts` (`accountid`, `accountName`, `accountBalance`, `accountCurrency`, `accountUserid`)
       VALUES (3, "ACC112233445", 569.26, "EUR", "2");
INSERT INTO `accounts` (`accountid`, `accountName`, `accountBalance`, `accountCurrency`, `accountUserid`)
       VALUES (4, "ACC556677889", 920.35, "GBP", "2");
INSERT INTO `accounts` (`accountid`, `accountName`, `accountBalance`, `accountCurrency`, `accountUserid`)
       VALUES (5, "ACC223344556", 657.00, "USD", "3");
INSERT INTO `accounts` (`accountid`, `accountName`, `accountBalance`, `accountCurrency`, `accountUserid`)
       VALUES (6, "ACC667788990", 2230.50, "EUR", "3");

-- Insert dummy data for `transactions`
INSERT INTO `transactions` (`transactionid`, `sourceAccount`, `destinationAccount`, `amount`, `currency`, `transactionDate`)
       VALUES (1, "ACC123456789", "ACC112233445", 540, "RON", "2020/6/15");
INSERT INTO `transactions` (`transactionid`, `sourceAccount`, `destinationAccount`, `amount`, `currency`, `transactionDate`)
       VALUES (2, "ACC123456789", "ACC112233445", 456.50, "RON", "2020/6/16");
INSERT INTO `transactions` (`transactionid`, `sourceAccount`, `destinationAccount`, `amount`, `currency`, `transactionDate`)
       VALUES (3, "ACC123456789", "PAYPAL", 250.50, "RON", "2020/6/17");
INSERT INTO `transactions` (`transactionid`, `sourceAccount`, `destinationAccount`, `amount`, `currency`, `transactionDate`)
       VALUES (4, "ACC123456789", "AMAZON", 658.30, "RON", "2020/6/18");