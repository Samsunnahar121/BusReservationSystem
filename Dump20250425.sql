-- MySQL dump 10.13  Distrib 8.0.42, for macos15 (arm64)
--
-- Host: localhost    Database: bus_reservation
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `bus_id` int NOT NULL,
  `seats` int NOT NULL,
  `total_fare` decimal(10,2) NOT NULL,
  `booking_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`booking_id`),
  KEY `user_id` (`user_id`),
  KEY `bus_id` (`bus_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`bus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` (`booking_id`, `user_id`, `bus_id`, `seats`, `total_fare`, `booking_date`) VALUES (1,1,2,2,61.00,'2025-04-24 17:53:24'),(2,2,1,3,135.00,'2025-04-24 17:53:24'),(3,3,4,1,55.25,'2025-04-24 17:53:24'),(4,4,9,2,40.00,'2025-04-24 17:55:18'),(6,6,9,2,40.00,'2025-04-24 22:38:18'),(9,1,2,2,61.00,'2025-04-25 09:13:19'),(10,2,1,3,135.00,'2025-04-25 09:13:19'),(11,3,4,1,55.25,'2025-04-25 09:13:19');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buses`
--

DROP TABLE IF EXISTS `buses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buses` (
  `bus_id` int NOT NULL AUTO_INCREMENT,
  `bus_name` varchar(100) NOT NULL,
  `bus_type` varchar(50) NOT NULL,
  `source` varchar(100) NOT NULL,
  `destination` varchar(100) NOT NULL,
  `fare` decimal(10,2) NOT NULL,
  PRIMARY KEY (`bus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buses`
--

LOCK TABLES `buses` WRITE;
/*!40000 ALTER TABLE `buses` DISABLE KEYS */;
INSERT INTO `buses` (`bus_id`, `bus_name`, `bus_type`, `source`, `destination`, `fare`) VALUES (1,'City Express','AC','New York','Boston',45.00),(2,'Metro Travels','Non-AC','Chicago','Detroit',30.50),(3,'Golden Arrow','Sleeper','Los Angeles','San Francisco',85.75),(4,'Coastal Cruiser','AC','Miami','Orlando',55.25),(5,'City Express','AC','New York','Boston',45.00),(6,'Metro Travels','Non-AC','Chicago','Detroit',30.50),(7,'Golden Arrow','Sleeper','Los Angeles','San Francisco',85.75),(8,'Coastal Cruiser','AC','Miami','Orlando',55.25),(9,'Local Service','Non-AC','Dattapara','Daffodil',20.00),(10,'City Express','AC','New York','Boston',45.00),(11,'Metro Travels','Non-AC','Chicago','Detroit',30.50),(12,'Golden Arrow','Sleeper','Los Angeles','San Francisco',85.75),(13,'Coastal Cruiser','AC','Miami','Orlando',55.25),(14,'City Express','AC','New York','Boston',45.00),(15,'Metro Travels','Non-AC','Chicago','Detroit',30.50),(16,'Golden Arrow','Sleeper','Los Angeles','San Francisco',85.75),(17,'Coastal Cruiser','AC','Miami','Orlando',55.25),(18,'Local Service','Non-AC','Dattapara','Daffodil',20.00);
/*!40000 ALTER TABLE `buses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `full_name`, `email`, `password`) VALUES (1,'Alice Johnson','alice@example.com','alice123'),(2,'Bob Smith','bob@example.com','bob@secure'),(3,'Charlie Brown','charlie@example.com','charlie789'),(4,'sams','sams12@gmail.com','1234'),(5,'Samsun Nahar','samsunnahar2305341699@diu.edu.bd','1234'),(6,'Samsun Nahar','samsunnaharsania50@gmail.com','1234'),(8,'Samsunnahar','samsunnahar23@gmail.com','1234'),(9,'Samsunnahar','sams23@gmail.com','1234'),(10,'samsunnahar','samsunnahar@gmail.com','1234'),(11,'sasm','sasm','sas'),(15,'sams','sas','sas');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-25 16:25:53
