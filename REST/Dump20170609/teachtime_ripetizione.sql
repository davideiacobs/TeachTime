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
-- Table structure for table `ripetizione`
--

DROP TABLE IF EXISTS `ripetizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ripetizione` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `luogo_incontro` varchar(250) DEFAULT 'a scelta dello studente',
  `costo_per_ora` int(11) NOT NULL,
  `descrizione` text,
  `citta` varchar(45) NOT NULL,
  `tutor_ID` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ripetizione_utente_idx` (`tutor_ID`),
  CONSTRAINT `fk_ripetizione_utente` FOREIGN KEY (`tutor_ID`) REFERENCES `utente` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ripetizione`
--

LOCK TABLES `ripetizione` WRITE;
/*!40000 ALTER TABLE `ripetizione` DISABLE KEYS */;
INSERT INTO `ripetizione` VALUES (9,'a scelta dello studente',12,'top','roma',1),(10,'a scelta dello studente',12,'top','roma',1),(11,'a scelta dello studente',12,'top','roma',1),(12,'a scelta dello studente',12,'top','roma',1),(13,'a scelta dello studente',12,'top','roma',1),(14,'a scelta dello studente',12,'top','roma',1),(15,'a scelta dello studente',12,'top','roma',1),(17,'a scelta dello studente',12,'top','roma',1),(18,'casa mia ',9,'top','sora',2),(19,'casa tua',11,'top','l\'aquila',3),(20,'a scelta dello studente',12,'descrizione della ripetizione','Roma',7),(21,'a scelta dello studente',12,'descrizione della ripetizione','Roma',7),(22,'a scelta dello studente',12,'descrizione della ripetizione','Firenze',7),(23,'a scelta dello studente',12,'descrizione della ripetizione','Firenze',7),(24,'a scelta dello studente',12,'descrizione della ripetizione','Firenze',7),(25,'a scelta dello studente',12,'descrizione della ripetizione','Roma',1),(26,'a scelta dello studente',12,'descrizione della ripetizione','Roma',1),(27,'a scelta dello studente',12,'descrizione della ripetizione','Roma',1),(28,'a scelta dello studente',12,'descrizione della ripetizione','Roma',1);
/*!40000 ALTER TABLE `ripetizione` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-09 18:28:52
