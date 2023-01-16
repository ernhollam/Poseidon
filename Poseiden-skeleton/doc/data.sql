-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           10.4.21-MariaDB-log - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour demo
CREATE DATABASE IF NOT EXISTS `demo` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `demo`;

-- Listage de la structure de la table demo. bidlist
CREATE TABLE IF NOT EXISTS `bidlist` (
  `BidListId` tinyint(4) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `bidQuantity` double DEFAULT NULL,
  `askQuantity` double DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `bidListDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `commentary` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `creationName` varchar(125) DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `revisionName` varchar(125) DEFAULT NULL,
  `revisionDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dealName` varchar(125) DEFAULT NULL,
  `dealType` varchar(125) DEFAULT NULL,
  `sourceListId` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`BidListId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Listage des données de la table demo.bidlist : ~7 rows (environ)
DELETE FROM `bidlist`;
/*!40000 ALTER TABLE `bidlist` DISABLE KEYS */;
INSERT INTO `bidlist` (`BidListId`, `account`, `type`, `bidQuantity`, `askQuantity`, `bid`, `ask`, `benchmark`, `bidListDate`, `commentary`, `security`, `status`, `trader`, `book`, `creationName`, `creationDate`, `revisionName`, `revisionDate`, `dealName`, `dealType`, `sourceListId`, `side`) VALUES
	(1, 'ab', 't', 1, NULL, NULL, NULL, NULL, '2022-12-06 16:19:50', NULL, NULL, NULL, NULL, NULL, NULL, '2022-12-06 16:19:50', NULL, '2022-12-06 16:19:50', NULL, NULL, NULL, NULL),
	(3, 'accasas', 'type', 3, NULL, NULL, NULL, NULL, '2022-12-22 11:06:33', NULL, NULL, NULL, NULL, NULL, NULL, '2022-12-22 11:06:33', NULL, '2022-12-22 11:06:33', NULL, NULL, NULL, NULL),
	(5, 'account', 'type3', 3, NULL, NULL, NULL, NULL, '2022-12-22 10:52:10', NULL, NULL, NULL, NULL, NULL, NULL, '2022-12-22 10:52:03', NULL, '2022-12-22 10:52:10', NULL, NULL, NULL, NULL),
	(7, 'acc7', 'type7', 9.4, NULL, NULL, NULL, NULL, '2023-01-02 17:02:06', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-02 17:02:06', NULL, '2023-01-02 17:02:06', NULL, NULL, NULL, NULL),
	(9, 'account', 'type', 7.8, NULL, NULL, NULL, NULL, '2023-01-09 17:03:39', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-09 17:03:39', NULL, '2023-01-09 17:03:39', NULL, NULL, NULL, NULL),
	(10, 'avec plusieurs validations', 'type', 1.5, NULL, NULL, NULL, NULL, '2023-01-09 15:12:54', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-09 15:12:54', NULL, '2023-01-09 15:12:54', NULL, NULL, NULL, NULL),
	(11, 'plusieurs validations', 'type plusieurs validations', 4.5, NULL, NULL, NULL, NULL, '2023-01-09 17:03:17', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-09 17:03:17', NULL, '2023-01-09 17:03:17', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `bidlist` ENABLE KEYS */;

-- Listage de la structure de la table demo. curvepoint
CREATE TABLE IF NOT EXISTS `curvepoint` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `CurveId` tinyint(4) DEFAULT NULL,
  `asOfDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `term` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Listage des données de la table demo.curvepoint : ~2 rows (environ)
DELETE FROM `curvepoint`;
/*!40000 ALTER TABLE `curvepoint` DISABLE KEYS */;
INSERT INTO `curvepoint` (`Id`, `CurveId`, `asOfDate`, `term`, `value`, `creationDate`) VALUES
	(1, 13, '2022-12-22 11:51:59', 12, 14, '2022-12-22 11:51:59'),
	(4, 4, '2023-01-10 14:39:25', 10, 5, '2022-12-22 12:03:08'),
	(5, 4, '2023-01-10 14:39:23', 11, 5.5, '2023-01-09 15:13:24');
/*!40000 ALTER TABLE `curvepoint` ENABLE KEYS */;

-- Listage de la structure de la table demo. rating
CREATE TABLE IF NOT EXISTS `rating` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `moodysRating` varchar(125) DEFAULT NULL,
  `sandPRating` varchar(125) DEFAULT NULL,
  `fitchRating` varchar(125) DEFAULT NULL,
  `orderNumber` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Listage des données de la table demo.rating : ~3 rows (environ)
DELETE FROM `rating`;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` (`Id`, `moodysRating`, `sandPRating`, `fitchRating`, `orderNumber`) VALUES
	(2, 'Aa3', 'AA-', 'AA-', 3),
	(4, 'Caa1', 'CCC+', 'CCC', 9),
	(5, 'Baa1', 'BBB+', 'BBB+', 9);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;

-- Listage de la structure de la table demo. rulename
CREATE TABLE IF NOT EXISTS `rulename` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) DEFAULT NULL,
  `description` varchar(125) DEFAULT NULL,
  `json` varchar(125) DEFAULT NULL,
  `template` varchar(512) DEFAULT NULL,
  `sqlStr` varchar(125) DEFAULT NULL,
  `sqlPart` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Listage des données de la table demo.rulename : ~3 rows (environ)
DELETE FROM `rulename`;
/*!40000 ALTER TABLE `rulename` DISABLE KEYS */;
INSERT INTO `rulename` (`Id`, `name`, `description`, `json`, `template`, `sqlStr`, `sqlPart`) VALUES
	(1, 'name', 'desc', 'json', 'template', 'selct blabla', '1:25'),
	(3, 'rule1', 'description', 'json', 'template', 'select * from rule;', 'part 10'),
	(5, 'grqg ', 'fgdsgvr', '\'"qf ', 'vfsd', ' gqq', '2e validation');
/*!40000 ALTER TABLE `rulename` ENABLE KEYS */;

-- Listage de la structure de la table demo. trade
CREATE TABLE IF NOT EXISTS `trade` (
  `TradeId` tinyint(4) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `buyQuantity` double DEFAULT NULL,
  `sellQuantity` double DEFAULT NULL,
  `buyPrice` double DEFAULT NULL,
  `sellPrice` double DEFAULT NULL,
  `tradeDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `security` varchar(125) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `creationName` varchar(125) DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `revisionName` varchar(125) DEFAULT NULL,
  `revisionDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dealName` varchar(125) DEFAULT NULL,
  `dealType` varchar(125) DEFAULT NULL,
  `sourceListId` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`TradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Listage des données de la table demo.trade : ~5 rows (environ)
DELETE FROM `trade`;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` (`TradeId`, `account`, `type`, `buyQuantity`, `sellQuantity`, `buyPrice`, `sellPrice`, `tradeDate`, `security`, `status`, `trader`, `benchmark`, `book`, `creationName`, `creationDate`, `revisionName`, `revisionDate`, `dealName`, `dealType`, `sourceListId`, `side`) VALUES
	(1, 'acctrade', 'type trade', 3, NULL, NULL, NULL, '2022-12-06 21:55:15', NULL, NULL, NULL, NULL, NULL, NULL, '2022-12-06 21:55:15', NULL, '2022-12-06 21:55:15', NULL, NULL, NULL, NULL),
	(3, 'nina', 'anin', 3, NULL, NULL, NULL, '2022-12-22 14:25:53', NULL, NULL, NULL, NULL, NULL, NULL, '2022-12-22 14:25:53', NULL, '2022-12-22 14:25:53', NULL, NULL, NULL, NULL),
	(7, 'account non vide', 'update plusieurs validations', 4.5, NULL, NULL, NULL, '2023-01-09 15:14:41', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-09 15:14:41', NULL, '2023-01-09 15:14:41', NULL, NULL, NULL, NULL),
	(11, 'triple validation avec erreurs', 'double validation', 4.5, NULL, NULL, NULL, '2023-01-09 16:59:38', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-09 16:59:38', NULL, '2023-01-09 16:59:38', NULL, NULL, NULL, NULL),
	(12, 'ac2', 'typ2', 3, NULL, NULL, NULL, '2023-01-10 16:33:14', NULL, NULL, NULL, NULL, NULL, NULL, '2023-01-10 16:33:14', NULL, '2023-01-10 16:33:14', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;

-- Listage de la structure de la table demo. users
CREATE TABLE IF NOT EXISTS `users` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `username` varchar(125) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `fullname` varchar(125) DEFAULT NULL,
  `role` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Listage des données de la table demo.users : ~2 rows (environ)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`Id`, `username`, `password`, `fullname`, `role`) VALUES
	(1, 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'Administrator', 'ADMIN'),
	(2, 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'User', 'USER'),
	(3, 'test', '$2a$10$X4cF4U3zx4/MrcjywJ1ZEOVdeay4sLyqTJzYe9A2Ub0ZGLMfdXXQm', 'test user', 'ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
