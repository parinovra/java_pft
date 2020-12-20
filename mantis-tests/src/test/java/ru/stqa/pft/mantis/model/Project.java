package ru.stqa.pft.mantis.model;

public class Project {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    //было
//    public void setId(int id) {
//        this.id = id;
//    }
    //стало - для вытягивания в цепочки fluent interface
    public Project withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project withName(String name) {
        this.name = name;
        return this;
    }
}
