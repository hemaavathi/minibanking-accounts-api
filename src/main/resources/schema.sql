CREATE TABLE IF NOT EXISTS `account` (
  `acNo` int NOT NULL,
  `acType` varchar(15) DEFAULT NULL,
  `bsb` varchar(15) DEFAULT NULL,
  `payId` varchar(15) DEFAULT NULL,
  `customerId` int DEFAULT '0',
  `status` varchar(15) DEFAULT NULL,
  `balance` int DEFAULT '0',
  PRIMARY KEY (`acNo`),
  UNIQUE KEY `acno_UNIQUE` (`acNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `account_no` (
  `next_val` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`next_val`)
) ENGINE=InnoDB AUTO_INCREMENT=2002022017 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
