/*
SQLyog Ultimate v9.30 
MySQL - 5.7.21-log : Database - hrm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hrm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `hrm`;

/*Table structure for table `dept_inf` */

DROP TABLE IF EXISTS `dept_inf`;

CREATE TABLE `dept_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `dept_inf` */

insert  into `dept_inf`(`ID`,`NAME`,`REMARK`,`state`) values (1,'技术部','技术部',0),(2,'运营部','运营部',0),(3,'财务部','财务部',0),(5,'总公办','总公办',0),(6,'市场部','市场部',0),(7,'教学部','教学部',0);

/*Table structure for table `document_inf` */

DROP TABLE IF EXISTS `document_inf`;

CREATE TABLE `document_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) NOT NULL,
  `filename` varchar(300) NOT NULL,
  `filetype` varchar(100) NOT NULL,
  `filebytes` longblob NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_DOCUMENT_USER` (`USER_ID`),
  CONSTRAINT `FK_DOCUMENT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `user_inf` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `document_inf` */

/*Table structure for table `employee_inf` */

DROP TABLE IF EXISTS `employee_inf`;

CREATE TABLE `employee_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `CARD_ID` varchar(18) NOT NULL,
  `ADDRESS` varchar(50) NOT NULL,
  `POST_CODE` varchar(50) DEFAULT NULL,
  `TEL` varchar(16) DEFAULT NULL,
  `PHONE` varchar(11) NOT NULL,
  `QQ_NUM` varchar(10) DEFAULT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `SEX` int(11) NOT NULL DEFAULT '1',
  `PARTY` varchar(10) DEFAULT NULL,
  `BIRTHDAY` timestamp NULL DEFAULT NULL,
  `RACE` varchar(100) DEFAULT NULL,
  `EDUCATION` varchar(10) DEFAULT NULL,
  `SPECIALITY` varchar(20) DEFAULT NULL,
  `HOBBY` varchar(100) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` int(11) DEFAULT '0',
  `dept_id` int(11) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_dept_inf` (`dept_id`),
  KEY `FK_job_inf` (`job_id`),
  CONSTRAINT `FK_dept_inf` FOREIGN KEY (`dept_id`) REFERENCES `dept_inf` (`ID`) ON DELETE SET NULL,
  CONSTRAINT `FK_job_inf` FOREIGN KEY (`job_id`) REFERENCES `job_inf` (`ID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `employee_inf` */

insert  into `employee_inf`(`ID`,`NAME`,`CARD_ID`,`ADDRESS`,`POST_CODE`,`TEL`,`PHONE`,`QQ_NUM`,`EMAIL`,`SEX`,`PARTY`,`BIRTHDAY`,`RACE`,`EDUCATION`,`SPECIALITY`,`HOBBY`,`REMARK`,`CREATE_DATE`,`state`,`dept_id`,`job_id`) values (1,'爱丽丝','441324198412031122','广州天河','510000','020-77777777','13902001111','36750066','251425887@qq.com',0,'党员','1980-01-01 00:00:00','满','本科','美声','唱歌',NULL,'2016-03-14 11:35:18',0,1,3),(2,'杰克','22623','43234','42427424','42242','4247242','42424','251425887@qq.com',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-03-14 11:35:18',0,NULL,NULL),(3,'bb','432801197711251038','广州','510000','020-99999999','13907351532','36750064','36750064@qq.com',1,'党员','1977-11-25 00:00:00','汉','本科','计算机','爬山','无','2016-07-14 09:54:52',0,NULL,NULL),(4,'test','441324198412031122','sdfas','510000','020-88798783','18932226981','12345678','229490481@qq.com',1,'清白','2020-04-16 00:00:00','汉','本科',NULL,'唱歌888','四大天王888','2020-04-17 11:36:29',0,NULL,NULL);

/*Table structure for table `job_inf` */

DROP TABLE IF EXISTS `job_inf`;

CREATE TABLE `job_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `job_inf` */

insert  into `job_inf`(`ID`,`NAME`,`REMARK`,`state`) values (1,'职员','职员',0),(3,'Java中级开发工程师','Java中级开发工程师',0),(4,'Java高级开发工程师','Java高级开发工程师',0),(5,'系统管理员','系统管理员',0),(6,'架构师','架构师',0),(7,'主管','主管',0),(8,'经理','经理',0),(9,'总经理','总经理',0),(10,'职员a','cccc',0);

/*Table structure for table `notice_inf` */

DROP TABLE IF EXISTS `notice_inf`;

CREATE TABLE `notice_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `notice_inf_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_inf` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `notice_inf` */

insert  into `notice_inf`(`id`,`name`,`create_date`,`type_id`,`content`,`user_id`,`modify_date`) values (1,'测试888','2020-04-17 15:32:36',1,'<p>test3333999</p>',NULL,NULL);

/*Table structure for table `type_inf` */

DROP TABLE IF EXISTS `type_inf`;

CREATE TABLE `type_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `type_inf` */

insert  into `type_inf`(`id`,`name`,`create_date`,`state`,`user_id`,`modify_date`) values (1,'新闻2','2020-04-17 14:19:31',0,NULL,'2020-04-19 10:02:10'),(2,'娱乐',NULL,NULL,1,NULL);

/*Table structure for table `user_inf` */

DROP TABLE IF EXISTS `user_inf`;

CREATE TABLE `user_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '1',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user_inf` */

insert  into `user_inf`(`ID`,`loginname`,`PASSWORD`,`STATUS`,`createdate`,`username`) values (1,'admin','123456',2,'2016-03-12 09:34:28','超级管理员'),(3,'xi','123456',1,'2020-04-14 14:18:28','希文'),(4,'test','123456',1,'2020-04-16 15:54:32','test');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
