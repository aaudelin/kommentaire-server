-- drop table QUESTION;
-- DROP TABLE USER;
create table if not exists QUESTION
(
	ID INT auto_increment,
	CONTENT VARCHAR,
	VOTES INT,
	USERID INT,
	constraint QUESTION_PK
		primary key (ID)
);

create table if not exists  USER_APP
(
	ID INT auto_increment,
	PSEUDO VARCHAR,
	DATETOKEN VARCHAR,
	PWD VARCHAR,
	ROLE VARCHAR,
	TOKEN VARCHAR,
	constraint USER_PK
		primary key (ID)
);

create table if not exists  USER_UPVOTE
(
	USERID INT,
	QUESTIONID INT,
	TYPE VARCHAR
);

