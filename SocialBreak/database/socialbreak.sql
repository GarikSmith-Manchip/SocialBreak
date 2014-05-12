-- Team 16
--
-- Garik Smith-Manchip
-- Kevin Thet
-- Andrew Kopczynski
-- Daniel Li
-- Sanders Lee
--
-- May 5, 2014 lol
-- (c) SocialBreak;
-- Database: sb
-- 
CREATE TABLE UserAccount
	(id     	INT(11)        	NOT NULL 	AUTO_INCREMENT
	,fName      VARCHAR(64)    	NOT NULL
	,lName      VARCHAR(64)    	NOT NULL
	,email      VARCHAR(64)    	NOT NULL
	,activated	TINYINT(1)		DEFAULT 0	NOT NULL
	,ageGate	TINYINT(1)		DEFAULT 0	NOT NULL
	,registDate	TIMESTAMP		DEFAULT CURRENT_TIMESTAMP	NOT NULL
	,altEmail	VARCHAR(64)
	,password	CHAR(128)
	,salt		CHAR(128)
	,PRIMARY KEY (id)
	,CONSTRAINT chk_email CHECK (email LIKE '%@my.bcit.ca')
) ENGINE = InnoDB;
--
CREATE TABLE `sb`.`login_attempts` (
    `user_id` INT(11) NOT NULL,
    `time` VARCHAR(30) NOT NULL
) ENGINE=InnoDB;
--
CREATE TABLE Tag
	(tagID      INT(11)        NOT NULL AUTO_INCREMENT
	,tagName    VARCHAR(16)    NOT NULL
	,PRIMARY KEY (tagID)
);
--
CREATE TABLE ProfileTag
	(id     	INT(11)        NOT NULL
	,tagID      INT(11)        NOT NULL
	,PRIMARY KEY (id, tagID)
	,FOREIGN KEY (id) REFERENCES UserAccount(id)
		ON DELETE CASCADE
	,FOREIGN KEY (tagID) REFERENCES Tag(tagID)
		ON DELETE CASCADE
);
--
CREATE TABLE TopTags
	(id     	INT(11)         NOT NULL
	,rankOne    INT(11)		
	,rankTwo    INT(11)		
	,rankThree  INT(11)
	,PRIMARY KEY (id)
	,FOREIGN KEY (id) REFERENCES UserAccount(id)
		ON DELETE CASCADE
	,FOREIGN KEY (rankOne) REFERENCES Tag(tagID)
		ON DELETE SET NULL
	,FOREIGN KEY (rankTwo) REFERENCES Tag(tagID)
		ON DELETE SET NULL
	,FOREIGN KEY (rankThree) REFERENCES Tag(tagID)
		ON DELETE SET NULL
);
--
-- Change proPIC to support user-uploaded images at some point
-- Note: Ask Keith wtf is up with varbinary
--	,proPic		VARBINARY(max)	NOT NULL
CREATE TABLE Profile
	(id     	INT(11)             NOT NULL
	,proPic     INT(4)  DEFAULT 0   NOT NULL	
	,proBio     VARCHAR(500)        NOT NULL
	,PRIMARY KEY (id)
	,FOREIGN KEY (id) REFERENCES UserAccount(id)
		ON DELETE CASCADE
);
--
CREATE TABLE Friend
	(id     	INT(11)     NOT NULL
	,friendID   INT(11)     NOT NULL
	,PRIMARY KEY (id, friendID)
	,FOREIGN KEY (id) REFERENCES UserAccount(id)
		ON DELETE CASCADE
	,FOREIGN KEY (friendID) REFERENCES UserAccount(id)
		ON DELETE CASCADE
);
--
CREATE TABLE Ignored
	(id     	INT(11)     NOT NULL
	,ignoredID  INT(11)     NOT NULL
	,PRIMARY KEY (id, ignoredID)
	,FOREIGN KEY (id) REFERENCES UserAccount(id)
		ON DELETE CASCADE
	,FOREIGN KEY (ignoredID) REFERENCES UserAccount(id)
		ON DELETE CASCADE
);
--
CREATE TABLE Reports
	(id     	INT(11)     NOT NULL
	,reportedID INT(11)     NOT NULL
	,reportDate TIMESTAMP   NOT NULL
	,PRIMARY KEY (id, reportedID, reportDate)
	,FOREIGN KEY (id) REFERENCES UserAccount(id)
		ON DELETE CASCADE
	,FOREIGN KEY (reportedID) REFERENCES UserAccount(id)
		ON DELETE CASCADE
);
-- end
INSERT INTO UserAccount (`fName`, `lName`, `email`, `activated`, `ageGate`, `password`, `salt`)
	VALUES ('Kevin','Thet','Kevin@my.bcit.ca', 1, 1, 'dummy', 'dummy');
	
INSERT INTO UserAccount (`fName`, `lName`, `email`, `activated`, `ageGate`, `password`, `salt`)
	VALUES ('Sanders','Lee','Sanders@my.bcit.ca', 1, 1, 'dummy', 'dummy');
	
INSERT INTO UserAccount (`fName`, `lName`, `email`, `activated`, `ageGate`, `password`, `salt`)
	VALUES ('Daniel','Li','Daniel@my.bcit.ca', 1, 1, 'dummy', 'dummy');
	
INSERT INTO UserAccount (`fName`, `lName`, `email`, `activated`, `ageGate`, `password`, `salt`)
	VALUES ('Andrew','Kopczynski','Andrew@my.bcit.ca', 1, 1, 'dummy', 'dummy');
	
INSERT INTO UserAccount (`fName`, `lName`, `email`, `activated`, `ageGate`, `password`, `salt`)
	VALUES ('Garik','Smith-Manchip','Garik@my.bcit.ca', 1, 1, 'dummy', 'dummy');
	
