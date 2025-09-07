/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - car_sales
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`car_sales` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;

USE `car_sales`;

/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vin` varchar(17) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  `first_reg` date NOT NULL,
  `mileage` int(11) NOT NULL,
  `category` varchar(20) NOT NULL,
  `fuel` varchar(20) NOT NULL,
  `engine_capacity` double NOT NULL,
  `engine_power` double NOT NULL,
  `gearbox` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `car` */

insert  into `car`(`id`,`vin`,`brand`,`model`,`first_reg`,`mileage`,`category`,`fuel`,`engine_capacity`,`engine_power`,`gearbox`,`price`,`status`) values 
(1,'1234567894','Audi','R8','2015-01-01',0,'','',0,0,'',50000,'AVAILABLE'),
(2,'7532156985','Nissan','R34 GTR','2015-03-14',0,'','',0,0,'',100000,'SOLD'),
(3,'9865321548','Seat','Ibiza','2011-01-01',420000,'Hatchback','Diesel',1.2,65,'Manual',5000,'SOLD'),
(4,'8754123548','Audi','A4','2003-01-01',0,'','',0,0,'',10000,'AVAILABLE'),
(5,'8654532695','Ford','Mustang','2016-01-01',0,'','',0,0,'',25000,'AVAILABLE'),
(6,'2546859351','Volkswagen','Golf 5','2008-01-01',0,'','',0,0,'',3000,'AVAILABLE'),
(11,'7865923458','BMW','M4','2011-01-01',0,'','',0,0,'',20000,'SOLD'),
(12,'AA71245965','Škoda','Superb','2021-01-01',150000,'Limousine/Salon','Petrol',1.5,90,'Automatic',10000,'AVAILABLE'),
(13,'YY49562145','Nissan','Qashqai','2010-01-01',0,'','',0,0,'',12000,'SOLD'),
(14,'CC963458U4','Volkswagen','Golf 8','2019-01-01',230000,'Hatchback','Petrol',1.4,85,'Automatic',15000,'AVAILABLE'),
(15,'4578652348','BMW','M3','2018-01-01',0,'','',0,0,'',50000,'SOLD'),
(16,'UU45657899','Audi','A3','2013-01-01',0,'','',0,0,'',6000,'AVAILABLE'),
(17,'6345YTE34V','Volkswagen','Passat','2020-01-01',220885,'Estate/Wagon','Petrol',2,150,'Automatic',16999,'AVAILABLE'),
(18,'HDFTYE6534','Opel','Astra','2006-01-01',420000,'Estate/Wagon','Diesel',1.6,120,'Manual',2000,'AVAILABLE'),
(19,'UYR7643789','Mercedes Benz','E 200','2009-01-01',235800,'Limousine/Salon','Petrol',2.2,136,'Manual',11000,'SOLD'),
(23,'789451542H','Volkswagen','Polo','2012-01-01',460000,'Hatchback','Diesel',1.2,65,'Manual',4000,'AVAILABLE'),
(24,'7548965325684OO78','Skoda','Octavia','2023-01-01',20000,'Limousine/Salon','Petrol',1.4,150,'Manual',14000,'AVAILABLE'),
(25,'789457516845963TT','Audi','A3','2011-01-01',130000,'Hatchback','Petrol',1.4,105,'Manual',7000,'SOLD'),
(26,'845697842TT647IO3','Nissan','Civic','2015-01-01',120000,'Hatchback','Petrol',1.6,150,'Manual',8500,'AVAILABLE');

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` bigint(20) unsigned NOT NULL,
  `company_name` varchar(100) NOT NULL,
  `PIB` varchar(9) NOT NULL,
  `MB` varchar(8) NOT NULL,
  `authorized_person` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `company_ibfk_1` FOREIGN KEY (`id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `company` */

insert  into `company`(`id`,`company_name`,`PIB`,`MB`,`authorized_person`) values 
(3,'Fakultet Organizacionih nauka','456789123','85236975','Aca Koroševski'),
(4,'Centro Agrar','753951486','74185293','Mileta Đukić'),
(8,'OTP Srbija','741596328','85264596','Marko Đukić'),
(9,'Vrtić Neven Mionica','985632569','78541254','Ana Đukić'),
(13,'KK Metalac Valjevo','111111111','11111111','Dragan Gale Simeunović'),
(15,'KK Crvena Zvezda','147147147','85258525','Miloš Teodosić'),
(16,'Studentski centar','865329547','87542693','Pera Perić'),
(20,'Đukić Prevoz','965238745','45612398','Ljubivoje Đukić'),
(22,'Nordeus','756489512','96587594','Stefan Krstović');

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `customer` */

insert  into `customer`(`id`,`phone`,`email`,`address`,`type`) values 
(1,'063741229','djukic.nemanja003@gmail.com','Selo Radobić b.b., 14242 Mionica','individual'),
(3,'011123456','fon@fon.bg.ac.rs','','company'),
(4,'069661036','centro.agrar@gmail.com','','company'),
(5,'0631748008','teka.djukic@gmail.com','','individual'),
(6,'069661036','nebojsa@gmail.com','','individual'),
(7,'0656153878','ana@gmail.com','','individual'),
(8,'011456789','banka@otp.com','','company'),
(9,'0143423456','neven@gmail.com','','company'),
(12,'064845612','mileta@gmail.com','','individual'),
(13,'014111111','metalac.to.je.tim.iz.valjeva@valjevo.com','','company'),
(15,'011456123','kkczv@beograd.rs','','company'),
(16,'011111111','stud@stud.stud','','company'),
(20,'014568565','prevoz@prevoz.djukic','','company'),
(22,'0115698585','nordeus@igrice.com','Negde u Beogradu','company'),
(23,'0698524554','pera@gmail.com','Donja Grabovica bb','individual');

/*Table structure for table `individual` */

DROP TABLE IF EXISTS `individual`;

CREATE TABLE `individual` (
  `id` bigint(20) unsigned NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `jmbg` varchar(13) NOT NULL,
  `id_card_number` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `individual` */

insert  into `individual`(`id`,`first_name`,`last_name`,`jmbg`,`id_card_number`) values 
(1,'Nemanja','Đukić','1234567898456','989765432'),
(5,'Teodora','Đukić','9876543215698','852412365'),
(6,'Nebojša','Đukić','4556123254644','123456000'),
(7,'Ana','Đukić','1234566543211','456653214'),
(12,'Mileta','Đukić','7531594862174','654138756'),
(23,'Pera','Perić','1208095771165','789847659');

/*Table structure for table `invoice` */

DROP TABLE IF EXISTS `invoice`;

CREATE TABLE `invoice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_num` bigint(20) unsigned NOT NULL,
  `date_of_issue` date NOT NULL,
  `total_amount` double NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `customer_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`,`user_id`,`customer_id`),
  KEY `user_fk` (`user_id`),
  KEY `costumer_fk` (`customer_id`),
  CONSTRAINT `costumer_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `invoice` */

insert  into `invoice`(`id`,`invoice_num`,`date_of_issue`,`total_amount`,`user_id`,`customer_id`) values 
(8,1,'2024-12-21',50000,3,1),
(9,2,'2024-12-21',5080000,3,3),
(10,3,'2024-12-21',20000,2,5),
(11,4,'2024-12-21',100000,1,4),
(12,5,'2024-12-21',325000,3,8),
(13,6,'2024-12-23',1000000,3,7),
(14,7,'2024-12-24',100000,3,1),
(15,8,'2024-12-25',20000,3,7),
(16,9,'2024-12-26',330000,1,5),
(17,10,'2024-12-26',24000,1,13),
(18,11,'2025-01-19',15000,3,13),
(19,12,'2025-01-19',85000,2,3),
(20,13,'2025-01-20',30000,3,20),
(21,14,'2025-01-22',95995,3,3),
(22,15,'2025-08-07',56000,3,4),
(23,16,'2025-08-07',10000,3,7),
(24,17,'2025-08-07',20000,3,13),
(25,18,'2025-08-07',20000,3,13),
(26,19,'2025-08-08',100000,3,1),
(27,20,'2025-08-10',5000,3,1),
(28,21,'2025-08-10',62000,3,4),
(29,22,'2025-08-10',8500,3,15),
(30,23,'2025-08-21',100000,3,5);

/*Table structure for table `invoice_item` */

DROP TABLE IF EXISTS `invoice_item`;

CREATE TABLE `invoice_item` (
  `invoice_id` bigint(20) unsigned NOT NULL,
  `rb` int(10) unsigned NOT NULL,
  `price` double unsigned NOT NULL,
  `note` varchar(100) DEFAULT NULL,
  `car_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`invoice_id`,`rb`),
  KEY `rb` (`rb`),
  KEY `car_fk` (`car_id`),
  CONSTRAINT `car_fk` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `invoice_fk` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `invoice_item` */

insert  into `invoice_item`(`invoice_id`,`rb`,`price`,`note`,`car_id`) values 
(8,1,50000,'staviti zimske gume',1),
(9,1,10000,NULL,4),
(9,3,5000,NULL,3),
(9,4,1000000,NULL,6),
(10,1,20000,'dodatna oprema',11),
(11,1,25000,NULL,5),
(12,1,10000,NULL,12),
(12,2,25000,NULL,5),
(13,1,1000000,'dodatna oprema',6),
(14,1,100000,NULL,2),
(15,1,8000,NULL,3),
(15,2,12000,NULL,13),
(16,1,20000,NULL,11),
(16,2,50000,NULL,15),
(17,1,2000,NULL,18),
(18,1,15000,'letnje gume',14),
(19,1,6000,NULL,16),
(19,2,5000,NULL,3),
(20,1,10000,NULL,12),
(21,1,11000,'sportska verzija',19),
(21,2,16999,NULL,17),
(22,1,6000,'staviti sportski auspuh',16),
(22,2,50000,NULL,15),
(25,1,20000,'namontirati sportski auspuh',11),
(26,1,100000,'null',2),
(27,1,5000,'Vraćam je kući',3),
(28,1,50000,'Sluzbeni',15),
(28,2,12000,'',13),
(29,1,8500,'',26),
(30,1,100000,'',2);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`first_name`,`last_name`) values 
(1,'admin','admin','Admin','Admin'),
(2,'nemanja','nemanja','Nemanja','Đukić'),
(3,'a','a','A','A'),
(4,'teka','teka','Teodora','Đukić'),
(6,'p','p','Pera','Perić');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
