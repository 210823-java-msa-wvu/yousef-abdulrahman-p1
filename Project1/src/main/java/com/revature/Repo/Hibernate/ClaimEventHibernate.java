package com.revature.Repo.Hibernate;

import com.revature.Repo.employeeRepo;
import com.revature.models.Claim;
import com.revature.models.ClaimEvent;
import com.revature.models.Employee;
import com.revature.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClaimEventHibernate {


    public ClaimEvent createEvent(ClaimEvent event){
        Session s =HibernateUtil.getSession();
        s.save(event);
        s.close();
        return event;
    }

    public void approvePresentation(Integer event_id, boolean approved) {
        Transaction tx = null;
        try(Session s = HibernateUtil.getSession()){
            tx= s.beginTransaction();
            s.createQuery("UPDATE ClaimEvent set presentation_satisfactory = :approval where event_id = :id")
                    .setParameter("approval", approved)
                    .setParameter("id", event_id)
                    .executeUpdate();
            tx.commit();
            s.close();
        }catch (HibernateException e ){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }

    public void approveGrade(Integer event_id, boolean approved) {
        Transaction tx = null;
        try(Session s = HibernateUtil.getSession()){
            tx= s.beginTransaction();
            s.createQuery("UPDATE ClaimEvent set grade_satisfactory = :approval where event_id = :id")
                    .setParameter("approval", approved)
                    .setParameter("id", event_id)
                    .executeUpdate();
            tx.commit();
            s.close();
        }catch (HibernateException e ){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }

    public ClaimEvent getClaimEvent(Integer event_id) {
        Session s = HibernateUtil.getSession();
        Query<ClaimEvent> query = s.createQuery("from ClaimEvent c where c.event_id=:id", ClaimEvent.class);
        query.setParameter("id", event_id);
        ClaimEvent claimEvent = query.getSingleResult();
        s.close();
        return claimEvent;
    }

    public void updateClaimEvent(ClaimEvent event){
        Transaction tx = null;
        try(Session s = HibernateUtil.getSession()){
            tx= s.beginTransaction();
            s.update(event);
            tx.commit();
            s.close();
        }catch (HibernateException e ){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }





}
