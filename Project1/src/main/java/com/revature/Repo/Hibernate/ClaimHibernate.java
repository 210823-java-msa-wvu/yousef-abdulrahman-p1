package com.revature.Repo.Hibernate;

import com.revature.models.Claim;
import com.revature.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClaimHibernate {


    public Claim createClaim(Claim claim) {
        Session s = HibernateUtil.getSession();
        s.save(claim);
        s.close();
        return claim;
    }

    public List<Claim> getClaimsForEmployeeForLastYear(Integer employee_id) {
        Session s = HibernateUtil.getSession();
        Query<Claim> query = s.createQuery("from Claim c where c.employee_id=:id AND c.claim_status in ('AWARDED','AWARDED(EXCEEDING TOTAL)')", Claim.class);
        query.setParameter("id", employee_id);
        List<Claim> claims = query.getResultList();
        s.close();
        return claims;
    }

    public List<Claim> getClaimsForEmployee(Integer employee_id) {
        Session s = HibernateUtil.getSession();
        Query<Claim> query = s.createQuery("from Claim c where c.employee_id=:id", Claim.class);
        query.setParameter("id", employee_id);
        List<Claim> claims = query.getResultList();
        s.close();
        return claims;
    }

    public List<Claim> getAllClaims() {
        Session s = HibernateUtil.getSession();
        Query<Claim> query = s.createQuery("from Claim c", Claim.class);
        List<Claim> claims = query.getResultList();
        s.close();
        return claims;
    }

    public List<Claim> getClaimsUnderDirectSupervisor(Integer direct_supervisor_id) {
        Session s = HibernateUtil.getSession();
        Query<Claim> query = s.createQuery("from Claim c where c.employee_id in (select employee_id from Employee where direct_supervisor_id=:id)", Claim.class);
        query.setParameter("id", direct_supervisor_id);
        List<Claim> claims = query.getResultList();
        s.close();
        return claims;
    }


    public Claim updateClaim(Claim claim) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSession()) {
            tx = s.beginTransaction();
            s.update(claim);
            tx.commit();
            s.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return claim;
    }


    public Claim getById(Integer id) {
        Session s = HibernateUtil.getSession();
        Claim claim = s.get(Claim.class, id);
        s.close();
        return claim;
    }

    public List<Claim> getAll() {
        Session s = HibernateUtil.getSession();
        String query = "from Claim";
        Query<Claim> q = s.createQuery(query, Claim.class);
        List<Claim> claims = q.getResultList();
        s.close();
        return claims;
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
