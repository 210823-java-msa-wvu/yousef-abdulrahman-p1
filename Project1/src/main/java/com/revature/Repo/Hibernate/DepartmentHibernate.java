package com.revature.Repo.Hibernate;

import com.revature.models.Department;
import com.revature.models.Employee;
import com.revature.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentHibernate {


    public List<Department> getAllByDepartmentHead(Integer head_id) {
        Session s = HibernateUtil.getSession();
        Query<Department> query = s.createQuery("from Department where head_id=:id", Department.class);
        query.setParameter("id", head_id);
        List<Department> departments= query.getResultList();
        s.close();
        return departments;
    }

    public Department updateDepartmentDetails(Department department) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSession()) {
            tx = s.beginTransaction();
            s.update(department);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return department;
    }


    public Department add(Department department) {
        Session s = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.save(department);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            s.close();
        }
        return department;
    }

    public Department getById(Integer id) {
        Session s = HibernateUtil.getSession();
        Department department = s.get(Department.class, id);
        s.close();
        return department;
    }

    public List<Department> getAll() {
        Session s = HibernateUtil.getSession();
        String query = "from employee";
        Query<Department> q = s.createQuery(query, Department.class);
        List<Department> departments = q.getResultList();
        s.close();
        return departments;
    }

    public void update(Department department) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSession()) {
            tx = s.beginTransaction();
            s.update(department);

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public void delete(Integer id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSession()) {
            tx = s.beginTransaction();
            s.delete(id);
            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
