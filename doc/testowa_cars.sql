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
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `id` bigint(20) NOT NULL,
  `daily_price` decimal(19,2) DEFAULT NULL,
  `registration` varchar(255) DEFAULT NULL,
  `vehicle_status` varchar(255) DEFAULT NULL,
  `car_parameters_id` bigint(20) DEFAULT NULL,
  `city_id` bigint(20) DEFAULT NULL,
  `model_id` bigint(20) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa2cm6f26r8rqcy9n6kvc4xx78` (`car_parameters_id`),
  KEY `FK7f7dyfat8eyd7n1vg7ka74ayv` (`city_id`),
  KEY `FK3s7hbihtkqltx9ffij1ep3nd8` (`model_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (543,200.00,'WWA 213151','rdy',544,5,1,0),(547,130.00,'WAA 831313','rdy',548,5,527,0),(549,130.00,'WWA 51133','rdy',550,5,528,0),(551,70.00,'SK 214931','rdy',552,5,530,0),(553,75.00,'W13 121D31','rdy',554,5,533,0),(552,200.00,'WWA 213151','rdy',544,4,1,0),(560,110.00,'SZO W123WV','rdy',546,4,525,0),(554,130.00,'WAA 831313','rdy',548,4,527,0),(555,130.00,'WWA 51133','rdy',550,4,528,0),(556,70.00,'SK 214931','rdy',552,4,530,0),(557,75.00,'W13 121D31','rdy',554,4,533,0),(570,200.00,'WWA 213151','rdy',544,3,1,0),(571,110.00,'SZO W123WV','rdy',546,3,525,0),(572,130.00,'WAA 831313','rdy',548,3,527,0),(573,130.00,'WWA 51133','rdy',550,3,528,0),(574,70.00,'SK 214931','rdy',552,3,530,0),(575,75.00,'W13 121D31','rdy',554,3,533,0),(650,222.00,'SZA201231','rdy',651,5,1,0);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13  1:27:09
