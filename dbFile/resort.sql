-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2017 at 10:01 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `resort`
--

-- --------------------------------------------------------

--
-- Table structure for table `adminlog`
--

CREATE TABLE `adminlog` (
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `sequrity_Answer1` varchar(50) DEFAULT NULL,
  `sequrity_Answer2` varchar(50) DEFAULT NULL,
  `sequrity_Answer3` varchar(50) DEFAULT NULL,
  `tables` int(11) DEFAULT NULL,
  `categories` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adminlog`
--

INSERT INTO `adminlog` (`username`, `password`, `sequrity_Answer1`, `sequrity_Answer2`, `sequrity_Answer3`, `tables`, `categories`) VALUES
('2', '2', 'me', 'gps', 'blue', 12, 6);

-- --------------------------------------------------------

--
-- Table structure for table `categories_list`
--

CREATE TABLE `categories_list` (
  `name_categories` varchar(50) DEFAULT NULL,
  `productsNumber` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categories_list`
--

INSERT INTO `categories_list` (`name_categories`, `productsNumber`) VALUES
('Coffee', 3),
('Pizza', 4),
('Burger', 4),
('MockTail', 4),
('Italian', 3),
('chinese', 3);

-- --------------------------------------------------------

--
-- Table structure for table `info_resort`
--

CREATE TABLE `info_resort` (
  `reort_name` varchar(50) DEFAULT NULL,
  `owner_name` varchar(50) DEFAULT NULL,
  `contact` varchar(15) DEFAULT NULL,
  `mail` varchar(32) DEFAULT NULL,
  `gstin_no` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `info_resort`
--

INSERT INTO `info_resort` (`reort_name`, `owner_name`, `contact`, `mail`, `gstin_no`) VALUES
('Red Chilli', 'Abhi', '999999999', 'hsdjhj', 'XXXXXXXXX');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productname` varchar(50) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productname`, `category`, `price`) VALUES
('Cold Coffee', 'Coffee', 69),
('Hot Coffee', 'Coffee', 49),
('Iced Coffee', 'Coffee', 149),
('Silicon Pizza', 'Pizza', 449),
('Greek Pizza', 'Pizza', 284),
('California Pizza', 'Pizza', 349),
('Veg Burger', 'Burger', 39.45),
('Chicken Burger', 'Burger', 99),
('Blue Shoe', 'MockTail', 154),
('Cherry CockTail', 'MockTail', 249),
('thai Iced Tea', 'MockTail', 199),
('Virgin Mery', 'MockTail', 149),
('Bruschetta', 'Italian', 1249.5),
('Pasta Carbonara', 'Italian', 749.5),
('Mushroom Risotto', 'Italian', 500),
('Chowmein', 'chinese', 55),
(' Manchow Soup', 'chinese', 149.99),
('Spring Rolls', 'chinese', 199),
('Choco Pizza', 'Pizza', 249.5),
('Chees Burger', 'Burger', 70),
('Paneer Burger', 'Burger', 45);

-- --------------------------------------------------------

--
-- Table structure for table `table_items`
--

CREATE TABLE `table_items` (
  `booked_table` varchar(12) DEFAULT NULL,
  `item` varchar(30) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
