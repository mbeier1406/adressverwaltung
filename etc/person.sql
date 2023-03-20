CREATE TABLE PERSON (
	ID				NUMBER NOT NULL,
	VORNAME			VARCHAR2(20) NOT NULL,
	NACHNAME		VARCHAR2(20) NOT NULL,
	GEBURTSDATUM	DATE,
	LIEBLINGSFARBE 	VARCHAR2(10), 
	CONSTRAINT PERSON_PK PRIMARY KEY ( ID ) ENABLE
);

CREATE INDEX NACHNAMENINDEX ON PERSON(NACHNAME);


select * from person;
update person set vorname = 'Anabell' where id_person = 1701;
delete from person;
-- commit;

select * from adresse;
