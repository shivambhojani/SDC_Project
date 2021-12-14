CREATE TABLE `media_archieve` (
  `mediaId` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`mediaId`,`filename`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `media_attributes` (
  `attributesId` int NOT NULL AUTO_INCREMENT,
  `mediaId` int NOT NULL,
  `attributeName` varchar(255) NOT NULL,
  `attributeValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`attributesId`),
  KEY `mId1_idx` (`mediaId`),
  CONSTRAINT `mId1` FOREIGN KEY (`mediaId`) REFERENCES `media_archieve` (`mediaId`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `media_tags` (
  `tag_Id` int NOT NULL AUTO_INCREMENT,
  `mediaId` int NOT NULL,
  `tag` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`tag_Id`),
  KEY `mediaID2_idx` (`mediaId`),
  CONSTRAINT `mediaID2` FOREIGN KEY (`mediaId`) REFERENCES `media_archieve` (`mediaId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `parentchild_relation` (
  `pcrelationId` int NOT NULL AUTO_INCREMENT,
  `parentid` int DEFAULT NULL,
  `childid` int DEFAULT NULL,
  PRIMARY KEY (`pcrelationId`),
  KEY `parentid` (`parentid`),
  KEY `childId` (`childid`),
  CONSTRAINT `childId` FOREIGN KEY (`childid`) REFERENCES `person` (`p_id`),
  CONSTRAINT `parentid` FOREIGN KEY (`parentid`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `partner_relation` (
  `relationId` int NOT NULL AUTO_INCREMENT,
  `partner1Id` int NOT NULL,
  `partner2Id` int NOT NULL,
  PRIMARY KEY (`relationId`),
  KEY `partner1` (`partner1Id`),
  KEY `partner2` (`partner2Id`),
  CONSTRAINT `partner1` FOREIGN KEY (`partner1Id`) REFERENCES `person` (`p_id`),
  CONSTRAINT `partner2` FOREIGN KEY (`partner2Id`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person` (
  `p_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person_attributes` (
  `attributesId` int NOT NULL AUTO_INCREMENT,
  `personId` int NOT NULL,
  `attributeName` varchar(255) NOT NULL,
  `attributevalue` varchar(255) NOT NULL,
  PRIMARY KEY (`attributesId`),
  KEY `pr1_idx` (`personId`),
  CONSTRAINT `pr1` FOREIGN KEY (`personId`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person_dissolution` (
  `dissolutionId` int NOT NULL AUTO_INCREMENT,
  `partner1Id` int NOT NULL,
  `partner2Id` int NOT NULL,
  PRIMARY KEY (`dissolutionId`),
  KEY `p_id1_idx` (`partner1Id`),
  KEY `p_id2_idx` (`partner2Id`),
  CONSTRAINT `p_id1` FOREIGN KEY (`partner1Id`) REFERENCES `person` (`p_id`),
  CONSTRAINT `p_id2` FOREIGN KEY (`partner2Id`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person_in_media` (
  `mediaId` int NOT NULL,
  `person` int NOT NULL,
  PRIMARY KEY (`mediaId`,`person`),
  KEY `persn` (`person`),
  KEY `md_idx` (`mediaId`),
  CONSTRAINT `md` FOREIGN KEY (`mediaId`) REFERENCES `media_archieve` (`mediaId`),
  CONSTRAINT `persn` FOREIGN KEY (`person`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person_notes` (
  `noteid` int NOT NULL AUTO_INCREMENT,
  `p_id` int NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`noteid`),
  KEY `personID_idx` (`p_id`),
  CONSTRAINT `personID` FOREIGN KEY (`p_id`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person_references` (
  `referenceid` int NOT NULL AUTO_INCREMENT,
  `p_id` int NOT NULL,
  `personReferences` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`referenceid`),
  KEY `personId2` (`p_id`),
  CONSTRAINT `personId2` FOREIGN KEY (`p_id`) REFERENCES `person` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
