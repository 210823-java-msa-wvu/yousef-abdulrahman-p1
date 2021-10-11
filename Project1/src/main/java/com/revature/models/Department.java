package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name="Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer department_id;
    private String name;
    private String description;
    private Integer head_id;

    public Department() { }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHead_id() {
        return head_id;
    }

    public void setHead_id(Integer head_id) {
        this.head_id = head_id;
    }
}
