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
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ripetizione`
--

LOCK TABLES `ripetizione` WRITE;
/*!40000 ALTER TABLE `ripetizione` DISABLE KEYS */;
INSERT INTO `ripetizione` VALUES (18,'casa mia ',9,'top','sora',2),(19,'casa tua',11,'top','l\'aquila',3),(38,'casa mia',12,'geoirhgierhogheo','Roma',1),(40,'biblioteca x',12,'blablablablablablabla','Roma',2),(41,'biblioteca xxxx',13,'blablablablablablabla','Bologna',2),(43,'biblioteca xxxx',9,'blablablablablablabla','Milano',2),(44,'biblioteca xxxx',9,'blablablablablablabla','Torino',2),(45,'biblioteca xxxx',13,'blablablablablablabla','Firenze',2),(46,'biblioteca comunale',12,'blablablalbalbalbalblalbsllsfvb','Pescara',2),(47,'biblioteca comunale',12,'blablablalbalbalbalblalbsllsfvb','Pescara',2),(48,'biblioteca comunale',12,'blablablalbalbalbalblalbsllsfvb','Pescara',2),(49,'dove vuoi',8,'blablabla','Ancona',2),(50,'dove vuoi',9,'blablabla descrizione blabla','Milano',1),(51,'biblio',13,'balbalbaslsvpeòr','Bari',1),(52,'boh',10,'descrizione blabla','Pisa',1),(53,'biblioteca comunale',11,'descrizione ripetizione ..','pescara',1),(54,'biblioteca',13,'descrizione blabla','L\'Aquila',1),(63,'biblioteca',12,'ewfiejfiejn','Pisa',1),(65,'dove vuoi ',9,'blablabla','lucca',1),(66,'dove vuoi ',12,'blablabla','Venezia',1),(67,'Sapienza',9,'blablablablabla','Roma',1),(68,'biblioteca comunale',8,'blablablabla','Sora',4),(87,'biblioteca',9,'descrizione aggiuntiva ripetizione','L\'Aquila',4),(92,'dove vuoi',12,'efiwpe','roma',1),(93,'dove vuoi',12,'einfwpnvwnv','roma',1);
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

-- Dump completed on 2017-07-19  0:29:35
