-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: erp
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `erp`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `erp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `erp`;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `username` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES ('rose','rose'),('sam','sam');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `code` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `credits` int NOT NULL,
  `instructor` varchar(100) NOT NULL,
  `quiz` int NOT NULL DEFAULT '0',
  `assignment` int NOT NULL DEFAULT '0',
  `midsem` int NOT NULL DEFAULT '0',
  `endsem` int NOT NULL DEFAULT '0',
  `group_project` int NOT NULL DEFAULT '0',
  `course_description` text,
  `seats` int NOT NULL,
  `section` varchar(20) NOT NULL DEFAULT 'A',
  `id` int DEFAULT NULL,
  UNIQUE KEY `unique_code_section` (`code`,`section`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('AUTO101','Automated Course 1',4,'Mickey',20,10,30,40,0,'Description for Automated Course 1',49,'A',NULL),('AUTO102','Automated Course 2',2,'qty',20,10,30,40,0,'Description for Automated Course 2',40,'A',NULL),('AUTO103','Automated Course 3',3,'qty',20,10,30,40,0,'Description for Automated Course 3',38,'A',NULL),('AUTO104','Automated Course 4',4,'Mickey',20,10,30,40,0,'Description for Automated Course 4',42,'A',NULL),('AUTO105','Automated Course 5',2,'Mickey',20,10,30,40,0,'Description for Automated Course 5',46,'A',NULL),('AUTO106','Automated Course 6',2,'qty',20,10,30,40,0,'Description for Automated Course 6',57,'A',NULL),('AUTO107','Automated Course 7',3,'qty',20,10,30,40,0,'Description for Automated Course 7',40,'A',NULL),('AUTO108','Automated Course 8',2,'qty',20,10,30,40,0,'Description for Automated Course 8',55,'A',NULL),('AUTO109','Automated Course 9',4,'Mickey',20,10,30,40,0,'Description for Automated Course 9',40,'A',NULL),('AUTO110','Automated Course 10',3,'qty',20,10,30,40,0,'Description for Automated Course 10',47,'A',NULL),('AUTO111','Automated Course 11',4,'Mickey',20,10,30,40,0,'Description for Automated Course 11',48,'A',NULL),('AUTO112','Automated Course 12',3,'Mickey',20,10,30,40,0,'Description for Automated Course 12',32,'A',NULL),('AUTO113','Automated Course 13',3,'Mickey',20,10,30,40,0,'Description for Automated Course 13',59,'A',NULL),('AUTO114','Automated Course 14',3,'qty',20,10,30,40,0,'Description for Automated Course 14',30,'A',NULL),('AUTO115','Automated Course 15',3,'Mickey',20,10,30,40,0,'Description for Automated Course 15',47,'A',NULL),('AUTO116','Automated Course 16',4,'qty',20,10,30,40,0,'Description for Automated Course 16',49,'A',NULL),('AUTO117','Automated Course 17',3,'qty',20,10,30,40,0,'Description for Automated Course 17',43,'A',NULL),('AUTO118','Automated Course 18',4,'Mickey',20,10,30,40,0,'Description for Automated Course 18',58,'A',NULL),('AUTO119','Automated Course 19',4,'Mickey',20,10,30,40,0,'Description for Automated Course 19',45,'A',NULL),('AUTO120','Automated Course 20',3,'Mickey',20,10,30,40,0,'Description for Automated Course 20',46,'A',NULL),('AUTO121','Automated Course 21',2,'qty',20,10,30,40,0,'Description for Automated Course 21',59,'A',NULL),('AUTO122','Automated Course 22',4,'qty',20,10,30,40,0,'Description for Automated Course 22',32,'A',NULL),('AUTO123','Automated Course 23',4,'qty',20,10,30,40,0,'Description for Automated Course 23',55,'A',NULL),('AUTO124','Automated Course 24',4,'Mickey',20,10,30,40,0,'Description for Automated Course 24',56,'A',NULL),('AUTO125','Automated Course 25',4,'qty',20,10,30,40,0,'Description for Automated Course 25',43,'A',NULL),('AUTO126','Automated Course 26',2,'qty',20,10,30,40,0,'Description for Automated Course 26',35,'A',NULL),('AUTO127','Automated Course 27',2,'Mickey',20,10,30,40,0,'Description for Automated Course 27',39,'A',NULL),('AUTO128','Automated Course 28',2,'qty',20,10,30,40,0,'Description for Automated Course 28',45,'A',NULL),('AUTO129','Automated Course 29',4,'qty',20,10,30,40,0,'Description for Automated Course 29',51,'A',NULL),('AUTO130','Automated Course 30',3,'qty',20,10,30,40,0,'Description for Automated Course 30',57,'A',NULL),('AUTO131','Automated Course 31',4,'Mickey',20,10,30,40,0,'Description for Automated Course 31',30,'A',NULL),('AUTO132','Automated Course 32',3,'qty',20,10,30,40,0,'Description for Automated Course 32',57,'A',NULL),('AUTO133','Automated Course 33',2,'qty',20,10,30,40,0,'Description for Automated Course 33',39,'A',NULL),('AUTO134','Automated Course 34',3,'Mickey',20,10,30,40,0,'Description for Automated Course 34',38,'A',NULL),('AUTO135','Automated Course 35',2,'Mickey',20,10,30,40,0,'Description for Automated Course 35',50,'A',NULL),('AUTO136','Automated Course 36',2,'Mickey',20,10,30,40,0,'Description for Automated Course 36',54,'A',NULL),('AUTO137','Automated Course 37',4,'Mickey',20,10,30,40,0,'Description for Automated Course 37',55,'A',NULL),('AUTO138','Automated Course 38',4,'qty',20,10,30,40,0,'Description for Automated Course 38',47,'A',NULL),('AUTO139','Automated Course 39',4,'Mickey',20,10,30,40,0,'Description for Automated Course 39',48,'A',NULL),('AUTO140','Automated Course 40',3,'Mickey',20,10,30,40,0,'Description for Automated Course 40',43,'A',NULL),('AUTO141','Automated Course 41',4,'Mickey',20,10,30,40,0,'Description for Automated Course 41',49,'A',NULL),('AUTO142','Automated Course 42',3,'Mickey',20,10,30,40,0,'Description for Automated Course 42',50,'A',NULL),('AUTO143','Automated Course 43',4,'Mickey',20,10,30,40,0,'Description for Automated Course 43',52,'A',NULL),('AUTO144','Automated Course 44',3,'Mickey',20,10,30,40,0,'Description for Automated Course 44',41,'A',NULL),('AUTO145','Automated Course 45',4,'qty',20,10,30,40,0,'Description for Automated Course 45',59,'A',NULL),('AUTO146','Automated Course 46',3,'qty',20,10,30,40,0,'Description for Automated Course 46',58,'A',NULL),('AUTO147','Automated Course 47',3,'Mickey',20,10,30,40,0,'Description for Automated Course 47',33,'A',NULL),('AUTO148','Automated Course 48',3,'qty',20,10,30,40,0,'Description for Automated Course 48',42,'A',NULL),('AUTO149','Automated Course 49',4,'qty',20,10,30,40,0,'Description for Automated Course 49',38,'A',NULL),('AUTO150','Automated Course 50',3,'qty',20,10,30,40,0,'Description for Automated Course 50',52,'A',NULL),('AUTO151','Automated Course 51',2,'Mickey',20,10,30,40,0,'Description for Automated Course 51',60,'A',NULL),('AUTO152','Automated Course 52',2,'qty',20,10,30,40,0,'Description for Automated Course 52',54,'A',NULL),('AUTO153','Automated Course 53',4,'qty',20,10,30,40,0,'Description for Automated Course 53',42,'A',NULL),('AUTO154','Automated Course 54',3,'qty',20,10,30,40,0,'Description for Automated Course 54',35,'A',NULL),('AUTO155','Automated Course 55',2,'Mickey',20,10,30,40,0,'Description for Automated Course 55',38,'A',NULL),('AUTO156','Automated Course 56',4,'Mickey',20,10,30,40,0,'Description for Automated Course 56',36,'A',NULL),('AUTO157','Automated Course 57',2,'Mickey',20,10,30,40,0,'Description for Automated Course 57',36,'A',NULL),('AUTO158','Automated Course 58',3,'Mickey',20,10,30,40,0,'Description for Automated Course 58',55,'A',NULL),('AUTO159','Automated Course 59',2,'qty',20,10,30,40,0,'Description for Automated Course 59',44,'A',NULL),('AUTO160','Automated Course 60',2,'qty',20,10,30,40,0,'Description for Automated Course 60',46,'A',NULL),('AUTO161','Automated Course 61',4,'Mickey',20,10,30,40,0,'Description for Automated Course 61',50,'A',NULL),('AUTO162','Automated Course 62',3,'qty',20,10,30,40,0,'Description for Automated Course 62',44,'A',NULL),('AUTO163','Automated Course 63',4,'qty',20,10,30,40,0,'Description for Automated Course 63',46,'A',NULL),('AUTO164','Automated Course 64',2,'Mickey',20,10,30,40,0,'Description for Automated Course 64',51,'A',NULL),('AUTO165','Automated Course 65',2,'Mickey',20,10,30,40,0,'Description for Automated Course 65',58,'A',NULL),('AUTO166','Automated Course 66',4,'Mickey',20,10,30,40,0,'Description for Automated Course 66',43,'A',NULL),('AUTO167','Automated Course 67',2,'Mickey',20,10,30,40,0,'Description for Automated Course 67',37,'A',NULL),('AUTO168','Automated Course 68',4,'Mickey',20,10,30,40,0,'Description for Automated Course 68',45,'A',NULL),('AUTO169','Automated Course 69',4,'qty',20,10,30,40,0,'Description for Automated Course 69',58,'A',NULL),('AUTO170','Automated Course 70',3,'qty',20,10,30,40,0,'Description for Automated Course 70',31,'A',NULL),('AUTO171','Automated Course 71',2,'qty',20,10,30,40,0,'Description for Automated Course 71',36,'A',NULL),('AUTO172','Automated Course 72',4,'qty',20,10,30,40,0,'Description for Automated Course 72',59,'A',NULL),('AUTO173','Automated Course 73',2,'Mickey',20,10,30,40,0,'Description for Automated Course 73',36,'A',NULL),('AUTO174','Automated Course 74',3,'qty',20,10,30,40,0,'Description for Automated Course 74',40,'A',NULL),('AUTO175','Automated Course 75',4,'qty',20,10,30,40,0,'Description for Automated Course 75',44,'A',NULL),('AUTO176','Automated Course 76',4,'qty',20,10,30,40,0,'Description for Automated Course 76',47,'A',NULL),('AUTO177','Automated Course 77',2,'qty',20,10,30,40,0,'Description for Automated Course 77',46,'A',NULL),('AUTO178','Automated Course 78',2,'qty',20,10,30,40,0,'Description for Automated Course 78',30,'A',NULL),('AUTO179','Automated Course 79',4,'qty',20,10,30,40,0,'Description for Automated Course 79',36,'A',NULL),('AUTO180','Automated Course 80',2,'qty',20,10,30,40,0,'Description for Automated Course 80',47,'A',NULL),('AUTO181','Automated Course 81',4,'qty',20,10,30,40,0,'Description for Automated Course 81',53,'A',NULL),('AUTO182','Automated Course 82',4,'qty',20,10,30,40,0,'Description for Automated Course 82',46,'A',NULL),('AUTO183','Automated Course 83',3,'qty',20,10,30,40,0,'Description for Automated Course 83',36,'A',NULL),('AUTO184','Automated Course 84',2,'qty',20,10,30,40,0,'Description for Automated Course 84',40,'A',NULL),('AUTO185','Automated Course 85',2,'Mickey',20,10,30,40,0,'Description for Automated Course 85',54,'A',NULL),('AUTO186','Automated Course 86',2,'Mickey',20,10,30,40,0,'Description for Automated Course 86',52,'A',NULL),('AUTO187','Automated Course 87',3,'Mickey',20,10,30,40,0,'Description for Automated Course 87',51,'A',NULL),('AUTO188','Automated Course 88',3,'qty',20,10,30,40,0,'Description for Automated Course 88',60,'A',NULL),('AUTO189','Automated Course 89',2,'Mickey',20,10,30,40,0,'Description for Automated Course 89',31,'A',NULL),('AUTO190','Automated Course 90',3,'Mickey',20,10,30,40,0,'Description for Automated Course 90',35,'A',NULL),('AUTO191','Automated Course 91',2,'Mickey',20,10,30,40,0,'Description for Automated Course 91',53,'A',NULL),('AUTO192','Automated Course 92',3,'qty',20,10,30,40,0,'Description for Automated Course 92',58,'A',NULL),('AUTO193','Automated Course 93',4,'Mickey',20,10,30,40,0,'Description for Automated Course 93',59,'A',NULL),('AUTO194','Automated Course 94',4,'Mickey',20,10,30,40,0,'Description for Automated Course 94',52,'A',NULL),('AUTO195','Automated Course 95',4,'qty',20,10,30,40,0,'Description for Automated Course 95',58,'A',NULL),('AUTO196','Automated Course 96',3,'qty',20,10,30,40,0,'Description for Automated Course 96',33,'A',NULL),('AUTO197','Automated Course 97',4,'qty',20,10,30,40,0,'Description for Automated Course 97',48,'A',NULL),('AUTO198','Automated Course 98',3,'qty',20,10,30,40,0,'Description for Automated Course 98',59,'A',NULL),('AUTO199','Automated Course 99',2,'Mickey',20,10,30,40,0,'Description for Automated Course 99',37,'A',NULL),('AUTO200','Automated Course 100',3,'Mickey',20,10,30,40,0,'Description for Automated Course 100',49,'A',NULL),('ECO 333','Macro Economics',4,'Mickey',10,40,20,30,0,'Youtube se padho pls',3,'A',NULL),('ECO 333','Macro Economics',2,'qty',31,0,29,40,0,'diff section boui',2,'B',NULL),('MTH 101','Linear Algebra',4,'Mickey',10,30,20,40,0,'9 cg nhi aayi yaar',2,'A',NULL),('MTH 101','Linear Algebra',2,'qty',25,25,25,25,0,'bbhbhbhbhb',1,'B',NULL),('test','Test',3,'Mickey',100,0,0,0,0,'nfnnf',2,'A',NULL);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollments`
