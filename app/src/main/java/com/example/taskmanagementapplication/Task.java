package com.example.taskmanagementapplication;

public class Task {
    private String id;
    private int project_id;
    private String title;
    private String assigned_to;
    private String dueDate;
    private String priority;
    private String status;

    // Constructor
    public Task(String id, String title,String assigned_to, String dueDate,String priority, String status) {
        this.id = id;
        this.title = title;
        this.assigned_to=assigned_to;
        this.priority=priority;
        this.dueDate = dueDate;
        this.status = status;
    }
    public Task(String id, String title, String dueDate, String status) {
        this.id = id;
        this.title = title;
        this.assigned_to=assigned_to;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAssigned_to(){ return assigned_to;}
    public String getDueDate() {
        return dueDate;
    }
    public String getPriority(){ return priority; }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setPriority(String priority){this.priority=priority;}
    public void setStatus(String status) {
        this.status = status;
    }
}
