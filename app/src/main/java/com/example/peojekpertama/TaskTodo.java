package com.example.peojekpertama;

public class TaskTodo {

    int id;
    String taskTodo;

    public TaskTodo() {
        super();
    }

    public TaskTodo(int id, String taskTodo) {
        this.id = id;
        this.taskTodo = taskTodo;
    }

    public TaskTodo(String taskTodo) {
        this.taskTodo = taskTodo;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTaskTodo(String taskTodo) {
        this.taskTodo = taskTodo;
    }

    public int getId() {
        return id;
    }

    public String getTaskTodo() {
        return taskTodo;
    }
}
