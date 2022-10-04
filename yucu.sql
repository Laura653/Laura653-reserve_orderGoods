-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 04, 2022 at 01:10 AM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `yucu`
--

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `id` int(10) UNSIGNED NOT NULL,
  `account` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `realname` varchar(20) NOT NULL,
  `grade` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`id`, `account`, `password`, `realname`, `grade`) VALUES
(3, 'yucu', '$2a$10$wVHU/clOnbfHyyRbLfnH0OEu4eV9ej2PDhFPriDhYY44bDw2TBjiW', '朱宇慈', 0),
(4, 'test', '$2a$10$fvPPYM7TzlCeI93DbRstoO1EMn8sMakppep05l7vLjWworvYEcu5W', '王曉明', 1),
(5, 'tex', '$2a$10$P.oxIDRilFvv9aLLygI84.0JzpHVsGi9QY7AHm8DyHN8opdjT8TXS', '測試中', 1),
(6, 'amy', '$2a$10$/CtMiA4csaGVeDRO1D.Isuho..p/Sx10bsI4Fyqfmt1fn.K3OJRpm', '艾蜜莉', 1);

-- --------------------------------------------------------

--
-- Table structure for table `porder`
--

CREATE TABLE `porder` (
  `oid` int(10) UNSIGNED NOT NULL,
  `account` varchar(20) NOT NULL,
  `proid` int(10) NOT NULL,
  `quantity` varchar(20) NOT NULL,
  `odate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `porder`
--

INSERT INTO `porder` (`oid`, `account`, `proid`, `quantity`, `odate`) VALUES
(1, 'test', 3, '100', '2022-08-17 06:56:29'),
(2, 'tex', 16, '50', '2022-08-17 06:57:43'),
(3, 'tex', 22, '50', '2022-08-17 06:57:43');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `pid` int(10) UNSIGNED NOT NULL,
  `company` varchar(20) NOT NULL,
  `pname` varchar(20) NOT NULL,
  `taste` varchar(20) NOT NULL,
  `price` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`pid`, `company`, `pname`, `taste`, `price`) VALUES
(1, '東京敘敘苑', '燒肉醬', '原味', 248),
(2, '東京敘敘苑', '燒肉醬', '鹽味', 198),
(3, '東京敘敘苑', '燒肉醬', '甘辛', 248),
(4, '東京敘敘苑', '沙拉醬', '芝麻', 278),
(5, '東京敘敘苑', '沙拉醬', '黑醋', 258),
(6, '東京敘敘苑', '沙拉醬', '辛口', 288),
(7, '東京敘敘苑', '燒肉醬', '皆辛萬能味噌', 268),
(8, 'S&B', '辣油', '原味', 58),
(9, 'S&B', '辣油', '唐辛子', 58),
(10, 'S&B', '辣油', '四川風', 58),
(11, '今塩屋佐兵衛', '調味鹽', '大蒜鹽', 199),
(12, '今塩屋佐兵衛', '調味鹽', '柚子鹽', 185),
(13, '今塩屋佐兵衛', '調味鹽', '咖哩鹽', 185),
(14, '今塩屋佐兵衛', '調味鹽', '七味蔥鹽', 185),
(15, '今塩屋佐兵衛', '調味鹽', '梅子芝麻', 185),
(16, '今塩屋佐兵衛', '調味鹽', '黑胡椒芝麻', 185),
(17, '今塩屋佐兵衛', '調味鹽', '唐辛子', 160),
(18, '今塩屋佐兵衛', '調味鹽', '辣椒大蒜', 230),
(19, '牛角', '燒肉醬', '甘辛', 139),
(20, '牛角', '燒肉醬', '醬油', 139),
(21, '牛角', '燒肉醬', '鹽', 169),
(22, '牛角', '燒肉醬', '炭燒醬油', 169),
(23, '牛角', '燒肉醬', '韓式辣味', 119),
(24, '牛角', '燒肉醬', '濃郁蒜鹽', 119),
(25, '牛角', '燒肉醬', '味噌醬油', 119),
(26, '牛角', '燒肉醬', '九州甘口', 119),
(27, '牛角', '燒肉醬', '北海道蒜味奶油', 119);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `porder`
--
ALTER TABLE `porder`
  ADD PRIMARY KEY (`oid`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`pid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `porder`
--
ALTER TABLE `porder`
  MODIFY `oid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `pid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
