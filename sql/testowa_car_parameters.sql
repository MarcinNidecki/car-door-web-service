CREATE DATABASE  IF NOT EXISTS `testowa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `testowa`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: testowa
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car_parameters`
--

DROP TABLE IF EXISTS `car_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_parameters` (
  `id` bigint(20) NOT NULL,
  `air_conditioning` tinyint(4) DEFAULT NULL,
  `all_wheel_drive` tinyint(4) DEFAULT NULL,
  `big_bags` int(11) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `doors_number` int(11) DEFAULT NULL,
  `fuel_type` varchar(255) DEFAULT NULL,
  `seats_number` int(11) DEFAULT NULL,
  `small_bags` int(11) DEFAULT NULL,
  `transmission_automatic` tinyint(4) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `car_picture_id` bigint(20) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrgdjopbqermpnp9kb5ipyuh7a` (`car_picture_id`),
  KEY `FK6p56st2ywdmspfbw9buc78dtq` (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_parameters`
--

LOCK TABLES `car_parameters` WRITE;
/*!40000 ALTER TABLE `car_parameters` DISABLE KEYS */;
INSERT INTO `car_parameters` VALUES (155,1,1,2,'white',2,'ON',2,2,1,2010,139,1),(157,1,0,1,'white',1,'ON',1,1,1,2010,139,1),(159,1,0,1,'Brw',1,'ON',1,1,1,2010,139,1),(153,1,1,2,'BLACK',2,'PB',4,4,1,2018,139,1),(161,1,1,3,'Brww',1,'ON',1,1,0,2010,139,1),(163,1,0,1,'Brw',1,'ON',1,1,0,2010,139,2),(190,1,1,1,'Brw',1,'ON',1,1,1,2010,139,2),(544,1,1,3,'Black',5,'ON',5,2,1,2019,539,1),(546,1,1,2,'Red',1,'PB',5,2,0,2017,540,1),(548,1,0,4,'Red',5,'ON',7,1,0,2016,541,1),(550,0,0,2,'Red',3,'HYBRID',2,3,0,2015,538,1),(552,1,1,2,'Gray',3,'LPG',4,4,0,2016,536,542),(554,1,1,2,'White',5,'ELECTRIC',5,1,0,2015,537,2),(651,0,0,1,'white',1,'ON',1,1,1,2015,536,1);
/*!40000 ALTER TABLE `car_parameters` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13  1:27:10
