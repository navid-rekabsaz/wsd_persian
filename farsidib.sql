-- MySQL dump 10.13  Distrib 5.5.37, for Win64 (x86)
--
-- Host: localhost    Database: farsidb
-- ------------------------------------------------------
-- Server version	5.5.37

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
-- Table structure for table `clusters`
--

DROP TABLE IF EXISTS `clusters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clusters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cluster` varchar(30) NOT NULL,
  `word_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`word_id`),
  CONSTRAINT `fk_cluster_words` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clusters`
--

LOCK TABLES `clusters` WRITE;
/*!40000 ALTER TABLE `clusters` DISABLE KEYS */;
INSERT INTO `clusters` VALUES (1,'مربی',1),(2,'کالسکه',1),(3,'بلیط',1),(4,'آموزش',2),(5,'تحصیل',2),(6,'معرفت',2),(7,'اعدام',3),(8,'انجام',3),(9,'توقیف',3),(10,'پیکر',4),(11,'شکل',4),(12,'رقم',4),(13,'شخص',4),(14,'کار',5),(15,'کار خلاف',5),(16,'رویداد',5),(17,'حرف',6),(18,'نامه',6),(19,'علم',6),(20,'ادبیات',6),(21,'کبریت',7),(22,'همانند',7),(23,'مسابقه',7),(24,'همسر',7),(25,'ماموریت',8),(26,'مبلغ مذهبی',8),(27,'سفارت',8);
/*!40000 ALTER TABLE `clusters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translations`
--

DROP TABLE IF EXISTS `translations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `translations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `translation` varchar(45) NOT NULL,
  `cluster_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tranlations_cluster_idx` (`cluster_id`),
  CONSTRAINT `fk_tranlations_cluster` FOREIGN KEY (`cluster_id`) REFERENCES `clusters` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `translations`
--

LOCK TABLES `translations` WRITE;
/*!40000 ALTER TABLE `translations` DISABLE KEYS */;
INSERT INTO `translations` VALUES (1,'مربی',1),(2,'مربی ورزش',1),(3,'سرپرست امور ورزشی',1),(4,'معلم خصوصی',1),(5,'کالسکه',2),(6,'دلیجان',2),(7,'واگن درجه سه',2),(8,'بلیط درجه دو',3),(9,'بلیط توریست',3),(10,' معلم خصوصی',1),(11,'اتومبیل دو در',2),(12,'واگن راه آهن',2),(13,'واگن',2),(26,'آموزش و پرورش',4),(27,'آموزش',4),(28,'تعلیم و تربیت',4),(29,'تحصیل',5),(30,'تحصیلات',5),(31,'تربیت',4),(32,'فرهیخت',5),(33,'آموختگی ',5),(34,'فضل و کمال',6),(35,'فرهیختگی',6),(36,'معرفت',6),(37,'بصیرت',6),(38,'اعدام',7),(39,'ظبط',8),(40,'توقیف',8),(41,'قتل',7),(42,'امضا',9),(43,'اجرا',9),(44,'انجام',9),(45,'کاربست',9),(46,'ایفا',9),(47,'نگاره',11),(48,'هیکل',10),(49,'پیکره',10),(50,'تندیس',10),(51,'پیکر',10),(52,'اندام ',10),(53,'ترکیب',11),(54,'شمایل',10),(55,'مجسمه',10),(56,'فرتور',10),(57,'دیسه',10),(58,'طرح',11),(59,'ریخت',11),(60,'شکل',11),(61,'دیس ',11),(62,'نگار',11),(63,'نمایه',11),(64,'سنبات',11),(65,'رخساره',11),(66,'شماره',12),(67,'عدد',12),(68,'رقم',12),(69,'حساب',12),(70,'شمارش',12),(71,'مبلغ',12),(72,'وجه',12),(73,'بها',12),(74,'شخص',13),(75,'انسان',13),(96,'کار',14),(97,'شغل',14),(98,'دخش',14),(99,'ورزه',14),(100,'مقام',14),(101,'موقعیت شغلی',14),(102,'سمت',14),(103,'پیشه',14),(104,'تبهکاری',15),(105,'سرقت',15),(106,'کار خلاف',15),(107,'قضیه',16),(108,'رویداد',16),(109,'موضوع',16),(110,'ماجرا',16),(111,'چیز',16),(112,'کار کنتراتی',14),(113,'مقاطعه',14),(114,'کار چاق کنی',14),(115,'مزدکاری',14),(116,'حرف',17),(117,'وات',17),(118,'نامه',18),(119,'مراسله',18),(120,'مکتوب',18),(121,'اوراق',18),(122,'مدرک',18),(123,'اعتبارنامه',18),(124,'ادبیات',20),(125,'دانشمندی',19),(126,'علم',19),(127,'لفظ',19),(128,'نص',19),(129,'کلام',19),(130,'متن',18),(131,'سند',18),(132,'نوشته',18),(133,'حرف الفبا',17),(134,'حرف چاپی',17),(135,'کاغذ',18),(136,'آثار ادبی',20),(137,'کبریت',21),(138,'فتیله ی توپ',21),(139,'فتیله ی بمب',12),(140,'تا',22),(141,'برابر',22),(142,'همتا',22),(143,'همسر',22),(144,'جفت',22),(145,'لنگه',22),(146,'نظیر',22),(147,'همانند',22),(148,'جور',22),(149,'مسابقه',23),(150,'همداوی',23),(151,'ناورد',23),(152,'هماوری',23),(153,'آورد',23),(154,'پادکوشی',23),(155,'قرارداد زناشویی',24),(156,'زناشویی',24),(157,'همسر آینده',24),(158,'همسر مناسب',24),(159,'هماورد',23),(160,'رقیب',23),(161,'حریف',23),(162,'پادکوش',23),(163,'رویارویی',23),(164,'همتاک',23),(165,'گسیل',24),(166,'فرستش',24),(167,'گسیلش',24),(168,'ماموریت',24),(169,'گماشت',24),(170,'هیات مبلغین مذهبی',25),(171,'سازمان هیات مبلغین',25),(172,'ساختمان مبلغین',25),(173,'مقر مبلغین',25),(174,'فعالیت مبلغین مذهبی',25),(175,'هیات نمایندگان',27),(176,'سفارت',27),(177,'دفتر سیاسی',27),(178,'ماموریت فرستادگان',24),(179,'کار عمده',24),(180,'هدف عمده',24),(181,'رسالت',24),(182,'سازمان خیریه برای مستمندان',25),(183,'ماموریت رزمی',24),(184,'گسیلش رزمی',24);
/*!40000 ALTER TABLE `translations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words`
--

DROP TABLE IF EXISTS `words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words`
--

LOCK TABLES `words` WRITE;
/*!40000 ALTER TABLE `words` DISABLE KEYS */;
INSERT INTO `words` VALUES (1,'coach'),(2,'education'),(3,'execution'),(4,'figure'),(5,'job'),(6,'letter'),(7,'match'),(8,'mission'),(9,'mood'),(10,'paper'),(11,'post'),(12,'pot'),(13,'range'),(14,'rest'),(15,'ring'),(16,'scene'),(17,'side'),(18,'soil'),(19,'strain'),(20,'test');
/*!40000 ALTER TABLE `words` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-10 10:15:58
