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
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'pinco','pallino','manuel123ryan@gmail.com','abcdef','roma','3334242421','1995-04-27','Studente di Informatica','0.6960467759415614'),(2,'andrea','perna','davideiacobs@gmail.com','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Fisica','76255602.jpg'),(3,'luca','bale','lb@gmail.it','abcdef','Roma','3456789012','1995-04-27','Laurea Base in Matematica','201556006.jpg'),(4,'davide','iacobelli','davideiacobs@gmail.it','abcdef','','','1995-04-27','','930668455.jpg'),(5,'pinco','pallino','pppp@gmail.it','abcdef','Pescara','333333333323','1995-04-27','Laurea Base in Matematica','profilo2.png'),(6,'davide','iacobelli','fwoiepjmfv@e','123','Sora','33333333333333','2017-01-01','','defaultImg.jpg'),(7,'Davide','iacobelli','davideiacobs@gmail.com','123','Sora','3347624576','1995-04-27','','defaultImg.jpg'),(8,'fewf','efwefwe','wefwef','fwefwef','fwefw','efwefwefwe','2017-01-01','','defaultImg.jpg'),(9,'manuel','di pietro ','manuel@top.it','123','teramo','382374374','2017-01-01','','defaultImg.jpg'),(10,'manuel','di pietro ','manuellll@top.it','123','teramo','382374374','2017-01-01','','defaultImg.jpg'),(11,'Davide','Iacobelli','davideiacobelli@hotmail.it','123','Sora','34343443434','2017-01-01','','defaultImg.jpg'),(12,'davide','iacobelli','fdwefc@fve','123','sora','3333','2017-01-01','','defaultImg.jpg'),(13,'davide','iacobelli','davideiewfcrobs@gmail.it','123','sora','342134242343','2017-01-01','','defaultImg.jpg'),(68,'thjrt','rtjtj','rgfvr@.it','123','errgerg','213123123123123123','1999-01-01','fgwre','defaultImg.jpg'),(69,'ameche','12','ameche12@gmail.it','123e','amecheland','1234567890','1999-01-01','weokfmn','defaultImg.jpg'),(70,'ef','uioedbfg','sestersi@gmail.it','123','gew','231231231231231231','1999-01-01','fwefwe','defaultImg.jpg'),(71,'diofunziona','evviva','regerger@rge.it','123','rwegre','123123123123123','1999-01-01','grregerge','0.8087805009727116'),(72,'paolo','rossi','paolorossi@gmail.it','123','Milano','1234567890','1993-01-10','Ingegneria delle Telecomunicazioni','defaultImg.jpg');
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

-- Dump completed on 2017-07-19  0:29:35
