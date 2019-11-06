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
-- Table structure for table `car_pictures`
--

DROP TABLE IF EXISTS `car_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_pictures` (
  `id` bigint(20) NOT NULL,
  `created_date` date DEFAULT NULL,
  `descriptions` varchar(255) DEFAULT NULL,
  `file_extension` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_name_path` varchar(255) DEFAULT NULL,
  `thumbnails_name` varchar(255) DEFAULT NULL,
  `thumbnails_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_pictures`
--

LOCK TABLES `car_pictures` WRITE;
/*!40000 ALTER TABLE `car_pictures` DISABLE KEYS */;
INSERT INTO `car_pictures` VALUES (139,NULL,'Unknown','jpg','unknown','/img/car/unknown.jpg','unknown-small','/img/car/unknown-small.jpg'),(536,NULL,'Fiat 500 2018','jpg','Fiat-500','http://debowyzakatek.pl/images/Fiat-500.jpg','Fiat-500-small','http://debowyzakatek.pl/images/Fiat-500-small.jpg'),(537,NULL,'Ford Fokus Active','jpg','2020-ford-focus-active','http://debowyzakatek.pl/images/2020-ford-focus-active.jpg','2020-ford-focus-active-small','http://debowyzakatek.pl/images/2020-ford-focus-active-small.jpg'),(538,NULL,'Audi TT','jpg','2019-audi-tt-coupe-tango-red-front-quarter','http://debowyzakatek.pl/images/2019-audi-tt-coupe-tango-red-front-quarter.jpg','2019-audi-tt-coupe-tango-red-front-quarter-small','http://debowyzakatek.pl/images/2019-audi-tt-coupe-tango-red-front-quarter-small.jpg'),(539,NULL,'Kia Sorento ','jpg','2019-kia-sorento','http://debowyzakatek.pl/images/2019-kia-sorento.jpg','2019-kia-sorento-small','http://debowyzakatek.pl/images/2019-kia-sorento-small.jpg'),(540,NULL,'Kia Sportage','jpg','2020-sportage-cargo-room_o','http://debowyzakatek.pl/images/2020-sportage-cargo-room_o.jpg','2020-sportage-cargo-room_o-small','http://debowyzakatek.pl/images/2020-sportage-cargo-room_o-small.jpg'),(541,NULL,'Audi Q7','jpg','Audi Q7','http://debowyzakatek.pl/images/Audi Q7.jpg','Audi Q7-small','http://debowyzakatek.pl/images/Audi Q7-small.jpg');
/*!40000 ALTER TABLE `car_pictures` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13  1:27:07
