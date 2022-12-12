-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: groups
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `password` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1234);
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` int DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `points` double DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Микита','Іващук',197.6,'Budget'),(2,'Тарас','Гриць',183.2,'Budget'),(3,'Михайло','Руденко',140.8,'Contract'),(4,'Ілля','Лядський',167.7,'Contract'),(5,'Микита','Іваненко',172,'Budget'),(6,'Владислав','Шарипов',150.9,'Contract'),(7,'Артем','Бернатович',188.3,'Budget'),(8,'Сергій','Ткач',138.6,'Contract'),(9,'Богдан','Тиченко',163,'Contract'),(10,'Данил','Качур',178.5,'Budget'),(11,'Іван','Лібченко',169,'Contract'),(12,'Єгор','Хвистонюк',175.5,'Budget'),(13,'Дмитро','Динюк',194.7,'Budget'),(14,'Володимир','Ликрутий',186.4,'Budget'),(15,'Світлана','Тюпа',167.3,'Contract'),(16,'Марія','Безценна',147,'Contract'),(17,'Владислава','Щербина',124.7,'Contract'),(18,'Станіслав','Черноус',166.9,'Contract'),(19,'Надія','Корнуш',190.3,'Budget'),(20,'Констянтин','Киричок',159.8,'Contract'),(21,'Ярослав','Левицький',166,'Contract'),(22,'Кирил','Приходько',193.2,'Budget'),(23,'В\'ячеслав','Петренко',176.8,'Budget'),(24,'Роман','Косенко',180.8,'Budget'),(25,'Антон','Шарипов',152.6,'Contract'),(26,'Максим','Антіпов',149.8,'Contract'),(27,'Віктор','Коновалов',141.1,'Contract'),(28,'Віталій','Киричок',159.8,'Contract'),(29,'Микола','Тимченко',151.1,'Contract'),(30,'Борис','Жуков',158.6,'Contract'),(31,'Дмитро','Лущенко',182.7,'Budget'),(32,'Андрій','Зябрев',200,'Budget'),(33,'Григорій','Таран',155.2,'Contract'),(34,'Анастасія','Безгузова',184.3,'Budget'),(35,'Ілля','Ковальов',167.8,'Contract'),(36,'Олег','Зінченко',180,'Budget'),(37,'Єгор','Іващенко',166.3,'Contract'),(38,'Олексій','Сидорчук',152.5,'Contract'),(39,'Назар','Олійник',164.2,'Contract'),(40,'Микита','Мороз',148,'Contract');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 19:05:43
