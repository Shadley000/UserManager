
CREATE TABLE `APP_USER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN` varchar(128) DEFAULT NULL,
  `USER_PASSWORD` varchar(32) DEFAULT NULL,
  `FIRST_NAME` varchar(128) DEFAULT NULL,
  `LAST_NAME` varchar(128) DEFAULT NULL,
  `EMAIL` varchar(128) DEFAULT NULL,
  USER_DEFINED1 varchar(256) DEFAULT NULL,
  USER_DEFINED2 varchar(256) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `LOGIN` (`LOGIN`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

CREATE TABLE `APPLICATION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPLICATION_NAME` varchar(128) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT '',
  USER_DEFINED1 varchar(256) DEFAULT NULL,
  USER_DEFINED2 varchar(256) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `APPLICATION_NAME` (`APPLICATION_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


CREATE TABLE `APP_PERMISSION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_APPLICATION` int(11) DEFAULT NULL,
  `PERMISSION_NAME` varchar(32) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT '',
   USER_DEFINED1 varchar(256) DEFAULT NULL,
  USER_DEFINED2 varchar(256) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_APPLICATION` (`ID_APPLICATION`,`PERMISSION_NAME`),
  CONSTRAINT `APP_PERMISSION_ibfk_1` FOREIGN KEY (`ID_APPLICATION`) REFERENCES `APPLICATION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

CREATE TABLE `APP_ROLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_APPLICATION` int(11) DEFAULT NULL,
  `ROLE_NAME` varchar(32) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT '',
  USER_DEFINED1 varchar(256) DEFAULT NULL,
  USER_DEFINED2 varchar(256) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  USER_DEFINED2 BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_APPLICATION` (`ID_APPLICATION`,`ROLE_NAME`),
  CONSTRAINT `APP_ROLE_ibfk_1` FOREIGN KEY (`ID_APPLICATION`) REFERENCES `APPLICATION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;


CREATE TABLE `APP_ROLE_TO_PERMISSION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_APP_ROLE` int(11) DEFAULT NULL,
  `ID_APP_PERMISSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_APP_ROLE` (`ID_APP_ROLE`,`ID_APP_PERMISSION`),
  KEY `ID_APP_PERMISSION` (`ID_APP_PERMISSION`),
  CONSTRAINT `APP_ROLE_TO_PERMISSION_ibfk_1` FOREIGN KEY (`ID_APP_ROLE`) REFERENCES `APP_ROLE` (`ID`),
  CONSTRAINT `APP_ROLE_TO_PERMISSION_ibfk_2` FOREIGN KEY (`ID_APP_PERMISSION`) REFERENCES `APP_PERMISSION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

CREATE TABLE `APP_ROLE_TO_USERS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_APP_USER` int(11) DEFAULT NULL,
  `ID_APP_ROLE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_APP_USER` (`ID_APP_USER`,`ID_APP_ROLE`),
  KEY `ID_APP_ROLE` (`ID_APP_ROLE`),
  CONSTRAINT `APP_ROLE_TO_USERS_ibfk_1` FOREIGN KEY (`ID_APP_USER`) REFERENCES `APP_USER` (`ID`),
  CONSTRAINT `APP_ROLE_TO_USERS_ibfk_2` FOREIGN KEY (`ID_APP_ROLE`) REFERENCES `APP_ROLE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;


