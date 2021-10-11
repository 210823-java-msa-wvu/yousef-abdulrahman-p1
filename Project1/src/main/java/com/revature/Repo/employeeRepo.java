package com.revature.Repo;

import com.revature.models.Employee;

import java.util.List;

public interface employeeRepo extends CrudRepository<Employee>{

    @Override
    Employee add(Employee employee);

    @Override
    Employee getById(Integer employee_id);

      List<Employee> getAll();

    @Override
    void update(Employee employee);

    @Override
    void delete(Integer id);
}
