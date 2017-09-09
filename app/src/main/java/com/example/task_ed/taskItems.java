package com.example.task_ed;

public class taskItems {
    /* class fields */
    private int __id;
    private String __task_name;
    private String __task_time;

    /* constructor */
    public taskItems(String task_name, String task_time){
        this.__task_name = task_name;
        this.__task_time = task_time;
    }

    /*methods to set task fields*/
    public void set__task_name(String __task_name) {
        this.__task_name = __task_name;
    }

    public void set__task_time(String __task_time) {
        this.__task_time = __task_time;
    }

    public void set__id(int __id) {
        this.__id = __id;
    }

    /* methods to get values of task fields*/
    public String get__task_name() {
        return __task_name;
    }

    public String get__task_time() {
        return __task_time;
    }

    public int get__id() {
        return __id;
    }

}
