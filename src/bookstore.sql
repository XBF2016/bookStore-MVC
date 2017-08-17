create table admin
(
adminAccount varchar(20) primary key,
adminName varchar(20) not null,
adminPassWord varchar(20) not null
);



create table participant
(participantID Varchar(50) primary key,
competitionType Varchar(50) not null,
participantName Varchar(20) not null,
participantSex Varchar(20) not null,
participantBirthday Date not null,
participantAccount Varchar(20) not null,
participantPassword Varchar(20) not null,
participantAge smallint not null,
isThrough boolean,
coachName Varchar(20) not null,
isDelete boolean
);

create table records
(recordID Varchar(50) primary key,
participantID Varchar(50) not null,
competitionType Varchar(50) not null,
score Dec(5,2),
time Date not null,
foreign key (participantID) references participant(participantID),
isDelete boolean
);

create table notice
(noticeID Varchar(50) primary key,
Content text not null,
noticeTime Date not null,
noticeCreaterID Varchar(50) not null,
foreign key (noticeCreaterID) references admin(adminAccount),
isDelete boolean
);
