package com.revature.Services;

import com.revature.exception.InvalidException;
import com.revature.models.ApprovalRequest;
import com.revature.models.Claim;
import com.revature.models.ClaimEvent;

public class ValidationService {

    public void validateIfStringPresent(String value) throws InvalidException {
        if(value == null || value.isEmpty())
            throw new InvalidException("Value should be present");
    }
    public void validateIfIntegerPresent(Integer value) throws InvalidException {
        if(value == null)
            throw new InvalidException("Value should be present");
    }

    public void validateClaimInput(Claim clam) {

    }

    public void validateClaimEventInput(ClaimEvent claimEvent) {

    }

    public void validateApprovalRequest(ApprovalRequest request) throws InvalidException {
        validateIfStringPresent(request.getApprover_username());
        validateIfIntegerPresent(request.getClaim_id());
        validateIfStringPresent(request.getApproval_type());
    }

    public void validSecuredGrade(String secured_grade) throws InvalidException {
        validateIfStringPresent(secured_grade);
    }

    public void validateMessage(String message) throws InvalidException {
        if(message == null || message.isEmpty())
            throw new InvalidException("Invalid message");
    }

    public void validateMoreInfoRequestByBenco(String type) throws InvalidException {
        if(type == null ||!(type.contentEquals("NEED_INFO_FROM_EMPLOYEE") || type.contentEquals("NEED_INFO_FROM_DIRECT_SUPERVISOR")
         || type.contentEquals("NEED_INFO_FROM_DEPARTMENT_HEAD"))) {
            throw new InvalidException("Invalid info request type");
        }
    }

    public void validateMoreInfoRequestByDepartmentHead(String type) throws InvalidException {
        if(type == null ||!(type.contentEquals("NEED_INFO_FROM_EMPLOYEE") || type.contentEquals("NEED_INFO_FROM_DIRECT_SUPERVISOR"))) {
            throw new InvalidException("Invalid info request type");
        }
    }

    public void validateMoreInfoRequestByDirectSupervisor(String type) throws InvalidException {
        if(type == null ||!(type.contentEquals("NEED_INFO_FROM_EMPLOYEE"))) {
            throw new InvalidException("Invalid info request type");
        }
    }
}
