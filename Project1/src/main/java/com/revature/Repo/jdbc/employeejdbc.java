package com.revature.Repo.jdbc;

import com.revature.Repo.employeeRepo;
import com.revature.models.Employee;
import com.revature.utils.ConnectionUtil;

import java.util.List;

public class employeejdbc implements employeeRepo {

    ConnectionUtil cu = ConnectionUtil.getConnectionUtil();








    @Override
    public Employee add(Employee employee) {
        return null;
    }

    @Override
    public Employee getById(Integer id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public void delete(Integer id) {

    }
}
