package ru.university.task7;

import java.util.ArrayList;

public class DiskCollection {
    ArrayList<Disk> disks = new ArrayList<>();
    ArrayList<DiskType> types = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    int nextType = 1;
    int nextCategory = 1;

    DiskCollection() {
        addType("DVD");
        addType("CD-R");
        addType("mini-disc");
        addCategory("фильмы");
        addCategory("музыка");
        addCategory("ПО");
    }

    int addType(String name) {
        if (name == null || name.trim().isEmpty()) {
            return -1;
        }
        name = name.trim();
        for (DiskType t : types) {
            if (t.name.equalsIgnoreCase(name)) {
                return -1;
            }
        }
        types.add(new DiskType(nextType, name));
        return nextType++;
    }

    boolean renameType(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        name = name.trim();
        for (DiskType t : types) {
            if (t.id != id && t.name.equalsIgnoreCase(name)) {
                return false;
            }
        }
        DiskType t = getType(id);
        if (t == null) {
            return false;
        }
        t.name = name;
        return true;
    }

    boolean deleteType(int id) {
        for (Disk d : disks) {
            if (d.typeId == id) {
                return false;
            }
        }
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).id == id) {
                types.remove(i);
                return true;
            }
        }
        return false;
    }

    DiskType getType(int id) {
        for (DiskType t : types) {
            if (t.id == id) {
                return t;
            }
        }
        return null;
    }

    int addCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            return -1;
        }
        name = name.trim();
        for (Category c : categories) {
            if (c.name.equalsIgnoreCase(name)) {
                return -1;
            }
        }
        categories.add(new Category(nextCategory, name));
        return nextCategory++;
    }

    boolean renameCategory(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        name = name.trim();
        for (Category c : categories) {
            if (c.id != id && c.name.equalsIgnoreCase(name)) {
                return false;
            }
        }
        Category c = getCategory(id);
        if (c == null) {
            return false;
        }
        c.name = name;
        return true;
    }

    boolean deleteCategory(int id) {
        for (Disk d : disks) {
            if (d.cats.contains(id)) {
                return false;
            }
        }
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).id == id) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    Category getCategory(int id) {
        for (Category c : categories) {
            if (c.id == id) {
                return c;
            }
        }
        return null;
    }
}
