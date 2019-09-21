USE Chinook;

/*  Joe Qiu assignment
2.1 SELECT
Task â€“ Select all records from the Employee table. 
Task â€“ Select all records from the Employee table. 
Task â€“ Select all records from the Employee table where last name is King.
Task â€“ Select all records from the Employee table where first name is Andrew and REPORTSTO is NULL.

*/
SELECT *
FROM employee;

SELECT *
FROM employee
WHERE LastName = 'King';

SELECT *
FROM employee
WHERE FirstName = 'Andrew' 
AND REPORTSTO = null;

/*
2.2 ORDER BY
Task â€“ Select all albums in album table and sort result set in descending order
Task â€“ Select first name from Customer and sort result set in ascending order
*/

SELECT *
FROM album
ORDER BY title DESC;

SELECT FirstName
FROM customer 
ORDER BY FirstName DESC; 

/*
2.3 INSERT INTO
Task â€“ Insert two new records into Genre table
Task â€“ Insert two new records into Employee table
*/

INSERT INTO Genre
(GenreId, Name)
VALUES  
(235, 'null');
INSERT INTO Genre
(GenreId, Name)
VALUES  
(236, 'null');

INSERT INTO `Employee` (`EmployeeId`, `LastName`, `FirstName`, `Title`, `BirthDate`, `HireDate`, `Address`, `City`, `State`, `Country`, `PostalCode`, `Phone`, `Fax`, `Email`) VALUES (10001, N'Adams', N'Andrew', N'General Manager', '1962/2/18', '2002/8/14', N'11120 Jasper Ave NW', N'Edmonton', N'AB', N'Canada', N'T5K 2N1', N'+1 (780) 428-9482', N'+1 (780) 428-3457', N'andrew@chinookcorp.com');
INSERT INTO `Employee` (`EmployeeId`, `LastName`, `FirstName`, `Title`, `ReportsTo`, `BirthDate`, `HireDate`, `Address`, `City`, `State`, `Country`, `PostalCode`, `Phone`, `Fax`, `Email`) VALUES (20001, N'Edwards', N'Nancy', N'Sales Manager', 1, '1958/12/8', '2002/5/1', N'825 8 Ave SW', N'Calgary', N'AB', N'Canada', N'T2P 2T3', N'+1 (403) 262-3443', N'+1 (403) 262-3322', N'nancy@chinookcorp.com');

/*
2.4 UPDATE
Task â€“ Update Aaron Mitchell in Customer table to Robert Walter
Task â€“ Update name of artist â€œCreedence Clearwater Revivalâ€? to â€œCCRâ€?

*/

UPDATE customer
SET FirstName = 'Robert' , LastName = 'Walter' 
WHERE FirstName= 'Aaron' AND LastName = 'Mitchell';


UPDATE artist
SET Name = 'CCR'
WHERE Name = 'Creedence Clearwater Revival';

/*2.5 LIKE
Task â€“ Select all invoices with a billing address like â€œTâ€?
*/

SELECT *
FROM invoice
WHERE BillingAddress
LIKE '%T%';


/*
2.6 BETWEEN
Task â€“ Select all invoices that have a total between 15 and 50
Task â€“ Select all employees hired between 1st of June 2003 and 1st of March 2004

*/
SELECT *
FROM invoice
WHERE total BETWEEN 15 AND 50;

SELECT *
FROM employee
WHERE HireDate BETWEEN '01-JUN-2003' AND '01-MAR-2004';

/*
2.7 DELETE
Task â€“ Delete a record in Customer table where the name is Robert Walter (There may be constraints that rely on this, find out how to resolve them).
*/

DELETE 
FROM customer
WHERE firstName = Robert AND lastName = Walter;



/*
3.1 System Defined Functions
Task â€“ Create a function that returns the current time.

Task â€“ create a function that returns the length of a mediatype from the mediatype table
*/

SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION get_time
    RETURN TIMESTAMP
    IS current_time TIMESTAMP;
    
BEGIN
    SELECT LOCALTIMESTAMP
    INTO current_time
    FROM dual;
    
    RETURN current_time;
    
END;
/

CREATE OR REPLACE FUNCTION getLength (name Varchar2)
RETURN length as
    Begin
        Select * INTO Length;
        return Length;
        END;
        /


/*
3.2 System Defined Aggregate Functions
Task â€“Create a function that returns the average total of all invoices
Task â€“ Create a function that returns the most expensive track
*/

