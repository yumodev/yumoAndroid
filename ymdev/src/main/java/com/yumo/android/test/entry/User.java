package com.yumo.android.test.entry;

import com.google.gson.Gson;


/**
 * Created by yumodev on 17/1/9.
 */

public class User {
    public Long id;
    public String objectId;
    public String username;
    public String password;
    public String phone;
    public String sessionToken;
    public Boolean mobilePhoneVerified;
    public String email;
    public Boolean emailVerified;
    public String createAt;
    public String updateAt;
    public String lastLoginTime;
    public int sex;
    public int age;

    @Override
    public String toString(){
       return (new Gson()).toJson(this);
    }
    public User(Long id, String objectId, String username, String password,
            String phone, String sessionToken, Boolean mobilePhoneVerified,
            String email, Boolean emailVerified, String createAt, String updateAt,
            String lastLoginTime, int sex, int age) {
        this.id = id;
        this.objectId = objectId;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.sessionToken = sessionToken;
        this.mobilePhoneVerified = mobilePhoneVerified;
        this.email = email;
        this.emailVerified = emailVerified;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.lastLoginTime = lastLoginTime;
        this.sex = sex;
        this.age = age;
    }
    public User() {
    }
    public Long getId() {
        return this.id;
    }


    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean getEmailVerified() {
        return this.emailVerified;
    }
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public Boolean getMobilePhoneVerified() {
        return this.mobilePhoneVerified;
    }
    public void setMobilePhoneVerified(Boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }
    public String getCreateAt() {
        return this.createAt;
    }
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
    public String getUpdateAt() {
        return this.updateAt;
    }
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSessionToken() {
        return this.sessionToken;
    }
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
