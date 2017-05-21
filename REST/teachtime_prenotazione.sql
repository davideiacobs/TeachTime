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
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prenotazione` (
  `ripetizione_ID` int(10) NOT NULL,
  `studente_ID` int(10) NOT NULL,
  `costo` int(11) NOT NULL,
  `descrizione` varchar(45) DEFAULT NULL,
  `stato` int(1) NOT NULL DEFAULT '0',
  `data` datetime DEFAULT NULL,
  `voto` int(1) NOT NULL DEFAULT '-1',
  `recensione` text,
  `materia_ID` int(10) NOT NULL,
  `materia_categoria_ID` int(10) NOT NULL,
  PRIMARY KEY (`ripetizione_ID`,`studente_ID`,`materia_ID`,`materia_categoria_ID`),
  KEY `fk_ripetizione_has_materia_ripetizione1_idx` (`ripetizione_ID`),
  KEY `fk_prenotazione_utente1_idx` (`studente_ID`),
  KEY `fk_prenotazione_materia1_idx` (`materia_ID`,`materia_categoria_ID`),
  CONSTRAINT `fk_prenotazione_materia1` FOREIGN KEY (`materia_ID`, `materia_categoria_ID`) REFERENCES `materia` (`ID`, `categoria_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prenotazione_utente1` FOREIGN KEY (`studente_ID`) REFERENCES `utente` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_ripetizione_has_materia_ripetizione1` FOREIGN KEY (`ripetizione_ID`) REFERENCES `ripetizione` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` VALUES (12,2,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',-1,NULL,1,1),(12,2,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',-1,NULL,2,1),(12,3,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',0,'',1,1),(13,3,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',4,'come andiamo',1,1),(14,1,12,'ripetizione descrizione',1,'2007-09-25 00:00:00',5,'eccellente',1,1),(14,2,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',-1,NULL,1,1),(14,3,12,'ripetizione descrizione',2,'2007-09-25 00:00:00',5,'ottimo',2,1),(15,5,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',-1,NULL,1,1),(15,5,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',0,'',2,1),(17,1,12,'ripetizione descrizione',0,'2007-09-25 17:40:00',0,'',2,1),(17,4,12,'ripetizione descrizione',0,'2007-09-25 17:40:00',-1,NULL,1,1),(17,5,12,'ripetizione descrizione',0,'2007-09-25 00:00:00',0,'',2,1),(19,2,11,'ripetizione descrizione',0,'2007-09-25 00:00:00',-1,NULL,1,1),(19,3,11,'ripetizione descrizione',0,'2007-09-25 00:00:00',0,'',1,1),(19,5,11,'ripetizione descrizione',0,'2007-09-25 00:00:00',-1,NULL,1,1),(20,1,12,'ripetizione descrizione',0,'2007-09-25 19:40:00',-1,NULL,2,1),(20,2,12,'ripetizione descrizione',0,'2007-09-25 18:40:00',-1,NULL,2,1),(20,3,12,'ripetizione descrizione',0,'2007-09-25 17:40:00',-1,NULL,2,1),(20,4,12,'ripetizione descrizione',0,'2007-09-25 15:40:00',-1,NULL,2,1),(21,4,12,'ripetizione descrizione',0,'2007-09-25 17:40:00',-1,NULL,1,1);
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;
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
