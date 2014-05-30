
CREATE TABLE jiveForum (
  forumID               BIGINT NOT NULL,
  name                  VARCHAR(255) UNIQUE NOT NULL,
  description           TEXT,
  modDefaultThreadVal   BIGINT NOT NULL,
  modMinThreadVal       BIGINT NOT NULL,
  modDefaultMsgVal      BIGINT NOT NULL,
  modMinMsgVal          BIGINT NOT NULL,
  modifiedDate          VARCHAR(15) NOT NULL,
  creationDate          VARCHAR(15) NOT NULL,
  PRIMARY KEY           (forumID)
);

CREATE INDEX jiveForum_name_idx ON jiveForum(NAME);

CREATE TABLE jiveForumProp (
  forumID       BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  propValue     TEXT NOT NULL,
  PRIMARY KEY   (forumID,name)
);

CREATE TABLE jiveThread (
  threadID          BIGINT NOT NULL,
  forumID           BIGINT NOT NULL,
  rootMessageID     BIGINT NOT NULL,
  modValue          BIGINT NOT NULL,
  rewardPoints      INT NOT NULL,
  creationDate      VARCHAR(15) NOT NULL,
  modifiedDate      VARCHAR(15) NOT NULL,
  PRIMARY KEY       (threadID),
);

CREATE  INDEX jiveThread_forumID_idx on jiveThread(forumID);
CREATE  INDEX jiveThread_modValue_idx on jiveThread(modValue);
CREATE  INDEX jiveThread_cDate_idx   on jiveThread(creationDate);
CREATE  INDEX jiveThread_mDate_idx   on jiveThread(modifiedDate);

CREATE TABLE jiveThreadProp (
  threadID      BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  propValue     TEXT NOT NULL,
  PRIMARY KEY   (threadID,name)
);

CREATE TABLE jiveMessage (
  messageID             BIGINT NOT NULL,
  parentMessageID       BIGINT NULL, 
  threadID              BIGINT NOT NULL,
  forumID               BIGINT NOT NULL,
  userID                BIGINT NULL,
  subject               VARCHAR(255),
  body                  TEXT,
  modValue              BIGINT NOT NULL,
  rewardPoints          INT NOT NULL,
  creationDate          VARCHAR(15) NOT NULL,
  modifiedDate          VARCHAR(15) NOT NULL,
  PRIMARY KEY           (messageID),
);
CREATE  INDEX jiveMessage_threadID_idx  ON jiveMessage(threadID);
CREATE  INDEX jiveMessage_forumID_idx   ON jiveMessage(forumID);
CREATE  INDEX jiveMessage_userID_idx    ON jiveMessage(userID);
CREATE  INDEX jiveMessage_modValue_idx  ON jiveMessage(modValue);
CREATE  INDEX jiveMessage_cDate_idx     ON jiveMessage(creationDate);
CREATE  INDEX jiveMessage_mDate_idx     ON jiveMessage(modifiedDate);


CREATE TABLE jiveMessageProp (
  messageID    BIGINT NOT NULL,
  name         VARCHAR(100) NOT NULL,
  propValue    TEXT NOT NULL,
  PRIMARY KEY  (messageID,name)
);


CREATE TABLE jiveID (
  idType        INT NOT NULL,
  id            BIGINT NOT NULL,
  PRIMARY KEY   (idType)
);



insert into jiveID values (1, 100);
insert into jiveID values (2, 100);
insert into jiveID values (3, 200);
insert into jiveID values (4, 100);
insert into jiveID values (5, 100);
insert into jiveID values (0, 100);


CREATE TABLE jiveshortmsg (
  msgId bigint(20) NOT NULL default '0',
  userId bigint(20) default NULL,
  messageTitle varchar(50) default NULL,
  messageBody text,
  messageFrom varchar(20) default NULL,
  messageTo varchar(20) default NULL,
  sendTime varchar(50) default NULL,
  hasRead int(1) default NULL,
  hasSent int(1) default NULL,
  PRIMARY KEY  (msgId)
) ;




CREATE TABLE setup (
  name varchar(50) NOT NULL default '',
  value text NOT NULL,
  PRIMARY KEY (name)  
);


