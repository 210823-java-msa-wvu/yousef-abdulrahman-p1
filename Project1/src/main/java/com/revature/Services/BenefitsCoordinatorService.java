package com.revature.Services;

import com.revature.exception.InvalidException;
import com.revature.models.Claim;

public class BenefitsCoordinatorService {

    private ClaimService claimService = new ClaimService();
    private ValidationService validationService = new ValidationService();
    private ClaimEventService claimEventService = new ClaimEventService();

    public void reject(Integer claim_id, String message) throws InvalidException {
        validationService.validateMessage(message);
        Claim claim = claimService.getClaimById(claim_id);
        claim.setApproval_benco("REJECTED");
        claim.setComment_benco(message);
        claimService.updateClaim(claim);
    }

    public void approve(Integer claim_id) {
        Claim claim = claimService.getClaimById(claim_id);
        claim.setApproval_benco("APPROVED");
        claimService.updateClaim(claim);
    }

    public void askForMoreInfo(Integer claim_id, String message, String type) throws InvalidException {
        validationService.validateMoreInfoRequestByBenco(type);
        Claim claim = claimService.getClaimById(claim_id);
        claim.setApproval_benco(type);
        claim.setComment_benco(message);
        claimService.updateClaim(claim);
    }

    public void approveGrade(Integer claim_id, boolean approved) {
        Claim claim = claimService.getClaimById(claim_id);
        claimEventService.approveGrade(claim.getClaim_id(), approved);

    }

    public void changeClaimAmount(Integer claim_id, Double new_amount, String reason) throws InvalidException {
        Claim claim = claimService.getClaimById(claim_id);
        if(claim.getClaim_amount() < new_amount) {
            validationService.validateMessage(reason);
        }
        claimService.updateClaimAmount(claim_id, new_amount, reason);
        if(claim.getApproval_direct_supervisor().contentEquals("APPROVED") &&
                claim.getApproval_department_head().contentEquals("APPROVED") &&
                claim.getApproval_benco().contentEquals("APPROVED")) {
            Double total = claimService.getClaimAmountForLastYear(claim.getEmployee_id());

            String message = "AWARDED";
            if(total > 1000) {
                message = message.concat("(EXCEEDING TOTAL)");
            }
            claim.setNew_amount(new_amount);
            claim.setClaim_status(message);
            claimService.updateClaim(claim);
        }
    }

}
