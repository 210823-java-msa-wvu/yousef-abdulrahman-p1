package com.revature.Repo.Hibernate;

import com.revature.Repo.employeeRepo;
import com.revature.models.Employee;
import com.revature.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeHibernate implements employeeRepo {


public Employee getbyusername(String username){
    Session s =HibernateUtil.getSession();
    Query<Employee> query = s.createQuery("from Employee where username=:usn", Employee.class);
    query.setParameter("usn",username);
    Employee empl = query.uniqueResult();
    s.close();
    return empl;
}

    public List<Employee> getAllByDirectSupervisorID(Integer direct_supervisor_id){
        Session s =HibernateUtil.getSession();
        Query<Employee> query = s.createQuery("from Employee where direct_supervisor_id=:id", Employee.class);
        query.setParameter("id",direct_supervisor_id);
        List<Employee> employees = query.getResultList();
        s.close();
        return employees;
    }

public Employee addemployeeinfo(Employee employee) {

    Transaction tx = null;
    try(Session s = HibernateUtil.getSession()){
        tx= s.beginTransaction();
        s.update(employee);

    }catch (HibernateException e ){
        e.printStackTrace();
        if(tx != null){
            tx.rollback();
        }
    }


return null;

}





    @Override
    public Employee add(Employee employee) {

        Session s =HibernateUtil.getSession();

        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
          if (tx!=null) {
              tx.rollback();
          }

        } finally {
            s.close();
        }
        return employee;


    }

    @Override
    public Employee getById(Integer id) {
        Session s = HibernateUtil.getSession();


        Employee a = s.get(Employee.class,id);

        s.close();

        return a;
    }

    @Override
    public List<Employee> getAll() {
       Session s = HibernateUtil.getSession();

       String query = "from employee";

        Query<Employee> q = s.createQuery(query, Employee.class);

        List<Employee> employee = q.getResultList();

        s.close();

        return employee;
    }

    @Override
    public void update(Employee employee) {
    Transaction tx = null;
        try(Session s = HibernateUtil.getSession()){
            tx= s.beginTransaction();
            s.update(employee);

        }catch (HibernateException e ){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }

    }

    @Override
    public void delete(Integer id) {

        Transaction tx = null;

        try(Session s = HibernateUtil.getSession()){
            tx = s.beginTransaction();
            s.delete(id);
            tx.commit();

        }catch(HibernateException e ){
            e.printStackTrace();
            if(tx!=null ) {
                tx.rollback();
            }
        }

    }
}
