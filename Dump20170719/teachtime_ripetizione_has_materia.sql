CREATE DATABASE  IF NOT EXISTS `teachtime` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `teachtime`;
-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: teachtime
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.17.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ripetizione_has_materia`
--

DROP TABLE IF EXISTS `ripetizione_has_materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ripetizione_has_materia` (
  `ripetizione_ID` int(10) NOT NULL,
  `materia_ID` int(10) NOT NULL,
  PRIMARY KEY (`ripetizione_ID`,`materia_ID`),
  KEY `fk_ripetizione_has_materia_materia2_idx` (`materia_ID`),
  KEY `fk_ripetizione_has_materia_ripetizione2_idx` (`ripetizione_ID`),
  CONSTRAINT `fk_ripetizione_has_materia_materia2` FOREIGN KEY (`materia_ID`) REFERENCES `materia` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ripetizione_has_materia_ripetizione2` FOREIGN KEY (`ripetizione_ID`) REFERENCES `ripetizione` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ripetizione_has_materia`
--

LOCK TABLES `ripetizione_has_materia` WRITE;
/*!40000 ALTER TABLE `ripetizione_has_materia` DISABLE KEYS */;
INSERT INTO `ripetizione_has_materia` VALUES (43,1),(44,1),(45,1),(68,1),(38,2),(51,2),(18,3),(53,3),(63,3),(19,4),(41,4),(65,4),(87,4),(48,9),(52,10),(92,11),(93,13),(46,14),(47,14),(49,14),(50,15),(40,16),(54,17),(63,17),(67,18),(87,19),(87,22),(63,23),(66,23),(67,25),(65,26),(65,27),(66,28),(67,29),(87,36),(87,37);
/*!40000 ALTER TABLE `ripetizione_has_materia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-19  0:29:35
