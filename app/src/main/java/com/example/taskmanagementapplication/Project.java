package com.example.taskmanagementapplication;

public class Project {
    private int id;
    private String name;
    private String description;
    private String admin;

    // Constructor
    public Project(int id,String name , String description , String admin){
        this.id=id;
        this.name=name;
        this.description=description;
        this.admin=admin;
    }
    // Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAdmin() {
        return admin;
    }

}