CREATE OR REPLACE FUNCTION my_avg
    RETURN NUMBER
    IS my_avg NUMBER;
BEGIN
    SELECT AVG(total) 
    INTO my_avg
    FROM invoice;
    RETURN my_avg;
END;
/


CREATE OR REPLACE FUNCTION get_max_trackid
    RETURN NUMBER
    AS max_id NUMBER;
    
BEGIN
    SELECT MAX(unitprice)
    INTO max_id
    FROM track; 
    RETURN max_id;
END;
/

/*
3.3 User Defined Scalar Functions
Task â€“ Create a function that returns the average price of invoice-line items in the invoice-line table
*/

CREATE OR REPLACE FUNCTION avg_price
    RETURN NUMBER
    AS avg_price NUMBER;
BEGIN
  SELECT AVG(unitprice)
  INTO avg_price
  FROM invoiceline;
  RETURN avg_price;
END;
/

select avg_price from dual;

/*
3.4 User Defined Table Valued Functions
Task â€“ Create a function that returns all employees who are born after 1968.
*/


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
END;
/


/*
4.0 Stored Procedures
 In this section you will be creating and executing stored procedures. You will be creating various types of stored procedures that take input and output parameters.
 reate a stored procedure that selects the first and last names of all the employees.
*/


CREATE OR REPLACE PROCEDURE full_name(new_name OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN new_name FOR
    SELECT firstname, lastname
    FROM chinook.employee;
    
END;
/


/*
4.2 Stored Procedure Input Parameters
Task  Create a stored procedure that updates the personal information of an employee.
Task   Create a stored procedure that returns the managers of an employee
*/
DECLARE
    first_name   chinook.employee.firstname%TYPE;
    last_name chinook.employee.lastname%TYPE;
    a_cursor  SYS_REFCURSOR;
BEGIN
    full_name(a_cursor);
    
    LOOP
        FETCH a_cursor             
        INTO first_name, last_name; 
        
        EXIT WHEN a_cursor%NOTFOUND;   
        DBMS_OUTPUT.PUT_LINE(first_name || ' ' || last_name);
    END LOOP;
    
    CLOSE a_cursor;
END;
/

SELECT * 
FROM chinook.employee;
GRANT UPDATE ON chinook.employee TO public;

CREATE OR REPLACE PROCEDURE update_employee(
    employeeid IN NUMBER,
    lastname IN VARCHAR2,
    firstname IN VARCHAR2,
    title IN VARCHAR2,
    reportsto IN NUMBER,
    birthdate IN DATE,
    hiredate IN DATE,
    address IN VARCHAR2,
    city IN VARCHAR2,
    current_state IN VARCHAR2,
    country IN VARCHAR2,
    postal_code IN VARCHAR2,
    phoine IN VARCHAR2,
    fax IN VARCHAR2,
    email IN VARCHAR2
    )
    IS
BEGIN
    UPDATE chinook.employee
    SET 
    chinook.employee.lastname = lastname,
    chinook.employee.firstname = firstname,
    chinook.employee.title = title,
    chinook.employee.reportsto = reportsto,
    chinook.employee.birthdate = birthdate,
    chinook.employee.hiredate = hiredate,
    chinook.employee.address = address,
    chinook.employee.city = city,
    chinook.employee.state = current_state,
    chinook.employee.country = country,
    chinook.employee.postalcode = postal_code,
    chinook.employee.phone = phone,
    chinook.employee.fax = fax,
    chinook.employee.email = email
    WHERE chinook.employee.employeeid = employeeid;
    COMMIT;
END;
/

BEGIN
    update_employee(10, 'Walker', 'Tina', 'Sales Support Agent', 2, date '1986-10-12', date '2012-10-05', '8686 Turney Lane', 'Calgary',
        'AB', 'Canada', 'T3R 3Y2', '+1 (403) 793-0021', '+1 (403) 755-1234', 'tina@chinookcorp.com'
    );
END;
/
CALL update_employee(10, 'Walker', 'Tina', 'Sales Support Agent', 2, date '1986-10-12', date '2012-10-05', '8686 Turney Lane', 'Calgary',
        'AB', 'USA', 'T3R 3Y2', '+1 (403) 793-0021', '+1 (403) 755-1234', 'tina@chinookcorp.com'
    );
    
SELECT * FROM chinook.employee WHERE employeeid = 10;



SELECT *
FROM chinook.employee
WHERE employeeid = 10;

CREATE OR REPLACE PROCEDURE update_employee(
    employid IN chinook.employee.employeeid%TYPE,
    lastname IN chinook.employee.lastname%TYPE,
    firstname IN chinook.employee.firstname%TYPE
    )
    IS
BEGIN
    UPDATE chinook.employee
    SET 
    chinook.employee.lastname = lastname,
    chinook.employee.firstname = firstname
    WHERE chinook.employee.employeeid = employid;
    COMMIT;
END;
/


BEGIN
    update_employee(10, 'Walker', 'Honey');
END;
/





create or replace PROCEDURE MANAGER_OF_EMPLOYEE 
(
  THE_EMPLOYEEID IN NUMBER
)
AS 
  TEMP VARCHAR2(20);
  TEMP2 VARCHAR2(20);
  TEMP3 VARCHAR2(20);
  TEMP4 VARCHAR2(20);
BEGIN
  SELECT MGR.FIRSTNAME, MGR.LASTNAME, EMP.FIRSTNAME, EMP.LASTNAME INTO TEMP, TEMP2, TEMP3, TEMP4
  FROM EMPLOYEE EMP
  LEFT OUTER JOIN EMPLOYEE MGR 
  ON MGR.REPORTSTO = EMP.EMPLOYEEID 
  WHERE EMP.EMPLOYEEID = THE_EMPLOYEEID AND EMP.REPORTSTO = MGR.EMPLOYEEID;
  DBMS_OUTPUT.PUT_LINE(TEMP || ' ' || TEMP2 || ' IS THE MANAGER OF ' || TEMP3 || ' ' || TEMP4);
END; 
/


/*
4.3 Stored Procedure Output Parameters
Task  Create a stored procedure that returns the name and company of a customer.
*/


CREATE OR REPLACE PROCEDURE get_customer(customer_id IN NUMBER)
   IS 
   customer_firstname VARCHAR2(50);
   customer_lastname VARCHAR2(50);
   customer_company VARCHAR2(50);
   
BEGIN
    SELECT firstname, lastname, company
    INTO customer_firstname, customer_lastname, customer_company
    FROM chinook.customer
    WHERE customerid = customer_id;
END;
/


BEGIN
    get_customer(2);
END;
/


/*
In this section you will be working with transactions. Transactions are usually nested within a stored procedure. You will also be working with handling errors in your SQL.
Task – Create a transaction that given a invoiceId will delete that invoice (There may be constraints that rely on this, find out how to resolve them)...poiij
Task – Create a transaction nested within a stored procedure that inserts a new record in the Customer table
*/
DECLARE 
    invoice_id NUMBER(5);
BEGIN
    invoice_id := 10;
    DELETE FROM chinook.invoiceline WHERE invoiceid = invoice_id;
    DELETE FROM chinook.invoice WHERE invoiceid = invoice_id;
    
    IF SQL%NOTFOUND THEN
        RAISE_APPLICATION_ERROR (-20201, invoice_id || ' does not exist');
    END IF;
    
    COMMIT;

END;
/


GRANT INSERT ON chinook.customer TO public;

CREATE OR REPLACE PROCEDURE create_customer(
    customerid IN chinook.customer.customerid%TYPE,
    firstname IN chinook.customer.firstname%TYPE,
    lastname IN chinook.customer.lastname%TYPE,
    company IN chinook.customer.company%TYPE,
    address IN chinook.customer.address%TYPE,
    city IN chinook.customer.city%TYPE,
    customer_state IN chinook.customer.state%TYPE,
    country IN chinook.customer.country%TYPE,
    postalcode IN chinook.customer.postalcode%TYPE,
    phone IN chinook.customer.phone%TYPE,
    fax IN chinook.customer.fax%TYPE,
    email IN chinook.customer.email%TYPE,
    supportrepid IN chinook.customer.supportrepid%TYPE
    )
    IS
BEGIN
    DECLARE
    error_1 VARCHAR2(50);
    error_2 VARCHAR(50);
    BEGIN
    error_1 := 'The index has already been used.';
    error_2 := 'Error on INSERT.';
    
    
        INSERT INTO chinook.customer VALUES(customerid, firstname, lastname, company, address, city, customer_state, country, postalcode, phone, fax, email, supportrepid);
        COMMIT;
        
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE(error_1);
            --error_1;
            ROLLBACK;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(error_2);
            ROLLBACK;
    END;
END;
/


/*
6.0 Triggers 
In this section you will create various kinds of triggers that work when certain DML statements are executed on a table.

6.1 AFTER/FOR
Task - Create an after insert trigger on the employee table fired after a new record is inserted into the table.
Task – Create an after update trigger on the album table that fires after a row is inserted in the table
Task – Create an after delete trigger on the customer table that fires after a row is deleted from the table.

*/


set serveroutput on;
GRANT INSERT ON chinook.employee TO public;

CREATE OR REPLACE TRIGGER after_insert
AFTER INSERT ON chinook.employee

FOR EACH ROW
DECLARE
    e_id NUMBER;
BEGIN 
                                                                
    DBMS_OUTPUT.PUT_LINE('Employee added successfully.');

END;
/
SELECT * FROM chinook.employee ORDER BY employeeid;
INSERT INTO chinook.employee VALUES (12, 'bob', 'simmons', 'Associate', 3, date '1970-12-11', date '2012-10-10', 
                                    '1500 Way Ave.', 'Witchita', 'Kansas', 'US', '67325', '888-111-2323', '675 000 2323', 'bob@bob.com');
CREATE OR REPLACE TRIGER emp_intsrt_triger
BEFORE INSERT ON chinook.employee
FOR EACH ROW

BEGIN 
    SELECT emp_pk_seq.NEXTVAL
    INTO: new.employeeid
    FROM dual;
    
END;
/
CREATE OR REPLACE TRIGGER after_album_update
AFTER UPDATE 
ON chinook.album 

FOR EACH ROW
BEGIN
     DBMS_OUTPUT.PUT_LINE('Album updated successfully.');  
END;
/

SELECT *
FROM chinook.album;

UPDATE chinook.album
SET title = 'BC'
WHERE title = 'Body Count';



--Task – Create an after delete trigger on the customer table that fires after a row is deleted from the table.
CREATE OR REPLACE TRIGGER after_delete_customer
AFTER DELETE
ON chinook.customer

FOR EACH ROW
BEGIN
    DBMS_OUTPUT.PUT_LINE('Customer was removed.');  
END;
/



--Task – Create a before trigger that restricts the deletion of any invoice that is priced over 50 dollars.
CREATE OR REPLACE TRIGGER invoice_delete_check
BEFORE DELETE ON chinook.invoice
FOR EACH ROW

DECLARE 
amount NUMBER(5);

BEGIN
    amount := :old.total;
    IF price > 50 
        THEN
        raise_application_error(-20001,'Records can not be deleted');
    END IF;
END;
/
SELECT * FROM chinook.invoice WHERE total > 50;

/*

7.0 JOINS
In this section you will be working with combining various tables through the use of joins. You will work with outer, inner, right, left, cross, and self joins.

7.1 INNER
Task – Create an inner join that joins customers and orders and specifies the name of the customer and the invoiceId.
7.2 OUTER
Task – Create an outer join that joins the customer and invoice table, specifying the CustomerId, firstname, last name, invoiceId, and total.

*/



select customer.customerid, customer.firstname, customer.lastname,invoice.invoiceid, invoice.invoiceId,invoice.total
from customer
inner join invoice
on customer.customerid = invoice.customerid
order by customerid;

select customer.customerid, customer.firstname, customer.lastname,invoice.invoiceid, invoice.invoiceId,invoice.total
from customer
full outer join invoice
on customer.customerid = invoice.customerid
order by customerid;


/*
7.3 RIGHT
Task – Create a right join that joins album and artist specifying artist name and title.

*/
select artist.name, album.title
from album
right join artist
on album.artistid= artist.artistid;



/*
7.4 CROSS
Task – Create a cross join that joins album and artist and sorts by artist name in ascending order.
*/

select * 
from album
cross join 
artist
order by artist.name ASC;

/*
7.5 SELF
Task – Perform a self-join on the employee table, joining on the reports to column.
*/

SELECT 
    concat(employee.firstname, ' ',  employee.lastname)  as name
FROM
    employee 
LEFT JOIN
    employee  ON manager.employeeid = employee.reportsto
ORDER BY manager;

select * from employee;
 

SELECT a.column_name, b.column_name... 
FROM table1 a, table1 b 
WHERE a.common_filed = b.common_field;
/*
8.0 Indexes
In this section you will be creating Indexes on various tables. Indexes can speed up performance of reading data.
8.1 Indexes
Task – Create an index on of table of your choice
*/

create index art_index
on album (artistid);


