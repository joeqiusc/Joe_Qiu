/*
    Set Operations
    
        - UNION
        - UNION ALL
        - MINUS
        - INTERSECT
*/

/*
    UNION
        
        UNION is an operator keyword in SQL that is used to combine the result sets
        of two or more queries. It also removes any duplicate records found in the queries.
        
        Rules for unionin query result sets:
            + Both query results sets must have the same number of columns
            + The datatypes of each column must be compatable with their counterpart.
*/

-- Does not work@ (# of columns in each SELECT statement does not match)
SELECT *
FROM departments
UNION
SELECT department_id
FROM employees;

-- Works! Also note that duplicate records were removed.
SELECT dept_id
FROM departments
UNION
SELECT department_id
FROM employees;

-- UNION ALL preserves duplicate records!
SELECT dept_id
FROM departments
UNION ALL
SELECT department_id
FROM employees;

/*
    INTERSECT
    
        Returns only the rows which are returned by both queries. So, in order for
        a row to be included in the final result set, it must be included in the first
        AND the second queries.
        
        Also, after calculating the intersection of the results sets from the component
        queries, duplicate records are removed.
*/
SELECT dept_id
FROM departments
WHERE monthly_budget > 15000
INTERSECT
SELECT department_id
FROM employees
WHERE monthly_income BETWEEN 2000 AND 2500;

/*
    MINUS
    
        MINUS works in the opposite manner as INTERSECT. Rather than returning the
        results that are shared by both queries, it returns the results that are not
        present in both queries.
*/
-- Returns '2'
SELECT department_id
FROM employees
WHERE monthly_income BETWEEN 2000 AND 2500
MINUS
SELECT dept_id
FROM departments
WHERE monthly_budget > 15000;

-- Returns '1'
SELECT dept_id
FROM departments
WHERE monthly_budget > 15000
MINUS
SELECT department_id
FROM employees
WHERE monthly_income BETWEEN 2000 AND 2500;