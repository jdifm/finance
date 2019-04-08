KAKAO 사전과제 3 

DDL 


CREATE DATABASE `kakao` /*!40100 DEFAULT CHARSET=utf8 */;

CREATE TABLE `house_finance_credit` (
  `year` int(4) NOT NULL,
  `month` int(2) NOT NULL,
  `institute_code` varchar(5) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`year`,`month`,`institute_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `institute` (
  `institute_code` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `institute_name` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `column_number` int(2) DEFAULT NULL,
  PRIMARY KEY (`institute_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `token` (
  `token_id` int(11) NOT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `user` (
  `id` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `token` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('AAA','주택기금공사','1');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('BBB','국민은행','2');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('CCC','우리은행','3');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('DDD','신한은행','4');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('EEE','씨티은행','5');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('FFF','하나은행','6');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('GGG','농협은행/수협은행','7');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('HHH','외한은행','8');
INSERT INTO `kakao`.`institute` (`institute_code`, `institute_name`, `column_number`) VALUES ('III','기타은행','9');
