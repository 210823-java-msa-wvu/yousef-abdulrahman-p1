package com.revature.Services;

import com.revature.Repo.Hibernate.DepartmentHibernate;
import com.revature.models.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentHibernate departmentRepo = new DepartmentHibernate();




    public List<Department> getDepartmentsUnderHead(Integer head_id) {
        return departmentRepo.getAllByDepartmentHead(head_id);
    }

    public List<Department> getAllFromDepartment() {
        return departmentRepo.getAll();
    }

    public Department createDepartment(Department a) {
        return departmentRepo.add(a);
    }

    public Department searchByDepartmentId(Integer id) {
        return departmentRepo.getById(id);
    }

    public void updateDepartment(Department a) {
        departmentRepo.update(a);
    }

    public void deleteDepartment(Integer id) {
        departmentRepo.delete(id);
    }

}
