package com.revature.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //instance variables
    private Integer employee_id;
    private String username;
    private String password;
    private Boolean benco;
    private Integer direct_supervisor_id;


    public Employee() {

    }

    public Integer getDirect_supervisor_id() {
        return direct_supervisor_id;
    }

    public void setDirect_supervisor_id(Integer direct_supervisor_id) {
        this.direct_supervisor_id = direct_supervisor_id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBenco() {
        return benco;
    }

    public void setBenco(Boolean benco) {
        this.benco = benco;
    }
}
