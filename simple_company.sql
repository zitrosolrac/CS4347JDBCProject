CREATE DATABASE  IF NOT EXISTS `SimpleCompany`;
USE `SimpleCompany`;

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;


CREATE TABLE `Address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address1` varchar(90) NOT NULL,
  `address2` varchar(90) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zipcode` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `CreditCard`
--

DROP TABLE IF EXISTS `CreditCard`;


CREATE TABLE `CreditCard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `number` varchar(45) NOT NULL,
  `expiration` date NOT NULL,
  `securityCode` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;


CREATE TABLE `Customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `gender` char(1) NOT NULL,
  `dob` date NOT NULL,
  `email` varchar(45) NOT NULL,
  `address_id` int(11) DEFAULT NULL,
  `cc_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `cc_fk_idx` (`cc_id`),
  KEY `address_fk_idx` (`address_id`),
  CONSTRAINT `customer_address_fk` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_cc_fk` FOREIGN KEY (`cc_id`) REFERENCES `CreditCard` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;


CREATE TABLE `Product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `category` int(11) NOT NULL,
  `upc` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `Purchase`
--

DROP TABLE IF EXISTS `Purchase`;


CREATE TABLE `Purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `purchase_customer_fk_idx` (`customer_id`),
  KEY `purchase_product_fk_idx` (`product_id`),
  CONSTRAINT `purchase_customer_fk` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_product_fk` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
