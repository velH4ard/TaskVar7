package ru.university.task7;

import java.util.ArrayList;

public class DiskCollection {
    ArrayList<Disk> disks = new ArrayList<>();
    ArrayList<DiskType> types = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    int nextDisk = 1;
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

    int addDisk(String name, String description, int typeId, ArrayList<Integer> cats) {
        if (!validDisk(name, description, typeId, cats)) {
            return -1;
        }
        disks.add(new Disk(nextDisk, name.trim(), description.trim(), typeId, cats));
        return nextDisk++;
    }

    boolean editDisk(int id, String name, String description, int typeId, ArrayList<Integer> cats) {
        Disk d = getDisk(id);
        if (d == null || !validDisk(name, description, typeId, cats)) {
            return false;
        }
        d.name = name.trim();
        d.description = description.trim();
        d.typeId = typeId;
        d.cats = new ArrayList<>(cats);
        return true;
    }

    boolean deleteDisk(int id) {
        for (int i = 0; i < disks.size(); i++) {
            if (disks.get(i).id == id) {
                disks.remove(i);
                return true;
            }
        }
        return false;
    }

    Disk getDisk(int id) {
        for (Disk d : disks) {
            if (d.id == id) {
                return d;
            }
        }
        return null;
    }

    boolean validDisk(String name, String description, int typeId, ArrayList<Integer> cats) {
        if (name == null || name.trim().isEmpty() || description == null ||
                description.trim().isEmpty() || getType(typeId) == null ||
                cats == null || cats.isEmpty()) {
            return false;
        }
        ArrayList<Integer> used = new ArrayList<>();
        for (Integer c : cats) {
            if (c == null || getCategory(c) == null || used.contains(c)) {
                return false;
            }
            used.add(c);
        }
        return true;
    }
}
