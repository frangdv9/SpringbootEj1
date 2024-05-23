package com.apidv.apidv.models;

public class User {
    private String name;
    private String email;
    private Integer age;
    private String password;

    public User() {}

    public User(String name, String email, Integer age, String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }
}
