create table if not exists QUESTION
(
	ID INT auto_increment,
	CONTENT VARCHAR,
	VOTES INT,
	"userId" INT,
	constraint QUESTION_PK
		primary key (ID)
);

create table if not exists  USER
(
	ID INT auto_increment,
	PSEUDO VARCHAR,
	"dateToken" VARCHAR,
	PWD VARCHAR,
	ROLE VARCHAR,
	TOKEN VARCHAR,
	constraint USER_PK
		primary key (ID)
);

