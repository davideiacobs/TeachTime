CREATE DATABASE  IF NOT EXISTS `teachtime` /*!40100 DEFAULT CHARACTER SET utf8 */;
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
-- Table structure for table `argomento`
--

DROP TABLE IF EXISTS `argomento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `argomento` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `nome` varchar(250) NOT NULL,
  `materia_ID` int(10) NOT NULL,
  PRIMARY KEY (`ID`,`materia_ID`),
  KEY `fk_argomento_materia1_idx` (`materia_ID`),
  CONSTRAINT `fk_argomento_materia1` FOREIGN KEY (`materia_ID`) REFERENCES `materia` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `argomento`
--

LOCK TABLES `argomento` WRITE;
/*!40000 ALTER TABLE `argomento` DISABLE KEYS */;
INSERT INTO `argomento` VALUES (1,'programmazione python',1),(2,'programmazione java',1),(3,'web',1),(4,'database',1),(5,'analisi 1',2),(6,'analisi 2',2),(7,'geometria piana',2),(8,'geometria piana',2),(9,'geometria solida',2),(10,'geometria solida',2),(11,'geometria solida',2),(12,'geometria solida',2),(13,'geometria solida',2),(14,'geometria solida',2),(15,'geometria solida',2),(16,'geometria solida',2),(17,'geometria solida',2),(18,'geometria solida',2),(19,'geometria solida',2),(20,'vita eterna',3),(21,'vita eterna',3),(22,'vita eterna',3),(23,'vita eterna',3),(24,'vita eterna',3),(25,'vita eterna',3),(26,'vita eterna',3),(27,'vita eterna',3),(28,'vita eterna',3),(29,'vita eterna',3),(30,'vita eterna',3),(31,'vita eterna',3),(32,'vita eterna',4),(33,'vita eterna',4),(34,'vita eterna',4),(35,'vita eterna',4),(36,'vita eterna',4);
/*!40000 ALTER TABLE `argomento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-16 12:32:21
