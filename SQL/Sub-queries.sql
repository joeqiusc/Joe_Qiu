/*
    Sub-queries
    
        A subquery is a SELECT statement that is included, or nested,
        within another SQL DML statement (SELECT, INSERT, UPDATE, DELETE)
        and are always enclosed within parentheses.
*/

-- Need to explore this more for when the subquery provides multiple columns
SELECT *
FROM employees
WHERE monthly_income =
    (
        SELECT MAX(monthly_income)
        FROM employees
    );
    
UPDATE employees
SET monthly_income = 10000
WHERE emp_id = 1;

-- Re-run the above subquery

SELECT *
FROM departments;

/*
    Use IN to handle a subquery that possibly returns more than 
    one record (not more than one column though!)
*/
SELECT *
FROM employees
WHERE dept_id IN
    (
        SELECT dept_id
        FROM departments
        WHERE monthly_budget > 20000
    )
ORDER BY dept_id, emp_id;