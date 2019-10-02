
CREATE USER reimburse_app
IDENTIFIED BY revature
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT CONNECT TO reimburse_app;
GRANT RESOURCE TO reimburse_app;
GRANT CREATE SESSION TO reimburse_app;
GRANT CREATE TABLE TO reimburse_app;
GRANT CREATE VIEW TO reimburse_app;



drop table user_roles;

CREATE TABLE user_roles (
    user_role_id         NUMBER,
    user_role       VARCHAR2(10) NOT NULL,
    
    CONSTRAINT pk_roles
    PRIMARY KEY (user_role_id)
);

drop table users;

CREATE TABLE users (
    user_id         NUMBER,
    username        VARCHAR2(50) UNIQUE NOT NULL,
    password        VARCHAR2(50) NOT NULL,
    user_first_name      VARCHAR2(100) NOT NULL,
    user_last_name       VARCHAR2(100) NOT NULL,
    user_email			 VARCHAR2(150) UNIQUE NOT NULL,
	user_role_id         NUMBER NOT NULL,
    
    CONSTRAINT pk_users
    PRIMARY KEY (user_id),
    
    CONSTRAINT fk_user_role_id
    FOREIGN KEY (user_role_id)
    REFERENCES user_roles (user_role_id)
);



drop table reimbursement_type;

CREATE TABLE reimbursement_type (
    reimb_type_id   NUMBER,
    reimb_type      VARCHAR2(10) NOT NULL,
    
    CONSTRAINT reimb_type_pk
    PRIMARY KEY (reimb_type_id)
);

drop table reimbursement_status;

CREATE TABLE reimbursement_status (
    reimb_status_id   NUMBER,
    reimb_status      VARCHAR2(10) NOT NULL,
    
    CONSTRAINT reimb_status_pk
    PRIMARY KEY (reimb_status_id)
);



drop table reimbursement;

CREATE TABLE reimbursement (
    reimb_id         	NUMBER NOT NULL,
	reimb_amount	 	NUMBER NOT NULL,
	reimb_submitted	 	TIMESTAMP NOT NULL,
	reimb_resolved	 	TIMESTAMP,
	reimb_description	VARCHAR2(250),
	reimb_receipt		BLOB,
	reimb_author		NUMBER NOT NULL,
	reimb_resolver		NUMBER,
	reimb_status_id		NUMBER NOT NULL,
	reimb_type_id		NUMBER NOT NULL,

    
    CONSTRAINT pk_reimb_id
    PRIMARY KEY (reimb_id),
	
  	CONSTRAINT users_fk_auth
 	FOREIGN KEY (reimb_author) 
 	REFERENCES	users (user_id),
    
    
	CONSTRAINT users_fk_reslvr
	FOREIGN KEY (reimb_resolver) 
	REFERENCES	users (user_id),
      
	
	CONSTRAINT users_status_fk
	FOREIGN KEY (reimb_status_id) 
	REFERENCES	reimbursement_status(reimb_status_id),
	
	CONSTRAINT users_type_fk
	FOREIGN KEY (reimb_type_id) 
	REFERENCES  reimbursement_type(reimb_type_id)
		
);



commit;

INSERT INTO user_roles VALUES (1, 'Manager');
INSERT INTO user_roles VALUES (2, 'Employee');

select * from user_roles;

DELETE  FROM user_roles where user_role_id>0;

INSERT INTO users VALUES (0,'Joe', 'password', 'Joe', 'Qiu', 'email@gmail.com', 2);



CREATE SEQUENCE user_pk_seq
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE reimbursement_pk_seq
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE reimb_status_pk_seq
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE reimb_type_pk_seq
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE user_role_pk_seq
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;

CREATE OR REPLACE TRIGGER user_pk_trigger
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SELECT user_pk_seq.NEXTVAL
    INTO :new.user_id
    FROM dual;
END;
/



CREATE OR REPLACE TRIGGER reimbursement_pk_trigger
BEFORE INSERT ON reimbursement
FOR EACH ROW
BEGIN
    SELECT reimb_pk_seq.NEXTVAL
    INTO :new.reimb_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER reimb_status_pk_trigger
BEFORE INSERT ON reimbursement_status
FOR EACH ROW
BEGIN
    SELECT reimb_status_pk_seq.NEXTVAL
    INTO :new.reimb_status_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER reimb_type_pk_trigger
BEFORE INSERT ON reimbursement_type
FOR EACH ROW
BEGIN
    SELECT reimb_type_pk_seq.NEXTVAL
    INTO :new.reimb_type_id
    FROM dual;
END;
/

--drop TRIGGER user_roles_pk_trigger;
--can not trigger on the user_roles

commit;


CREATE OR REPLACE PROCEDURE get_all_users
    (
        my_cursor OUT SYS_REFCURSOR
    )
IS
BEGIN
    OPEN my_cursor FOR

    SELECT *
    FROM users
    ORDER BY user_id;
END;
/
commit;

    
create or replace PROCEDURE insertReimbursement(
	  p_reimb_id  IN reimbursement.reimb_id%TYPE,
      p_reimb_amount IN reimbursement.reimb_amount%TYPE,
      p_reimb_submitted IN reimbursement.reimb_submitted%TYPE,
      p_reimb_resolved IN reimbursement.reimb_resolved%TYPE,
      p_reimb_description IN reimbursement.reimb_description%TYPE,
      p_reimb_receipt IN reimbursement.reimb_receipt%TYPE,
      p_reimb_author IN reimbursement.reimb_author%TYPE,
      p_reimb_resolver IN reimbursement.reimb_resolver%TYPE,
      p_reimb_status_id IN reimbursement.reimb_status_id%TYPE,
      p_reimb_type_id IN reimbursement.reimb_type_id%TYPE )
AS
BEGIN

  INSERT INTO reimbursement
  VALUES (p_reimb_id, p_reimb_amount, p_reimb_submitted, p_reimb_resolved, p_reimb_description,
  p_reimb_receipt, p_reimb_author, p_reimb_resolver, p_reimb_status_id, p_reimb_type_id );

  COMMIT;

END;
/  


CREATE OR REPLACE PROCEDURE get_all_reimburesment_statusJoin
    (
        my_cursor OUT SYS_REFCURSOR
    )
IS
BEGIN
    OPEN my_cursor FOR

    select reimbursement.* , reimbursement_status.*
from reimbursement 
join reimbursement_status
on reimbursement.reimb_status_id = reimbursement_status.reimb_status_id
    ORDER BY reimbursement.reimb_status_id ;
END;
/
  
  
CREATE OR REPLACE PROCEDURE get_all_reimburesment_typeJoin
    (
        my_cursor OUT SYS_REFCURSOR
    )
IS
BEGIN
    OPEN my_cursor FOR

select reimbursement.* , reimbursement_type.*
from reimbursement 
join reimbursement_type
on reimbursement.reimb_type_id = reimbursement_type.reimb_type_id
    ORDER BY reimbursement.reimb_type_id;
END;
/  
 
 commit;
 
 
 create or replace PROCEDURE insertReimbursement_status(
	  p_reimb_status_id  IN reimbursement_status.reimb_status_id%TYPE,
      p_reimb_status IN reimbursement_status.reimb_status%TYPE
     )
AS
BEGIN

  INSERT INTO reimbursement_status
  VALUES (p_reimb_status_id, p_reimb_status);

COMMIT;

END;
/  
