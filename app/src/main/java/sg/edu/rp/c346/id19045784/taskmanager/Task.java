package sg.edu.rp.c346.id19045784.taskmanager;

import java.io.Serializable;

public class Task implements Serializable {
    String name;
    String description;
    int id;

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  id + " " + name + "\n" + description;
    }
}
