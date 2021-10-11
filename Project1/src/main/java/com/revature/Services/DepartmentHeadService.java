package com.revature.Services;

import com.revature.Repo.Hibernate.ClaimHibernate;
import com.revature.exception.InvalidException;
import com.revature.models.Claim;

public class DepartmentHeadService {

    private ClaimHibernate claimHibernate = new ClaimHibernate();
    private ValidationService validationService = new ValidationService();


    public void reject(Integer claim_id, String message) throws InvalidException {
        validationService.validateMessage(message);
        Claim claim = claimHibernate.getById(claim_id);
        claim.setApproval_department_head("REJECTED");
        claim.setComment_department_head(message);
        claimHibernate.updateClaim(claim);
    }

    public void approve(Integer claim_id) {
        Claim claim = claimHibernate.getById(claim_id);
        claim.setApproval_department_head("APPROVED");
        claimHibernate.updateClaim(claim);
    }

    public void askForMoreInfo(Integer claim_id, String message, String type) throws InvalidException {
        validationService.validateMoreInfoRequestByDepartmentHead(type);
        Claim claim = claimHibernate.getById(claim_id);
        claim.setApproval_department_head(type);
        claim.setComment_department_head(message);
        claimHibernate.updateClaim(claim);
    }

}
