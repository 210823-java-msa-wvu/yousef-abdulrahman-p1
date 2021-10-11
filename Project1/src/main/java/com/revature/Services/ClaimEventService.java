package com.revature.Services;

import com.revature.Repo.Hibernate.ClaimEventHibernate;
import com.revature.Repo.Hibernate.EmployeeHibernate;
import com.revature.models.ClaimEvent;
import com.revature.models.Employee;

import java.util.List;

public class ClaimEventService {

    ClaimEventHibernate claimEventHibernate = new ClaimEventHibernate();

    public ClaimEvent create(ClaimEvent claimEvent) {
            return claimEventHibernate.createEvent(claimEvent);
    }

    public void approveEventPresentation(Integer event_id, boolean approved) {
        claimEventHibernate.approvePresentation(event_id, approved);
    }

    public void approveGrade(Integer event_id, boolean approved) {
        claimEventHibernate.approveGrade(event_id, approved);
    }

    public void addSecuredGrade(Integer event_id, String secured_grade) {
        ClaimEvent claimEvent = claimEventHibernate.getClaimEvent(event_id);
        claimEvent.setGrade_secured(secured_grade);
        claimEventHibernate.updateClaimEvent(claimEvent);
    }

    public void addEventAttachments(Integer event_id, String attachment) {
        ClaimEvent claimEvent = claimEventHibernate.getClaimEvent(event_id);
        claimEvent.setAttachments(attachment);
        claimEventHibernate.updateClaimEvent(claimEvent);
    }






}
