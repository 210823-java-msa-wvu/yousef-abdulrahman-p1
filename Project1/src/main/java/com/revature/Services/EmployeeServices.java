package com.revature.Services;

import com.revature.Repo.Hibernate.EmployeeHibernate;
import com.revature.models.Employee;

import java.util.List;

public class EmployeeServices {

    EmployeeHibernate employeeRepo = new EmployeeHibernate();
    private DepartmentService departmentService = new DepartmentService();

    public boolean login(String username, String password) {
            Employee u = employeeRepo.getbyusername(username);
            if(u==null)
               return false;

           if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {

               return true;

           }
           return false;
       }



       public Employee getEmployeeByUsername(String username){
           return employeeRepo.getbyusername(username);
       }


    public boolean isDepartmentHead(int user_id) {
        return !departmentService.getDepartmentsUnderHead(user_id).isEmpty();
    }

    public boolean isDirectSupervisor(int user_id) {
        return !employeeRepo.getAllByDirectSupervisorID(user_id).isEmpty();
    }

    public boolean iBenco(String userName) {
        Employee employee = employeeRepo.getbyusername(userName);
        return employee.getBenco();
    }


    public List<Employee> getAllfromEmployee() {
        return employeeRepo.getAll();
    }

    public Employee createemployee(Employee a) { return employeeRepo.add(a);}

    public Employee searchbyemployeeId(Integer id) { return employeeRepo.getById(id);}

    public void updateemployee(Employee a) {
        employeeRepo.update(a);
    }

    public void deleteAuthor(Integer id) {
        employeeRepo.delete(id);
    }



}
