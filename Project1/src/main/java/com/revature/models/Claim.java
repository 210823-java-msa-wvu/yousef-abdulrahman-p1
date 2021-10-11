package com.revature.models;

import javax.persistence.*;
@Entity
@Table(name="Claim")
public class Claim {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer claim_id;
//        private Integer event_id;
        private String work_related_justification;
        private Double claim_amount;
        private Integer employee_id;
        private Double work_time_missed;
        private String approval_attachement_direct_supervisor;
        private String approval_attachement_department_head;
        private Boolean urgent;
        private String approval_direct_supervisor;
        private String comment_direct_supervisor;
        private String approval_department_head;
        private String comment_department_head;
        private String approval_benco;
        private String comment_benco;
        private Double new_amount;
        private String alter_reason;
        private String claim_status;


        @ManyToOne
        @JoinColumn(name = "event_id")
        private ClaimEvent claimEvent;

    public void setClaimEvent(ClaimEvent claimEvent) {
        this.claimEvent = claimEvent;
    }

    public ClaimEvent getClaimEvent() {
        return claimEvent;
    }


    public Claim() { }

    public Integer getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(Integer claim_id) {
        this.claim_id = claim_id;
    }
//
//    public Integer getEvent_id() {
//        return event_id;
//    }
//
//    public void setEvent_id(Integer event_id) {
//        this.event_id = event_id;
//    }

    public String getWork_related_justification() {
        return work_related_justification;
    }

    public void setWork_related_justification(String work_related_justification) {
        this.work_related_justification = work_related_justification;
    }

    public Double getClaim_amount() {
        return claim_amount;
    }

    public void setClaim_amount(Double claim_amount) {
        this.claim_amount = claim_amount;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Double getWork_time_missed() {
        return work_time_missed;
    }

    public void setWork_time_missed(Double work_time_missed) {
        this.work_time_missed = work_time_missed;
    }

    public String getApproval_attachement_direct_supervisor() {
        return approval_attachement_direct_supervisor;
    }

    public void setApproval_attachement_direct_supervisor(String approval_attachement_direct_supervisor) {
        this.approval_attachement_direct_supervisor = approval_attachement_direct_supervisor;
    }

    public String getApproval_attachement_department_head() {
        return approval_attachement_department_head;
    }

    public void setApproval_attachement_department_head(String approval_attachement_department_head) {
        this.approval_attachement_department_head = approval_attachement_department_head;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public String getApproval_direct_supervisor() {
        return approval_direct_supervisor;
    }

    public void setApproval_direct_supervisor(String approval_direct_supervisor) {
        this.approval_direct_supervisor = approval_direct_supervisor;
    }

    public String getComment_direct_supervisor() {
        return comment_direct_supervisor;
    }

    public void setComment_direct_supervisor(String comment_direct_supervisor) {
        this.comment_direct_supervisor = comment_direct_supervisor;
    }

    public String getApproval_department_head() {
        return approval_department_head;
    }

    public void setApproval_department_head(String approval_department_head) {
        this.approval_department_head = approval_department_head;
    }

    public String getComment_department_head() {
        return comment_department_head;
    }

    public void setComment_department_head(String comment_department_head) {
        this.comment_department_head = comment_department_head;
    }

    public String getApproval_benco() {
        return approval_benco;
    }

    public void setApproval_benco(String approval_benco) {
        this.approval_benco = approval_benco;
    }

    public String getComment_benco() {
        return comment_benco;
    }

    public void setComment_benco(String comment_benco) {
        this.comment_benco = comment_benco;
    }

    public Double getNew_amount() {
        return new_amount;
    }

    public void setNew_amount(Double new_amount) {
        this.new_amount = new_amount;
    }

    public String getAlter_reason() {
        return alter_reason;
    }

    public void setAlter_reason(String alter_reason) {
        this.alter_reason = alter_reason;
    }

    public String getClaim_status() {
        return claim_status;
    }

    public void setClaim_status(String claim_status) {
        this.claim_status = claim_status;
    }
}

