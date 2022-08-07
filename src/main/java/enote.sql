/*CREATE DATABASE eNote ON PRIMARY
( NAME = eNote_data, FILENAME = 'D:\eNote_data.mdf', SIZE = 10, MAXSIZE = 1000, FILEGROWTH = 10)
LOG ON
( NAME = eNote_log, FILENAME = 'D:\eNote_log.ldf', SIZE = 10, FILEGROWTH = 5)*/

USE eNote
GO

/*
DROP TABLE NOTE
DROP TABLE ACCOUNT
*/

CREATE TABLE ACCOUNT (
                         Username VARCHAR(100),
                         Password VARCHAR(100)
                         CONSTRAINT PK_Username PRIMARY KEY CLUSTERED (Username)
)

ON [PRIMARY]
GO

CREATE TABLE NOTE (
                      ID VARCHAR(50),
                      Type VARCHAR(50),
                      Content VARCHAR(50),
                      Username_ID VARCHAR(100),
					  Create_Time TIMESTAMP,
                      CONSTRAINT PK_ID PRIMARY KEY CLUSTERED (ID)
)

ALTER TABLE NOTE WITH NOCHECK
	ADD FOREIGN KEY (Username_ID) REFERENCES ACCOUNT(Username)
GO

INSERT INTO ACCOUNT values('username','1234')
INSERT INTO NOTE VALUES(1, 'hello', 'txt', '0x9473FBCCBC01AF', 'username')

SELECT * FROM ACCOUNT
SELECT * FROM NOTE

DELETE FROM NOTE WHERE USERNAME_ID = 'username'
DELETE FROM ACCOUNT WHERE USERNAME = 'username'