--

DROP TABLE IF EXISTS `enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_user_name` varchar(100) NOT NULL,
  `section_id` int NOT NULL,
  `status` varchar(20) DEFAULT 'enrolled',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_user_name` (`student_user_name`,`section_id`),
  KEY `enrollments_ibfk_2` (`section_id`),
  CONSTRAINT `enrollments_ibfk_1` FOREIGN KEY (`student_user_name`) REFERENCES `students` (`user_name`),
  CONSTRAINT `enrollments_ibfk_2` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollments`
--

LOCK TABLES `enrollments` WRITE;
/*!40000 ALTER TABLE `enrollments` DISABLE KEYS */;
INSERT INTO `enrollments` VALUES (3,'shanky',2,'enrolled'),(5,'mannu',4,'enrolled');
/*!40000 ALTER TABLE `enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades` (
  `user_name` varchar(50) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `grad` varchar(50) NOT NULL,
  `quiz_marks` int NOT NULL DEFAULT '0',
  `assignment_marks` int NOT NULL DEFAULT '0',
  `midsem_marks` int NOT NULL DEFAULT '0',
  `endsem_marks` int NOT NULL DEFAULT '0',
  `group_project_marks` int NOT NULL DEFAULT '0',
  `id` int DEFAULT NULL,
  `section` varchar(50) DEFAULT NULL,
  `instructor` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES ('shanky','ECO 333','B-',50,60,70,69,39,NULL,'B','qty'),('shanky','MTH 101','N/A',0,0,0,0,0,NULL,'A','Mickey'),('mannu','MTH 101','F',23,23,23,23,23,NULL,'B','qty');
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_name_username`
--

DROP TABLE IF EXISTS `instructor_name_username`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_name_username` (
  `user_name` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_name_username`
--

LOCK TABLES `instructor_name_username` WRITE;
/*!40000 ALTER TABLE `instructor_name_username` DISABLE KEYS */;
INSERT INTO `instructor_name_username` VALUES ('Mickey','Mickey Mouse'),('qty','quantity');
/*!40000 ALTER TABLE `instructor_name_username` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintainence`
--

DROP TABLE IF EXISTS `maintainence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintainence` (
  `maintainance_ongoing` varchar(20) NOT NULL DEFAULT 'F'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintainence`
--

LOCK TABLES `maintainence` WRITE;
/*!40000 ALTER TABLE `maintainence` DISABLE KEYS */;
INSERT INTO `maintainence` VALUES ('yes');
/*!40000 ALTER TABLE `maintainence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `username` varchar(100) NOT NULL,
  `status` varchar(10) DEFAULT 'NO',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES ('mannu','NO'),('sanchit','NO'),('shanky','NO'),('sifu','NO'),('taran','NO'),('varun','NO');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration_dates`
--

DROP TABLE IF EXISTS `registration_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_dates` (
  `id` int NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration_dates`
--

LOCK TABLES `registration_dates` WRITE;
/*!40000 ALTER TABLE `registration_dates` DISABLE KEYS */;
INSERT INTO `registration_dates` VALUES (1,'2000-01-01','2025-11-24');
/*!40000 ALTER TABLE `registration_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation1`
--

DROP TABLE IF EXISTS `relation1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation1` (
  `user_name` varchar(100) NOT NULL,
  `designation` varchar(50) NOT NULL,
  PRIMARY KEY (`user_name`),
  CONSTRAINT `fk_relation1_auth` FOREIGN KEY (`user_name`) REFERENCES `auth`.`username_password` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation1`
--

LOCK TABLES `relation1` WRITE;
/*!40000 ALTER TABLE `relation1` DISABLE KEYS */;
INSERT INTO `relation1` VALUES ('mannu','Student'),('Mickey','Instructor'),('qty','Instructor'),('rose','Admin'),('sam','Admin'),('sanchit','Student'),('shanky','Student'),('sifu','Student'),('taran','Student'),('varun','Student');
/*!40000 ALTER TABLE `relation1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation2`
--

DROP TABLE IF EXISTS `relation2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation2` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_code` varchar(50) NOT NULL,
  `section` char(10) DEFAULT 'A',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation2`
--

LOCK TABLES `relation2` WRITE;
/*!40000 ALTER TABLE `relation2` DISABLE KEYS */;
/*!40000 ALTER TABLE `relation2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_code` varchar(50) NOT NULL,
  `instructor_user_name` varchar(100) NOT NULL,
  `day_time` varchar(50) DEFAULT NULL,
  `room` varchar(50) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `semester` varchar(10) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `section` varchar(10) DEFAULT 'A',
  PRIMARY KEY (`id`),
  KEY `instructor_user_name` (`instructor_user_name`),
  KEY `sections_ibfk_1` (`course_code`,`section`),
  CONSTRAINT `sections_ibfk_1` FOREIGN KEY (`course_code`, `section`) REFERENCES `courses` (`code`, `section`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sections_ibfk_2` FOREIGN KEY (`instructor_user_name`) REFERENCES `relation1` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES (1,'ECO 333','Mickey','MWF 11:00-12:30','C 101',3,'3',2025,'A'),(2,'MTH 101','Mickey','MWF 10:00-12:00','C 21',2,'1',2024,'A'),(3,'ECO 333','qty','M 11:00-11:45','C 112',2,'3',2025,'B'),(4,'MTH 101','qty','MTh 11:00-12:08','C 45',1,'1',2025,'B'),(6,'test','Mickey','MTh 11:00-12:05','C 199',2,'3',2024,'A'),(7,'AUTO101','Mickey','MTh 11:00-12:00','C 45',49,'1',2024,'A'),(8,'AUTO102','qty','MWF 10:00-11:00','C 21',40,'3',2025,'A'),(9,'AUTO103','qty','MTh 11:00-12:00','C 21',38,'1',2024,'A'),(10,'AUTO104','Mickey','MW 09:00-10:30','C 102',42,'1',2025,'A'),(11,'AUTO105','Mickey','MTh 11:00-12:00','C 101',46,'4',2025,'A'),(12,'AUTO106','qty','MWF 10:00-11:00','C 21',57,'2',2024,'A'),(13,'AUTO107','qty','TTh 14:00-15:30','C 102',40,'2',2025,'A'),(14,'AUTO108','qty','MW 09:00-10:30','C 102',55,'4',2024,'A'),(15,'AUTO109','Mickey','MW 09:00-10:30','C 21',40,'2',2025,'A'),(16,'AUTO110','qty','MW 09:00-10:30','C 45',47,'1',2025,'A'),(17,'AUTO111','Mickey','MW 09:00-10:30','C 45',48,'1',2024,'A'),(18,'AUTO112','Mickey','MW 09:00-10:30','C 45',32,'3',2025,'A'),(19,'AUTO113','Mickey','MW 09:00-10:30','C 45',59,'1',2025,'A'),(20,'AUTO114','qty','MTh 11:00-12:00','L 2',30,'2',2025,'A'),(21,'AUTO115','Mickey','TTh 14:00-15:30','C 45',47,'1',2025,'A'),(22,'AUTO116','qty','MTh 11:00-12:00','L 2',49,'4',2024,'A'),(23,'AUTO117','qty','MWF 10:00-11:00','C 101',43,'3',2024,'A'),(24,'AUTO118','Mickey','MWF 10:00-11:00','C 102',58,'1',2025,'A'),(25,'AUTO119','Mickey','TTh 14:00-15:30','C 101',45,'3',2024,'A'),(26,'AUTO120','Mickey','MWF 10:00-11:00','L 1',46,'1',2025,'A'),(27,'AUTO121','qty','MTh 11:00-12:00','C 101',59,'2',2024,'A'),(28,'AUTO122','qty','MTh 11:00-12:00','C 102',32,'2',2024,'A'),(29,'AUTO123','qty','TTh 14:00-15:30','C 45',55,'2',2025,'A'),(30,'AUTO124','Mickey','MTh 11:00-12:00','L 1',56,'2',2024,'A'),(31,'AUTO125','qty','MWF 10:00-11:00','C 101',43,'1',2024,'A'),(32,'AUTO126','qty','MTh 11:00-12:00','L 2',35,'3',2024,'A'),(33,'AUTO127','Mickey','MWF 10:00-11:00','C 102',39,'3',2024,'A'),(34,'AUTO128','qty','MTh 11:00-12:00','C 101',45,'3',2024,'A'),(35,'AUTO129','qty','MW 09:00-10:30','C 101',51,'3',2025,'A'),(36,'AUTO130','qty','TTh 14:00-15:30','L 2',57,'4',2025,'A'),(37,'AUTO131','Mickey','TTh 14:00-15:30','L 1',30,'4',2024,'A'),(38,'AUTO132','qty','MW 09:00-10:30','C 45',57,'3',2025,'A'),(39,'AUTO133','qty','MTh 11:00-12:00','L 1',39,'4',2025,'A'),(40,'AUTO134','Mickey','MWF 10:00-11:00','L 1',38,'3',2024,'A'),(41,'AUTO135','Mickey','MTh 11:00-12:00','C 101',50,'2',2024,'A'),(42,'AUTO136','Mickey','MWF 10:00-11:00','C 101',54,'2',2025,'A'),(43,'AUTO137','Mickey','MTh 11:00-12:00','L 2',55,'2',2025,'A'),(44,'AUTO138','qty','TTh 14:00-15:30','L 2',47,'1',2025,'A'),(45,'AUTO139','Mickey','MWF 10:00-11:00','C 102',48,'1',2025,'A'),(46,'AUTO140','Mickey','TTh 14:00-15:30','L 1',43,'3',2025,'A'),(47,'AUTO141','Mickey','MTh 11:00-12:00','L 1',49,'1',2025,'A'),(48,'AUTO142','Mickey','MTh 11:00-12:00','C 102',50,'3',2024,'A'),(49,'AUTO143','Mickey','MW 09:00-10:30','C 102',52,'2',2024,'A'),(50,'AUTO144','Mickey','TTh 14:00-15:30','C 101',41,'2',2024,'A'),(51,'AUTO145','qty','TTh 14:00-15:30','C 21',59,'3',2025,'A'),(52,'AUTO146','qty','MWF 10:00-11:00','C 101',58,'2',2024,'A'),(53,'AUTO147','Mickey','MTh 11:00-12:00','C 101',33,'3',2025,'A'),(54,'AUTO148','qty','TTh 14:00-15:30','C 45',42,'4',2024,'A'),(55,'AUTO149','qty','TTh 14:00-15:30','L 1',38,'3',2024,'A'),(56,'AUTO150','qty','MTh 11:00-12:00','C 21',52,'3',2024,'A'),(57,'AUTO151','Mickey','MWF 10:00-11:00','C 101',60,'3',2025,'A'),(58,'AUTO152','qty','MTh 11:00-12:00','C 102',54,'1',2024,'A'),(59,'AUTO153','qty','MW 09:00-10:30','L 2',42,'4',2024,'A'),(60,'AUTO154','qty','MWF 10:00-11:00','C 101',35,'3',2024,'A'),(61,'AUTO155','Mickey','MWF 10:00-11:00','L 1',38,'3',2025,'A'),(62,'AUTO156','Mickey','MTh 11:00-12:00','C 101',36,'2',2025,'A'),(63,'AUTO157','Mickey','MTh 11:00-12:00','C 45',36,'4',2024,'A'),(64,'AUTO158','Mickey','TTh 14:00-15:30','C 102',55,'4',2024,'A'),(65,'AUTO159','qty','MW 09:00-10:30','C 45',44,'3',2024,'A'),(66,'AUTO160','qty','MW 09:00-10:30','L 2',46,'2',2025,'A'),(67,'AUTO161','Mickey','TTh 14:00-15:30','C 21',50,'4',2025,'A'),(68,'AUTO162','qty','MTh 11:00-12:00','L 2',44,'1',2024,'A'),(69,'AUTO163','qty','MTh 11:00-12:00','L 2',46,'2',2024,'A'),(70,'AUTO164','Mickey','TTh 14:00-15:30','C 102',51,'2',2024,'A'),(71,'AUTO165','Mickey','TTh 14:00-15:30','L 2',58,'1',2024,'A'),(72,'AUTO166','Mickey','MTh 11:00-12:00','C 45',43,'3',2025,'A'),(73,'AUTO167','Mickey','MTh 11:00-12:00','L 1',37,'1',2025,'A'),(74,'AUTO168','Mickey','MTh 11:00-12:00','C 102',45,'4',2025,'A'),(75,'AUTO169','qty','MTh 11:00-12:00','L 2',58,'4',2025,'A'),(76,'AUTO170','qty','MWF 10:00-11:00','L 1',31,'3',2025,'A'),(77,'AUTO171','qty','MTh 11:00-12:00','C 21',36,'1',2024,'A'),(78,'AUTO172','qty','MW 09:00-10:30','C 45',59,'3',2025,'A'),(79,'AUTO173','Mickey','TTh 14:00-15:30','L 2',36,'3',2025,'A'),(80,'AUTO174','qty','MW 09:00-10:30','C 45',40,'1',2025,'A'),(81,'AUTO175','qty','TTh 14:00-15:30','C 102',44,'4',2024,'A'),(82,'AUTO176','qty','MWF 10:00-11:00','L 1',47,'1',2025,'A'),(83,'AUTO177','qty','MTh 11:00-12:00','C 21',46,'1',2024,'A'),(84,'AUTO178','qty','MW 09:00-10:30','C 45',30,'1',2025,'A'),(85,'AUTO179','qty','TTh 14:00-15:30','L 2',36,'3',2025,'A'),(86,'AUTO180','qty','TTh 14:00-15:30','L 2',47,'3',2024,'A'),(87,'AUTO181','qty','MTh 11:00-12:00','L 2',53,'4',2025,'A'),(88,'AUTO182','qty','MW 09:00-10:30','L 2',46,'3',2025,'A'),(89,'AUTO183','qty','MWF 10:00-11:00','C 102',36,'3',2024,'A'),(90,'AUTO184','qty','MTh 11:00-12:00','L 1',40,'3',2024,'A'),(91,'AUTO185','Mickey','MTh 11:00-12:00','C 101',54,'4',2025,'A'),(92,'AUTO186','Mickey','MWF 10:00-11:00','L 2',52,'1',2025,'A'),(93,'AUTO187','Mickey','MW 09:00-10:30','L 1',51,'1',2024,'A'),(94,'AUTO188','qty','TTh 14:00-15:30','C 21',60,'3',2024,'A'),(95,'AUTO189','Mickey','MWF 10:00-11:00','C 45',31,'3',2025,'A'),(96,'AUTO190','Mickey','MTh 11:00-12:00','L 1',35,'4',2025,'A'),(97,'AUTO191','Mickey','TTh 14:00-15:30','C 101',53,'2',2024,'A'),(98,'AUTO192','qty','MTh 11:00-12:00','C 101',58,'1',2024,'A'),(99,'AUTO193','Mickey','MW 09:00-10:30','L 2',59,'3',2025,'A'),(100,'AUTO194','Mickey','MW 09:00-10:30','L 1',52,'3',2025,'A'),(101,'AUTO195','qty','TTh 14:00-15:30','C 102',58,'3',2024,'A'),(102,'AUTO196','qty','MWF 10:00-11:00','C 102',33,'2',2025,'A'),(103,'AUTO197','qty','MW 09:00-10:30','C 21',48,'1',2024,'A'),(104,'AUTO198','qty','MW 09:00-10:30','L 1',59,'1',2024,'A'),(105,'AUTO199','Mickey','MWF 10:00-11:00','L 1',37,'4',2025,'A'),(106,'AUTO200','Mickey','TTh 14:00-15:30','L 2',49,'2',2025,'A');
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `user_name` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `roll_no` int NOT NULL,
  `program` varchar(50) NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `roll_no` (`roll_no`),
  CONSTRAINT `fk_students_auth` FOREIGN KEY (`user_name`) REFERENCES `auth`.`username_password` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('mannu','mannat',2024333,'Btech',2024),('sanchit','sanchit',2024503,'Btech',2024),('shanky','shashank',2024521,'Btech',2024),('sifu','sifat',2024556,'Btech',2024),('taran','Tarandeep',2024587,'Btech',2024),('varun','varun',2024678,'Btech',2024);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectsxname_instructor`
--

DROP TABLE IF EXISTS `subjectsxname_instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectsxname_instructor` (
  `user_name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectsxname_instructor`
--

LOCK TABLES `subjectsxname_instructor` WRITE;
/*!40000 ALTER TABLE `subjectsxname_instructor` DISABLE KEYS */;
INSERT INTO `subjectsxname_instructor` VALUES ('Mickey','ECO 333'),('Mickey','MTH 101'),('qty','ECO 333'),('qty','MTH 101'),('Mickey','test'),('Mickey','AUTO101'),('qty','AUTO102'),('qty','AUTO103'),('Mickey','AUTO104'),('Mickey','AUTO105'),('qty','AUTO106'),('qty','AUTO107'),('qty','AUTO108'),('Mickey','AUTO109'),('qty','AUTO110'),('Mickey','AUTO111'),('Mickey','AUTO112'),('Mickey','AUTO113'),('qty','AUTO114'),('Mickey','AUTO115'),('qty','AUTO116'),('qty','AUTO117'),('Mickey','AUTO118'),('Mickey','AUTO119'),('Mickey','AUTO120'),('qty','AUTO121'),('qty','AUTO122'),('qty','AUTO123'),('Mickey','AUTO124'),('qty','AUTO125'),('qty','AUTO126'),('Mickey','AUTO127'),('qty','AUTO128'),('qty','AUTO129'),('qty','AUTO130'),('Mickey','AUTO131'),('qty','AUTO132'),('qty','AUTO133'),('Mickey','AUTO134'),('Mickey','AUTO135'),('Mickey','AUTO136'),('Mickey','AUTO137'),('qty','AUTO138'),('Mickey','AUTO139'),('Mickey','AUTO140'),('Mickey','AUTO141'),('Mickey','AUTO142'),('Mickey','AUTO143'),('Mickey','AUTO144'),('qty','AUTO145'),('qty','AUTO146'),('Mickey','AUTO147'),('qty','AUTO148'),('qty','AUTO149'),('qty','AUTO150'),('Mickey','AUTO151'),('qty','AUTO152'),('qty','AUTO153'),('qty','AUTO154'),('Mickey','AUTO155'),('Mickey','AUTO156'),('Mickey','AUTO157'),('Mickey','AUTO158'),('qty','AUTO159'),('qty','AUTO160'),('Mickey','AUTO161'),('qty','AUTO162'),('qty','AUTO163'),('Mickey','AUTO164'),('Mickey','AUTO165'),('Mickey','AUTO166'),('Mickey','AUTO167'),('Mickey','AUTO168'),('qty','AUTO169'),('qty','AUTO170'),('qty','AUTO171'),('qty','AUTO172'),('Mickey','AUTO173'),('qty','AUTO174'),('qty','AUTO175'),('qty','AUTO176'),('qty','AUTO177'),('qty','AUTO178'),('qty','AUTO179'),('qty','AUTO180'),('qty','AUTO181'),('qty','AUTO182'),('qty','AUTO183'),('qty','AUTO184'),('Mickey','AUTO185'),('Mickey','AUTO186'),('Mickey','AUTO187'),('qty','AUTO188'),('Mickey','AUTO189'),('Mickey','AUTO190'),('Mickey','AUTO191'),('qty','AUTO192'),('Mickey','AUTO193'),('Mickey','AUTO194'),('qty','AUTO195'),('qty','AUTO196'),('qty','AUTO197'),('qty','AUTO198'),('Mickey','AUTO199'),('Mickey','AUTO200');
/*!40000 ALTER TABLE `subjectsxname_instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectsxname_students`
--

DROP TABLE IF EXISTS `subjectsxname_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectsxname_students` (
  `user_name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectsxname_students`
--

LOCK TABLES `subjectsxname_students` WRITE;
/*!40000 ALTER TABLE `subjectsxname_students` DISABLE KEYS */;
INSERT INTO `subjectsxname_students` VALUES ('shanky','ECO 333'),('shanky','MTH 101'),('mannu','MTH 101');
/*!40000 ALTER TABLE `subjectsxname_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `auth`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `auth` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `auth`;

--
-- Table structure for table `username_password`
--

DROP TABLE IF EXISTS `username_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `username_password` (
  `user_name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `desg` varchar(100) NOT NULL,
  `failed_attempts` int DEFAULT '0',
  `is_locked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `username_password`
--

LOCK TABLES `username_password` WRITE;
/*!40000 ALTER TABLE `username_password` DISABLE KEYS */;
INSERT INTO `username_password` VALUES ('mannu','$2a$12$.5G.ak0.PbMgcaOvjPuv5unRu5LJLWS5SCnPak6Gq7cYBBBq5I5tO','Student',0,0),('Mickey','$2a$12$zpJ1xSF4v4MvnhA7NlnDdu9HQo/0xooJ2m6Ps0pTnHdjvdZNEBzze','Instructor',0,0),('qty','$2a$12$GCxdEqfJfdXRuVHHPw51f.OiWB8CEAWq2ZhXgUMszLFy12z4/dd92','Instructor',0,0),('rose','$2a$12$c5pQPmEOcrpY74iKbhK/d.wu4NvgJh4QQ2PM8iewj9.lg7I9OcsS6','Admin',0,0),('sam','$2a$12$fJYGl/6ujdkkOThIAJAfqeiyCdMSz9KcTHTVPGlvuFOoJtEmWbHLK','Admin',0,0),('sanchit','$2a$12$BS0Q9EhY9qJDUgmy7PibdeaHT9E/7jQO0c9TGxzKzahVixgnvfK1G','Student',0,0),('shanky','$2a$12$ddPZhVRdHFbEye./NxMUbebsfwMzosZTQojN2cpONNl/WWQs1r4lO','Student',0,0),('sifu','$2a$12$1y6OTi/./mfUIH0KIKlG2.llckbViZJOdyPSxBGr32HS4iE4clEsK','Student',0,0),('taran','$2a$12$secpuQtH4.YFvYeuDW8qnu2kb4wQwBDCc/opMtIsyIqAyOzP5TO7m','Student',0,0),('varun','$2a$12$yacJ5gdbPW9ClR4u22jUgukOCVdtwXcq0zAJIJsNyl.wLTPwrQk7y','Student',0,0);
/*!40000 ALTER TABLE `username_password` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26 13:08:31
