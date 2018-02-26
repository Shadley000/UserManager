DROP TABLE IF EXISTS login_record;

DROP TABLE IF EXISTS role_to_users;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS role_type;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    users_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    login VARCHAR(128) DEFAULT NULL,
    user_password VARCHAR(32) DEFAULT NULL,
    first_name VARCHAR(128) DEFAULT NULL,
    last_name VARCHAR(128) DEFAULT NULL,
    email VARCHAR(128) DEFAULT NULL,
    confirmed tinyint DEFAULT 0,
    PRIMARY KEY (users_id),
    UNIQUE KEY login (login)
)  ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET=LATIN1;

CREATE TABLE application (
    application_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    nname VARCHAR(128) DEFAULT NULL,
    description VARCHAR(256) DEFAULT NULL,
    PRIMARY KEY (application_id),
    UNIQUE KEY nname (nname)
)  ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=LATIN1;

CREATE TABLE role_type (
    role_type_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    nname VARCHAR(32) DEFAULT NULL,
    description VARCHAR(256) DEFAULT NULL,
    unique key(nname),
    PRIMARY KEY (role_type_id)  
)  ENGINE=INNODB AUTO_INCREMENT=25 DEFAULT CHARSET=LATIN1;

CREATE TABLE role (
    role_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    application_id BIGINT(20) DEFAULT NULL,
    role_type_id BIGINT(20) DEFAULT NULL,
    role_type_name VARCHAR(32) NOT NULL,
    nname VARCHAR(32) NOT NULL,
    ud1  VARCHAR(256) DEFAULT NULL,
    PRIMARY KEY (role_id),
    UNIQUE KEY application_id (application_id , role_type_id, nname),
    CONSTRAINT role_ibfk_1 FOREIGN KEY (application_id)        			REFERENCES application (application_id),
    CONSTRAINT role_ibfk_2 FOREIGN KEY (role_type_id)        			REFERENCES role_type (role_type_id)
)  ENGINE=INNODB AUTO_INCREMENT=25 DEFAULT CHARSET=LATIN1;

CREATE TABLE role_to_users (
    role_to_users_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    application_id BIGINT(20) DEFAULT NULL,
    users_id BIGINT(20) DEFAULT NULL,
    role_id BIGINT(20) DEFAULT NULL,
    PRIMARY KEY (role_to_users_id),
    UNIQUE KEY id_users (application_id, users_id , role_id),    
    CONSTRAINT role_to_users_ibfk_1 	FOREIGN KEY (users_id)        	REFERENCES users (users_id),
    CONSTRAINT role_to_users_ibfk_2 	FOREIGN KEY (role_id)         	REFERENCES role (role_id),
    CONSTRAINT role_to_users_ibfk_3 	FOREIGN KEY (application_id)    REFERENCES application (application_id)
)  ENGINE=INNODB AUTO_INCREMENT=20 DEFAULT CHARSET=LATIN1;
    
CREATE TABLE login_record (
    login_record_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    ip_address VARCHAR(64),
    login VARCHAR(128) DEFAULT NULL,
    user_password VARCHAR(32) DEFAULT NULL,
    login_date TIMESTAMP,
    success tinyint(1) DEFAULT 0,
    PRIMARY KEY (login_record_id)
)  ENGINE=INNODB AUTO_INCREMENT=20 DEFAULT CHARSET=LATIN1;

