package ru.university.task7;

import java.util.ArrayList;

public class Disk {
    int id;
    String name;
    String description;
    int typeId;
    ArrayList<Integer> cats;

    Disk(int id, String name, String description, int typeId, ArrayList<Integer> cats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.typeId = typeId;
        this.cats = new ArrayList<>(cats);
    }
}
