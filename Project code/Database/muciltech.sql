-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 04, 2017 at 07:43 AM
-- Server version: 5.5.32
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `muciltech`
--
CREATE DATABASE IF NOT EXISTS `muciltech` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `muciltech`;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`userid`, `username`, `password`) VALUES
(1, 'mucil', 'mucil@1995'),
(2, 'festor', 'festor2017');

-- --------------------------------------------------------

--
-- Table structure for table `branches`
--

CREATE TABLE IF NOT EXISTS `branches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `location` varchar(30) NOT NULL,
  `address` varchar(20) NOT NULL,
  `contacts` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`id`, `name`, `location`, `address`, `contacts`) VALUES
(2, 'Kondele Box', 'Kisumu', '34, Kisumu', '0790959933'),
(3, 'Kaka Workspace', 'Mega mall-Kakamega', '670-9000 Kakamega', '0780679980'),
(4, 'Eastleigh', 'Abhurkhala inc-Eestleigh', '600-9000 Eastleigh', '0733257800'),
(5, 'Kefi-nco', 'Kenfinco Estate-Kakamega', '67-9000 Kakaga', '0780679980'),
(6, 'Spartak Mos', 'Taj mall-Moscow', '67-9000 Moscow', '0780679980'),
(7, 'Pied Piper', 'Times-Carlifornia', '67-9000 Carlifonia', '0780679980'),
(8, 'Taj Complex', 'Taj mall-Nairobi', '67-9000 Nairobi', '0780679980'),
(9, 'Pues Piper', 'Trump towers-New york', '289-9800 New york', '0790859200'),
(11, 'Drappers', 'Nairobi', '34, Nairobi', '0725312233'),
(12, 'Canyon', 'Concer City', '35, Hulk Road, Conce', '0725624452'),
(13, 'Camp Mucil', 'Kakamega', '130-600, Kakamega', '0702503079');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `names` varchar(30) NOT NULL,
  `identification` varchar(10) NOT NULL,
  `region` varchar(20) NOT NULL,
  `amount` varchar(15) NOT NULL,
  `phone_no` varchar(15) NOT NULL,
  `address` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `names`, `identification`, `region`, `amount`, `phone_no`, `address`) VALUES
(1, 'Hamphrey Mushila', '12345678', 'Western', '202000', '0790959933', '1000 Oilibya, Kingston'),
(2, 'Sylvester Katana', '89004576', 'Luo nyanza', '200000', '0723458890', '45-200 Lake basin, Kisumu'),
(3, 'Festor Lute', '56346728', 'Upper western', '490000', '0712674589', '347-2000 Sango, Turbo'),
(4, 'Eliud Irungu', '17470034', 'Upper western', '2000000', '0745673899', '6000 Lute, Matunda'),
(5, 'Wesley Kidiavayi', '88590120', 'Upper western', '48000', '0723480066', '36-8900 Mandela road, Nairobi'),
(19, 'Dennis Nyakundi', '89000562', 'Luo nyanza', '0', '0735223389', '8900 Ojijo, Nairobi'),
(21, 'Geofrey Angadia', '71143000', 'Lower western', '51000', '0711235689', '76 Kefinco, Kakamega'),
(22, 'Kevin Mbugua', '12009000', 'Southern nyanza', '0', '0714563322', '34, Elm Street'),
(23, 'Keith Aduda', '00000000', 'Rift valley', '0', '0725664433', '55 Elm street corner'),
(24, ' Alvin Kinjo', '58000000', 'Western', '0', '0723667890', '56 Kamusinga'),
(25, 'colloh', '22852228', 'Nyanza', '0', '0715178007', 'kisumu'),
(26, 'Faith Sholei', '11111111', 'Rift valley', '0', '0723667512', '21 Langas, Eldoret'),
(28, 'Franklin Oywero', '98765432', 'Western', '0', '0732456877', '33 Bus station, Rongo');

-- --------------------------------------------------------

--
-- Table structure for table `deposit`
--

CREATE TABLE IF NOT EXISTS `deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` varchar(15) NOT NULL,
  `transaction_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `deposit`
--

