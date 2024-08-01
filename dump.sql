-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: 127.0.0.1    Database: research_mng_db
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `t_academic_paper`
--

DROP TABLE IF EXISTS `t_academic_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_academic_paper` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `press` varchar(128) NOT NULL,
  `authors` varchar(64) NOT NULL,
  `department` varchar(64) NOT NULL,
  `publish_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_academic_paper`
--

INSERT INTO `t_academic_paper` VALUES (1,'一种基于大数据的分析原理','新华出版社','王丽','欧语系','2020-02-03 08:00:00',0);

--
-- Table structure for table `t_academic_work`
--

DROP TABLE IF EXISTS `t_academic_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_academic_work` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `press` varchar(128) NOT NULL,
  `authors` varchar(64) NOT NULL,
  `department` varchar(64) NOT NULL,
  `publish_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0',
  `category` varchar(20) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_academic_work`
--

INSERT INTO `t_academic_work` VALUES (1,'基于大数据分析的金融投资行为分析理论','上海交通大学出版社','张三','上海交通大学','2023-10-01 08:00:00',0,'专著'),(2,'三维不对称空间下的超量子物理学研究','复旦大学出版社','王五','复旦大学','2023-08-01 08:00:00',0,'译著'),(9,'基于大数据分析的金融投资行为分析理论2','上海交通大学出版社','张三','上海交通大学','2023-10-01 00:00:00',0,'专著'),(10,'三维不对称空间下的超量子物理学研究3','复旦大学出版社','王五','复旦大学','2024-08-01 00:00:00',0,'译著');

--
-- Table structure for table `t_awards`
--

DROP TABLE IF EXISTS `t_awards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_awards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `department` varchar(128) NOT NULL,
  `award_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `award_scope` varchar(64) NOT NULL,
  `award_level` varchar(64) NOT NULL,
  `status` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_awards`
--

INSERT INTO `t_awards` VALUES (1,'年度创新科研项目','上海交通大学','2024-07-18 08:00:00','国家级','二等奖',0),(2,'国家重点学科重点实验室创新项目','上海市教委','2024-07-18 08:00:00','地市级','二等奖',0);

--
-- Table structure for table `t_h_project`
--

DROP TABLE IF EXISTS `t_h_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_h_project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `card_num` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `responsible` varchar(20) NOT NULL,
  `category` varchar(20) DEFAULT '',
  `budget` int NOT NULL,
  `department` varchar(20) DEFAULT '',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0',
  `type` int DEFAULT '0',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=889 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_h_project`
--

INSERT INTO `t_h_project` VALUES (111,111,'水岸东方2算法123','312','国家级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(123,123,'水岸东方1算法123','312','部级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(222,222,'水岸东方3算法123','312','省级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(333,222,'水岸东方4算法123','312','地市级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(444,222,'水岸东方3算法125','312','地市级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(555,222,'水岸东方3算法123','312','地市级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(666,222,'水岸东方3算法126','312','地市级',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(777,222,'水岸东方3算法1231','312','其他',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0),(888,222,'水岸东方3算法1232','312','其他',123123,'欧语系','2024-03-12 08:00:00','2026-03-12 08:00:00',0,0);

--
-- Table structure for table `t_researcher`
--

DROP TABLE IF EXISTS `t_researcher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_researcher` (
  `id` int NOT NULL,
  `name` varchar(20) NOT NULL DEFAULT '',
  `title` varchar(64) NOT NULL,
  `subject` varchar(64) NOT NULL,
  `degree` varchar(20) NOT NULL DEFAULT '',
  `department` varchar(64) NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_researcher`
--

INSERT INTO `t_researcher` VALUES (1,'张三','副教授','计算机','博士','英语系',0),(2,'李四','讲师','物理学','博士','外语教学部',0);

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `is_admin` int NOT NULL DEFAULT '0',
  `status` int DEFAULT '0',
  `login_name` varchar(20) DEFAULT '',
  `department` varchar(64) DEFAULT '',
  `position` varchar(64) DEFAULT '',
  `mobile` varchar(20) DEFAULT '',
  `email` varchar(32) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

INSERT INTO `t_user` VALUES (1,'张霞','123',1,0,'admin','欧语系','教学秘书','3456789','admin1@163.com'),(2,'李奥1','123',0,0,'leo','日语系','工程师','12345678','yss@163.com'),(3,'张曼丽','123',0,0,'mary','教务中心','秘书','12332423','lll@163.com');

--
-- Table structure for table `t_v_project`
--

DROP TABLE IF EXISTS `t_v_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_v_project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `card_num` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `responsible` varchar(20) NOT NULL,
  `category` varchar(20) DEFAULT '',
  `budget` int NOT NULL,
  `department` varchar(20) DEFAULT '',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0',
  `type` int DEFAULT '0',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14124125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_v_project`
--

INSERT INTO `t_v_project` VALUES (1,6222001,'人工智能实验室机器学习项目','张三','省级',2000000,'英语系','2024-07-16 08:00:00','2026-07-16 08:00:00',0,0),(2,6222002,'物理学院核物理燃料研究','张成','省级',3000000,'欧语系','2024-07-16 08:00:00','2028-07-16 08:00:00',0,0),(3,6222003,'大数据智能金融研究项目','赵六','部级',400000,'法语系','2022-07-17 08:00:00','2026-07-17 08:00:00',0,0),(4,6222004,'大数据智能金融研究项目1','赵六','部级',400000,'法语系','2024-07-17 08:00:00','2026-07-17 08:00:00',0,0);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-30 11:41:01
