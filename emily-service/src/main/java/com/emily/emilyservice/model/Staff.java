package com.emily.emilyservice.model;

public class Staff {
    private long userId;
    private String userName;
    private int age;
    private String address;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
