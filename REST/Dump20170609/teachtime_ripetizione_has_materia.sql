CREATE DATABASE  IF NOT EXISTS `teachtime` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `teachtime`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: teachtime
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
INSERT INTO `ripetizione_has_materia` VALUES (15,1),(17,1),(25,1),(26,1),(27,1),(28,1),(18,3),(25,3),(26,3),(27,3),(28,3),(19,4),(24,9),(24,10);
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

-- Dump completed on 2017-06-09 18:28:54
