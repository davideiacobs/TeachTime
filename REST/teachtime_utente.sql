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
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `citta` varchar(45) DEFAULT 'NULL',
  `telefono` varchar(45) DEFAULT 'NULL',
  `data_di_nascita` date NOT NULL,
  `titolo_di_studio` varchar(250) DEFAULT 'NULL',
  `img_profilo` varchar(250) DEFAULT 'NULL',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'pinco','pallino','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(2,'andrea','perna','adp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(3,'luca','bale','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(4,'luca','bale','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(5,'luca','bale','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(6,'luca','bale','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(7,'abab','bebeb','ababebeb@gmail.it','abcdef','Firenze','3456789000','1998-08-19','Laurea Base in Fisica','profilo2.png'),(8,'pinco','pallino','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(9,'pinco','pallino','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(10,'pinco','pallino','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png'),(11,'pinco','pallino','pp@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Informatica','profilo.png');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-21 19:27:40