CREATE TABLE upload (
  objectId            char(50) NOT NULL default '',
  name                varchar(50) default '',
  description         varchar(200) default '',
  datas               LONGBLOB,
  size                int(20) NOT NULL default '0',  
  messageId           varchar(20) NOT NULL default '0',
  creationDate        VARCHAR(15) NOT NULL,
  contentType         varchar(50) default '',
  PRIMARY KEY  (objectId),
);
CREATE UNIQUE INDEX messageId ON upload(messageId);

CREATE TABLE tag (
  tagID         BIGINT NOT NULL,
  title         varchar(50) default '',
  assonum       int(20) NOT NULL default '0',    
  PRIMARY KEY   (tagID)
);

CREATE TABLE threadTag (
  threadTagID   BIGINT NOT NULL,
  threadID      BIGINT NOT NULL,
  tagID         BIGINT NOT NULL,
  PRIMARY KEY   (threadTagID),
);
CREATE  INDEX jiveThread_tagID_idx ON threadTag(tagID);
CREATE  INDEX tagID_jiveThread_idx ON threadTag(threadID);


CREATE TABLE jiveUser (
  userID         BIGINT NOT NULL,
  username        VARCHAR(30) UNIQUE NOT NULL,
  passwordHash    VARCHAR(32) NOT NULL,
  name            VARCHAR(100),
  nameVisible     INT NOT NULL,
  email           VARCHAR(100) NOT NULL,
  emailVisible    INT NOT NULL,
  rewardPoints    INT NOT NULL,
  creationDate    VARCHAR(15) NOT NULL,
  modifiedDate    VARCHAR(15) NOT NULL,
  PRIMARY KEY     (userID),
);
CREATE  INDEX jiveUser_username_idx ON jiveUser(username);
CREATE  INDEX jiveUser_cDate_idx    ON jiveUser(creationDate);

insert into jiveUser (userID,name,username,passwordHash,email,emailVisible,nameVisible,creationDate,modifiedDate,rewardPoints)
  values (1,'Administrator','admin','21232f297a57a5a743894a0e4a801fc3','admin@yoursite.com',1,1,'0','0',0);

CREATE TABLE jiveUserProp (
  userID        BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  propValue     TEXT NOT NULL,
  PRIMARY KEY   (userID,name)
);

CREATE TABLE jiveReward (
  userID          char(50) NOT NULL,
  creationDate    VARCHAR(15) NOT NULL,
  rewardPoints    BIGINT NOT NULL,
  messageID       BIGINT NULL,
  threadID        BIGINT NULL,
);
CREATE  INDEX jiveReward_userID_idx ON jiveReward(userID);
CREATE  INDEX jiveReward_creationDate_idx ON jiveReward(creationDate);
CREATE  INDEX jiveReward_messageID_idx ON jiveReward(messageID);
CREATE  INDEX jiveReward_threadID_idx ON jiveReward(threadID);


CREATE TABLE jiveModeration (
  objectID    BIGINT NOT NULL,
  objectType  BIGINT NOT NULL,
  userID      char(50) NULL,
  modDate     VARCHAR(15) NOT NULL,
  modValue    BIGINT NOT NULL,
);
CREATE  INDEX jiveModeration_objectID_idx ON jiveModeration(objectID);
CREATE  INDEX jiveModeration_objectType_idx ON jiveModeration(objectType);
CREATE  INDEX jiveModeration_userID_idx ON jiveModeration(userID);

CREATE TABLE subscription (
  subscriptionID bigint(20) NOT NULL,
  userId char(50) default NULL,
  subscribedtype int(1) default NULL,
  subscribedID bigint(20) default NULL,
  creationDate varchar(15) default NULL,
  sendmsg bit(1) default NULL,
  sendemail bit(1) default NULL,
  PRIMARY KEY  (`subscriptionID`),
) ;
CREATE UNIQUE INDEX subscriptionuserId ON subscription(userId);
CREATE UNIQUE INDEX threadID ON subscription(subscribedID);

insert into jiveID values (6, 100);

create table userconnector (
	userId varchar (100),
	conntype varchar (100),
	connuser  varchar (100),
	connpasswd varchar (100)		
); 
CREATE INDEX userId ON userconnector(userId, conntype);