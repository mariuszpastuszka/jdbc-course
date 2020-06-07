CREATE SCHEMA IF NOT EXISTS jdbc_schema;

CREATE TABLE IF NOT EXISTS Persons
(
  ID      int AUTO_INCREMENT PRIMARY KEY,
  NAME    varchar(50),
  SURNAME varchar(50),
  AGE     int
);

CREATE TABLE IF NOT EXISTS OWNER
(
  id bigint auto_increment,
  name varchar(256),
  sex char,
  city varchar(256),
  street varchar(256),
  zipcode varchar(256),
  constraint OWNER_pk
    primary key (id)
);

CREATE TABLE IF NOT EXISTS DOG
(
  ID bigint auto_increment,
  NAME  VARCHAR(256),
  BREED VARCHAR(256),
  constraint DOG_PK
    primary key (ID),
  constraint OWNER_ID
    foreign key (ID) references OWNER
);


