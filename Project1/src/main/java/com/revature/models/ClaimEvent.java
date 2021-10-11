package com.revature.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="ClaimEvent")
public class ClaimEvent {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer event_id;
        private String event_date;
        private String event_time;
        private String event_location;
        private String description;
        private Double event_cost;
        private String grading_format;
        private String passing_grade;
        private String event_type;
        private String attachments;
        private String grade_secured;
        private Boolean presentation_uploaded;
        private Boolean presentation_satisfactory;
        private Boolean grade_satisfactory;



        public ClaimEvent() {


        }

    public Double getEvent_cost() {
        return event_cost;
    }

    public void setEvent_cost(Double event_cost) {
        this.event_cost = event_cost;
    }

    public String getPassing_grade() {
        return passing_grade;
    }

    public void setPassing_grade(String passing_grade) {
        this.passing_grade = passing_grade;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public Integer getEvent_id() {
        return event_id;
    }



    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrading_format() {
        return grading_format;
    }

    public void setGrading_format(String grading_format) {
        this.grading_format = grading_format;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getGrade_secured() {
        return grade_secured;
    }

    public void setGrade_secured(String grade_secured) {
        this.grade_secured = grade_secured;
    }

    public Boolean getPresentation_uploaded() {
        return presentation_uploaded;
    }

    public void setPresentation_uploaded(Boolean presentation_uploaded) {
        this.presentation_uploaded = presentation_uploaded;
    }

    public Boolean getPresentation_satisfactory() {
        return presentation_satisfactory;
    }

    public void setPresentation_satisfactory(Boolean presentation_satisfactory) {
        this.presentation_satisfactory = presentation_satisfactory;
    }

    public Boolean getGrade_satisfactory() {
        return grade_satisfactory;
    }

    public void setGrade_satisfactory(Boolean grade_satisfactory) {
        this.grade_satisfactory = grade_satisfactory;
    }
}
