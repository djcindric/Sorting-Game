-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 22, 2013 at 02:14 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sorting`
--
CREATE DATABASE IF NOT EXISTS `sorting` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sorting`;

-- --------------------------------------------------------

--
-- Table structure for table `android_metadata`
--

CREATE TABLE IF NOT EXISTS `android_metadata` (
  `locale` varchar(80) NOT NULL DEFAULT 'en_us'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `android_metadata`
--

INSERT INTO `android_metadata` (`locale`) VALUES
('en_us'),
('en_us');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `LevelName` varchar(80) NOT NULL,
  `CatName` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`CatName`,`LevelName`),
  KEY `CatName` (`CatName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `CatName` varchar(30) NOT NULL DEFAULT '',
  `imgpath` varchar(255) NOT NULL,
  PRIMARY KEY (`CatName`,`imgpath`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `level`
--

CREATE TABLE IF NOT EXISTS `level` (
  `LevelName` varchar(30) NOT NULL DEFAULT '',
  `Icon` varchar(255) DEFAULT NULL,
  `isPreloaded` tinyint(1) NOT NULL,
  `background` varchar(255) NOT NULL,
  PRIMARY KEY (`LevelName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `savedcategory`
--

CREATE TABLE IF NOT EXISTS `savedcategory` (
  `SavedLevelName` varchar(30) NOT NULL,
  `SavedCatName` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`SavedLevelName`,`SavedCatName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `savedimage`
--

CREATE TABLE IF NOT EXISTS `savedimage` (
  `CatName` varchar(30) NOT NULL DEFAULT '',
  `imgpath` varchar(255) NOT NULL,
  `view` varchar(30) NOT NULL,
  PRIMARY KEY (`CatName`,`imgpath`),
  KEY `CatName` (`CatName`),
  KEY `CatName_2` (`CatName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `savedlevel`
--

CREATE TABLE IF NOT EXISTS `savedlevel` (
  `SavedLevelName` varchar(30) NOT NULL DEFAULT '',
  `Icon` varchar(255) DEFAULT NULL,
  `Background` varchar(255) NOT NULL,
  PRIMARY KEY (`SavedLevelName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `Level` FOREIGN KEY (`CatName`) REFERENCES `level` (`LevelName`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
