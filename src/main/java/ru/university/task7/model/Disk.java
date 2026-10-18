package ru.university.task7.model;

import java.util.ArrayList;

public class Disk {
    private int id;
    private String name;
    private String description;
    private int typeId;
    private ArrayList<Integer> cats;

    public Disk(int id, String name, String description, int typeId, ArrayList<Integer> cats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.typeId = typeId;
        this.cats = new ArrayList<>(cats);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTypeId() {
        return typeId;
    }

    public ArrayList<Integer> getCats() {
        return new ArrayList<>(cats);
    }
}
