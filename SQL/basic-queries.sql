/*
    In a SELECT statement, you need to specify at least:
    
        - the columns you want to retrieve (column list)
        - the table where those values are stored (FROM clause)
*/
-- Get all information from the products table
SELECT *
FROM products;

-- Specify certain columns for retrieval
SELECT prod_name, price
FROM products;

-- Not many restrictions on what columns can be included in the result set
SELECT prod_name, price, 'test', (price / 2) AS sale_price -- column aliasing
FROM products;

-- Get the first and last names of all employees with their monthly incomes
SELECT emp_fn, emp_ln, monthly_income
FROM employees;

SELECT emp_id, emp_fn, emp_ln, monthly_income, (monthly_income * 1.2) AS raise
FROM employees;

/*
    WHERE clauses
*/

SELECT prod_id, prod_name, price
FROM products
WHERE price < 10;

-- Retrieve all product info from products where expiration dates are earlier than 01APR2019
SELECT *
FROM products
WHERE expiration_date < '01-APR-2019';

/*
    NOTE: When you use strings to represent dates, as in the previous example, Oracle uses the date 
    format defined in your system to convert the string to an actual date. So even though it works 
    correctly for you nothing guarantees that the same statement will work as intended on another 
    system.
*/

/*
    Complex WHERE clauses
*/

SELECT *
FROM products
WHERE
    (
        prod_id < 5
        OR prod_name = 'Amoxicillin' 
        AND price > 10
    )
    AND 
    (
        price <= 10
        OR expiration_date = DATE '2018-04-30'
    );
    
/*
    DISTINCT
*/

SELECT department_id
FROM employees;

-- eliminates duplicate query results
SELECT DISTINCT department_id
FROM employees;

-- A record is considered a duplicate if ALL the retrieved columns match value
SELECT DISTINCT emp_fn, department_id
FROM employees;

-- The UNIQUE keyword can used in place of DISTINCT (not recommended)
-- SELECT UNIQUE department_id
-- FROM employees;

/*
    ORDER BY
*/
-- Selects all employees in ascending order based on their birthdate
SELECT *
FROM employees
ORDER BY birthdate ASC;

-- The ASC keyword is not required
SELECT *
FROM employees
ORDER BY birthdate;

-- Select all employees in descending order based on birthdate
SELECT *
FROM employees
ORDER BY birthdate DESC;

-- We can apply more than one sorting criteria
SELECT *
FROM employees
ORDER BY department_id DESC, monthly_income;

-- We can use the column positions instead of the names (makes it hard to read)
SELECT *
FROM employees
ORDER BY 6 DESC, 5;

/*
    Scalar Functions
    
        Scalar functions, aka single-row functions, return a value for every
        row that is processed in a query. There are four types/categories of
        scalar functions that we should be familiar with:
        
            - Numeric functions
                + ABS()
                + CEIL(), FLOOR()
                + TRUNC(), ROUND()
                
            - Character/Text functions
                + LOWER(), UPPER(), INITCAP()
                + LTRIM(), RTRIM(), TRIM()
                + SUBSTR()
                + LENGTH()
                
            - Date functions
                + ADD_MONTH()
                + MONTHS_BETWEEN()
                + SYSTIME
                
            - Conversion functions
                + TO_CHAR()
                + TO_DATE()
                + TO_NUMBER()
*/

/*
    Aggregate Functions
    
        Functions that operate on a group of rows at the same time and provide
        a single value, as opposed to scalar functions which operate on only a
        single row at a time.
*/
SELECT MIN(price) AS minimum_price, MAX(price) AS maximum_price
FROM products;

SELECT MIN(price), MAX(price), AVG(price), SUM(price)
FROM products;

SELECT COUNT(prod_name)
FROM products;

ALTER TABLE products
ADD alternate_name VARCHAR(25);

UPDATE products
SET alternate_name = SUBSTR(prod_name, 1, 3)
WHERE prod_id < 4;

SELECT *
FROM products;

SELECT COUNT(prod_id), COUNT(alternate_name)
FROM products;

/*
    GROUP BY
    
        The GROUP BY clause groups the result set into groups and most of the time,
        it is used with aggregate functions. When that is the case the aggregate function
        is applied to each group, and the results include one row per group.
*/

/*
    Retrieve all the rows in the employees table and group them by department_id
    and then apply the count function to each group.
*/
SELECT department_id, COUNT(*)
FROM employees
GROUP BY department_id
ORDER BY department_id;

/*
    HAVING clause
    
        The HAVING clause is used in a similar manner as the WHERE clause, just
        as the GROUP BY clause is similar to the ORDER BY clause. In fact, the
        same difference between these two applies to HAVING and WHERE. The HAVING
        clause is used with aggregate functions.
*/

-- Retrieves only departments whose smallest salary is less than 2000 or their highest salary is
-- greater than 4000. The results are displayed in descending order by the salary.
SELECT department_id, MIN(monthly_income) AS min_income, MAX(monthly_income) AS max_income
FROM employees
GROUP BY department_id
    HAVING
        MIN(monthly_income) < 2000
        OR
        MAX(monthly_income) > 4000
    ORDER BY MIN(monthly_income) DESC;
    
/*
    More on the HAVING clause
    
    Key points here are that you can use conditions that involve aggregate functions in the HAVING clause, 
    and you can also order the results by including an aggregate function in the HAVING clause. 
    
    Remember that you can do these 2 things even if the aggregate functions are not used or referenced in 
    the SELECT list of the query.
*/

/*
    Logical Operators
    
    Logical operators are those that are true or false. The return true or false values to combine 
    one or more true or false values.
    
    - AND
        + compares between two Booleans as expression and returns true when both expressions are true.
        
    - OR
        + compares between two Booleans as expression and returns true when one of the expression is true.
        
    - NOT
        + takes a single Boolean as an argument and changes its value from false to true or from true 
        to false.
        
    - IN
        + checks a value within a set of values separated by commas and retrieve the rows from the 
          table which are matching.
        
    - BETWEEN
        + tests an expression against a range. The range consists of a beginning, followed by an AND 
          keyword and an end expression.
        
    - ANY
        + compares a value to each value in a list or results from a query and evaluates to true if the 
          result of an inner query contains at least one row.
        
    - ALL
        + used to select all records of a SELECT statement. It compares a value to every value in a list 
          or results from a query.
          
        + must be preceded by the comparison operators and evaluates to TRUE if the query returns no rows.
        
    - SOME
        + compare a value to each value in a list or results from a query and evaluate to true if the 
          result of an inner query contains at least one row.
        
    - EXISTS
        + checks the existence of a result of a subquery.
        + tests whether a subquery fetches at least one row
        + when no data is returned then this operator returns 'FALSE'.
*/

select * from departments;
select * from products;

/*
union is an oprator keyword in SQL  union get rid of dupulicate, union all keep the dupulicate
*/

select dept_id
From departments
union
select department_id
from employees;

select dept_id
From departments
union all
select department_id
from employees;

/*
INTERSECTION ruturns  only the rows which are return
*/

select dept_id
from departments
where monthly_budget > 15000;
INTERSECT
select department_id
from employees
where monthly_income between 2000 and 2500; 


select department_id
from employees
where monthly_income between 2000 and 2500; 
minus
select dept_id
from departments
where monthly_budget > 15000;