INSERT INTO `deposit` (`id`, `amount`, `transaction_id`) VALUES
(1, '1000', 'S7RVR6Q7');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `names` varchar(30) NOT NULL,
  `phone_no` varchar(11) NOT NULL,
  `identification` varchar(10) NOT NULL,
  `receiptcontents` varchar(100) NOT NULL,
  `receiptquantities` varchar(100) NOT NULL,
  `grandtotal` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `names`, `phone_no`, `identification`, `receiptcontents`, `receiptquantities`, `grandtotal`) VALUES
(18, 'Hamphrey Mushila', '0790959933', '12345678', '[LCD HD Monitor Sonny] \n', '[1] \n', '10000');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(50) NOT NULL,
  `price` varchar(10) NOT NULL,
  `category` varchar(20) NOT NULL,
  `image` varchar(50) NOT NULL,
  `offer_price` varchar(10) NOT NULL,
  `offer` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `description`, `price`, `category`, `image`, `offer_price`, `offer`) VALUES
(1, 'LCD HD Monitor Sonny', '27 inches, HD, Sonny ', '10000', 'Input/Output', 'http://192.168.43.135/muciltech/products/0.png', '', ''),
(2, 'HP Notebook 1500', 'Core i5, 500 GB HDD, 2GB RAM', '40000', 'Comp-system', 'http://192.168.43.135/muciltech/products/1.png', '', ''),
(3, 'Twinster HI Speakers', 'High quality sound', '2000', 'Input/Output', 'http://192.168.43.135/muciltech/products/2.png', '1500', '1'),
(4, 'Oval Mini Dish', 'Real time data delivery', '20000', 'Com-network', 'http://192.168.43.135/muciltech/products/3.png', '', ''),
(5, 'DFX RAM Memory set', '4 set, fast memory', '10000', 'Processing/CPU', 'http://192.168.43.135/muciltech/products/4.png', '', ''),
(6, 'Apple Wireless Mouse', 'Wireless', '800', 'Input/Output', 'http://192.168.43.135/muciltech/products/5.png', '', ''),
(7, 'MS package', 'Office 2013 package', '10000', 'Miscellaneous', 'http://192.168.43.135/muciltech/products/6.png', '', ''),
(8, 'PS Mini gamer', 'Real time gaming with high graphics', '2500', 'Miscellaneous', 'http://192.168.43.135/muciltech/products/7.png', '', ''),
(9, 'SATA HDD', '500 GB', '7000', 'Storage', 'http://192.168.43.135/muciltech/products/8.png', '', ''),
(10, 'Laptop bag', 'Specified for carrying laptops', '2000', 'Miscellaneous', 'http://192.168.43.135/muciltech/products/9.png', '', ''),
(11, 'UT TECH Rooter', 'Support upto 20 Clients', '5000', 'Com-network', 'http://192.168.43.135/muciltech/products/10.png', '', ''),
(12, 'Ethernet Cables', '1 Metre', '200', 'Com-network', 'http://192.168.43.135/muciltech/products/11.png', '', ''),
(13, 'Epson 3570 Series', 'High quality color/black & white ', '18000', 'Input/Output', 'http://192.168.43.135/muciltech/products/12.png', '', ''),
(14, '121 Gamer', 'Awesome gaming specs', '4000', 'Miscellaneous', 'http://192.168.43.135/muciltech/products/13.png', '', ''),
(15, 'HDMI Cable', 'DY2, Hp Manufacturer', '1500', 'Com-network', 'http://192.168.43.135/muciltech/products/14.png', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE IF NOT EXISTS `sales` (
  `id` varchar(10) NOT NULL,
  `identification` varchar(10) NOT NULL,
  `receiptcontents` varchar(100) NOT NULL,
  `receiptquantities` varchar(100) NOT NULL,
  `grandtotal` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `identification`, `receiptcontents`, `receiptquantities`, `grandtotal`) VALUES
('12', '12345678', '[LCD HD Monitor Sonny] \n[HP Notebook 1500] \n', '[1] \n[1] \n', '50000'),
('14', '12345678', '[LCD HD Monitor Sonny] \n', '[1] \n', '10000'),
('15', '12345678', '[LCD HD Monitor Sonny] \n', '[1] \n', '10000'),
('16', '12345678', '[HP Notebook 1500] \n', '[1] \n', '40000'),
('17', '12345678', '[LCD HD Monitor Sonny] \n', '[1] \n', '12000');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
