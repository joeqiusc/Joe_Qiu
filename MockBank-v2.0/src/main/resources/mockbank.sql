DROP USER mockbank CASCADE;

CREATE USER mockbank 
IDENTIFIED BY revature
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to mockbank;
GRANT resource to mockbank;
GRANT create session TO mockbank;
GRANT create table TO mockbank;
GRANT create view TO mockbank;



DROP TABLE user_roles;

CREATE TABLE user_roles (
    role_id         NUMBER,
    role_name       VARCHAR2(25),
    
    CONSTRAINT pk_roles
    PRIMARY KEY (role_id)
);

DROP TABLE users; 

CREATE TABLE users (
    user_id         NUMBER,
    username        VARCHAR2(25) UNIQUE NOT NULL,
    password        VARCHAR2(50) NOT NULL,
    first_name      VARCHAR2(25) NOT NULL,
    last_name       VARCHAR2(25) NOT NULL,
    role_id         NUMBER,
    
    CONSTRAINT pk_users
    PRIMARY KEY (user_id),
    
    CONSTRAINT fk_user_role
    FOREIGN KEY (role_id)
    REFERENCES user_roles (role_id)
);

DROP TABLE account;

CREATE TABLE account (
    account_no        NUMBER  NOT NULL,
    user_id           NUMBER,
    balance           NUMBER(10, 2) NOT NULL,
    
    CONSTRAINT pk__account
    PRIMARY KEY (account_no),
	
	CONSTRAINT fk__account_no
    FOREIGN KEY (user_id)
    REFERENCES users (user_id)
	
	
);
commit;


CREATE SEQUENCE user_pk_seq
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;


CREATE SEQUENCE account_pk_seq
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


CREATE OR REPLACE TRIGGER account_pk_trigger
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
    SELECT account_pk_seq.NEXTVAL
    INTO :new.account_no
    FROM dual;
END;


CREATE OR REPLACE TRIGGER user_roles_pk_trigger
BEFORE INSERT ON user_roles
FOR EACH ROW
BEGIN
    SELECT user_role_pk_seq.NEXTVAL
    INTO :new.role_id
    FROM dual;
END;


commit;

INSERT INTO user_roles VALUES (0, 'ADMIN');
INSERT INTO user_roles VALUES (0, 'USER');
INSERT INTO user_roles VALUES (0, 'LOCKED');

INSERT INTO users VALUES (0, 'Joe', 'Paasword00', 'Joe', 'Qiu', 1);
INSERT INTO users VALUES (0, 'Zhang', 'Paasword01', 'zhang', 'Zhang', 2);
INSERT INTO users VALUES (0, 'wang', 'Paasword02', 'wang', 'wang', 2);
INSERT INTO users VALUES (0, 'zhou', 'Paasword03', 'zhou', 'zhou', 2);
INSERT INTO users VALUES (0, 'zheng', 'Paasword04', 'zheng', 'zheng', 2);

INSERT INTO account VALUES (0, 1, 30000);
INSERT INTO account VALUES (0, 2, 1000);
INSERT INTO account VALUES (0, 3, 7000);
INSERT INTO account VALUES (0, 4, 305);
INSERT INTO account VALUES (0, 5, 10000.00);

commit;

COMMIT;
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
