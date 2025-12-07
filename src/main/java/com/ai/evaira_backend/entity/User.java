package com.ai.evaira_backend.entity;


import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private Integer age;
    private String gender;
    private String location;
    private String bodyType;
    private String faceShape;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPreference> preferences = new ArrayList<>();

    // getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(String faceShape) {
        this.faceShape = faceShape;
    }

    public List<UserPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<UserPreference> preferences) {
        this.preferences = preferences;
    }
}


