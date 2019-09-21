-- This allows the DBMS to print messages to the console (OFF by default)
SET SERVEROUTPUT ON;

-- Function: Get the max id from the artist table

-- Method signature (method name, return type, variables to be returned)
CREATE OR REPLACE FUNCTION get_max_id
    RETURN NUMBER
    AS max_id NUMBER;
    
BEGIN
    SELECT MAX(artistid)
    INTO max_id
    FROM chinook.artist;
    
    RETURN max_id;
END;
/

DECLARE
    maxId NUMBER;
BEGIN
    maxId := get_max_id();
    DBMS_OUTPUT.PUT_LINE('The max id in the artist table is: ' || maxId);
END;
/

-- Remove the stored function get_max_id from the DB.
DROP FUNCTION get_max_id;

/*
    Cursors
    
        In PL/SQL, a cursor is a variable data type that can 
        hold and entire result as its value.
*/

-- Task - Create a function that returns all employees who are born after 1968
CREATE OR REPLACE FUNCTION after_1968
    RETURN SYS_REFCURSOR
    IS my_cursor SYS_REFCURSOR;
    
BEGIN
    OPEN my_cursor FOR
    SELECT firstname, lastname, birthdate
    FROM chinook.employee
    GROUP BY firstname, lastname, birthdate
    HAVING MIN(birthdate) > date '1968-12-31'
    ORDER BY birthdate;
    
    RETURN my_cursor;
    
    /* Does the same thing as the above query
        SELECT firstname, lastname, birthdate
        FROM chinook.employee
        WHERE birthdate > date '1968-12-31'
        ORDER BY birthdate;
    */
END;
/

SELECT after_1968()
FROM dual;

-- Create a procedure that returns all of the artists in the artist table
CREATE OR REPLACE PROCEDURE get_all_artists(my_cursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN my_cursor FOR
    SELECT *
    FROM chinook.artist
    ORDER BY artistid;
END;
/

DECLARE
    artist_id   chinook.artist.artistid%TYPE;
    artist_name chinook.artist.name%TYPE;
    tha_cursor  SYS_REFCURSOR;
BEGIN
    get_all_artists(tha_cursor);
    
    LOOP
        FETCH tha_cursor             -- fetchs the next record stored in the cursor
        INTO artist_id, artist_name; -- puts the values in the current record in our variables
        
        EXIT WHEN tha_cursor%NOTFOUND;   -- exit the loop when there are no more records
        DBMS_OUTPUT.PUT_LINE('Artist id: ' || artist_id || ', Name: ' || artist_name);
    END LOOP;
    
    CLOSE tha_cursor;
END;
/

------------------------------------------------------------------
DROP SEQUENCE artist_pk_seq;
-- Create a sequence that will be used to increment PKs on the artist table
CREATE SEQUENCE artist_pk_seq
MINVALUE 1
MAXVALUE 9999999999999
INCREMENT BY 1
START WITH 276;
/

-- Create a trigger that will use artist_pk_seq whenever a new row
CREATE OR REPLACE TRIGGER artist_insert_trig
BEFORE INSERT ON chinook.artist
FOR EACH ROW

BEGIN
    SELECT artist_pk_seq.NEXTVAL
    INTO :new.artistid
    FROM dual;
END;
/

SELECT *
FROM chinook.artist
ORDER BY artistid;

INSERT INTO chinook.artist (name)
VALUES ('Blink-182');

INSERT INTO chinook.artist (name)
VALUES ('Snails House');

INSERT INTO chinook.artist
VALUES (300, 'Atreyu');

/*
    Indexes
        
        Provides a method of quickly looking up values from a table without
        having to look at EVERY attribute of a record.
*/
CREATE INDEX emp_index
ON chinook.employee (employeeId, firstname, lastname);