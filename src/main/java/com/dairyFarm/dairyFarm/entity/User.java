package com.dairyFarm.dairyFarm.entity;

import jakarta.persistence.*;

import java.util.concurrent.ThreadLocalRandom;

@Entity
public class User extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String state;
    private String location;
    private String area;
    private String houseNo;
    private Long hashkey;
    private String role;

    @Column(columnDefinition = "ENUM('Y', 'N') DEFAULT 'N'")
    private String isActive;

    public User(Long id, String name, String email, String mobile, String password, String state, String location, String area, String houseNo, Long hashkey, String isActive, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.location = location;
        this.state = state;
        this.area = area;
        this.houseNo = houseNo;
        this.hashkey = hashkey;
        this.isActive = isActive;
        this.role = role;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getState() {
        return state;
    }

    public String getLocation() {
        return location;
    }

    public String getArea() {
        return area;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getIsActive() {
        return isActive;
    }

    public Long getHashkey() {
        return hashkey;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public void setHashkey(Long hashkey) {
        this.hashkey = hashkey;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //    @PrePersist
//    public void generateReferenceNumber() {
//        if (hashkey == null) {
//            hashkey = ThreadLocalRandom.current().nextLong(100000000000L, 999999999999L);
//        }
//    }
}
