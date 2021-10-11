package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApprovalRequest {

    private String approval_type;
    private Integer claim_id;
    private String comment;
    private String approver_username;
    private Double amount;

    public String getApproval_type() {
        return approval_type;
    }

    public void setApproval_type(String approval_type) {
        this.approval_type = approval_type;
    }

    public Integer getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(Integer claim_id) {
        this.claim_id = claim_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApprover_username() {
        return approver_username;
    }

    public void setApprover_username(String approver_username) {
        this.approver_username = approver_username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
