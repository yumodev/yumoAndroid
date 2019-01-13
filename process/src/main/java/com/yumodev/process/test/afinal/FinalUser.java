package com.yumodev.process.test.afinal;

import java.util.Date;

/**
 * Created by yumo on 2018/4/23.
 */

public class FinalUser {
    private int id;
    private String name;
    private String email;
    private Date registerDate;
    private Double money;

    /////////////getter and setter 不能省略哦///////////////
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
}
