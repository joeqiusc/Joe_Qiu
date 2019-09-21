USE `demos`;

/*
Inner join is kind of the join that return records that statisfy a join condition which means that a record from the tfirtst table will only
be made a part of the result set if there is matching record in the second table
*/

select emp_id, emp_fn, emp_ln, dept_name
from employees
join departments
on employees.department_id = departments.dept_id;


select emp_id, emp_fn, emp_ln, dept_name
from employees
inner join departments
on employees.department_id = departments.dept_id
order by emp_id;

/*
we can also alias table names, similar to how we can alias
column names in the comlun list of  a se
*/

select emp_id, emp_fn, emp_ln, dept_name
from employees e
inner join departments d
on e.department_id = d.dept_id
order by emp_id;

ALTER TABLE employees
change column department_id dept_id int(5);

/*
*/

alter table employees
add column manager_id int(5);
insert into employees
values(14, 'AL', 'Green', date '1960-02-05', 10000.00, 4, date '2001-09-01', 'DA Boss', 'ALGREEN', 14);

select * from employees;

update employees
set manager_id =14
where emp_id = 1 or emp_id = 2 or emp_id = 3 or emp_id = 4;

update employees
set manager_id =1
where dept_id = 1 and not emp_id =1;

UPDATE employees
SET manager_id = 2
WHERE dept_id = 2 AND NOT emp_id = 2;

UPDATE employees
SET manager_id = 3
WHERE dept_id = 3 AND NOT emp_id = 3;

UPDATE employees
SET manager_id = 4
WHERE dept_id = 4 AND NOT emp_id = 4;

commit;

select e.emp_id, e.emp_fn, e.emp_ln, e.monthly_income, e.manger_id, em.emp_fn CONCAT(e.emp_fn, ',', e.emp_ln) em.emp_ln AS manger_name
from employees e
join employees em
on e.manger_id = em.emp_id
order by e.manger_id, e.emp_id;

select *
from employees
where monthly_income =
(
select max(monthly_income)
from employees
);


select sysdate()from dual;

select *
from departments;

select *
from employees
where dept_id in
(
    select dept_id
    from departments
    where monthly_budget >20000
)
order by dept_id, emp_id;


