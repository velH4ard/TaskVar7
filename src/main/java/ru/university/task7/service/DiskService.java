package ru.university.task7.service;

import java.util.ArrayList;
import ru.university.task7.model.Category;
import ru.university.task7.model.Disk;
import ru.university.task7.model.DiskType;
import ru.university.task7.repository.DiskRepository;

public class DiskService {
    private DiskRepository repo;
    private ArrayList<DiskType> types = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private int nextDisk = 1;
    private int nextType = 1;
    private int nextCategory = 1;

    public DiskService(DiskRepository repo) {
        this.repo = repo;
        addType("DVD");
        addType("CD-R");
        addType("mini-disc");
        addCategory("фильмы");
        addCategory("музыка");
        addCategory("ПО");
    }

    public ArrayList<Disk> getAll() {
        return repo.getAll();
    }

    public ArrayList<DiskType> getTypes() {
        return new ArrayList<>(types);
    }

    public ArrayList<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public int addType(String name) {
        if (name == null || name.trim().isEmpty()) {
            return -1;
        }
        name = name.trim();
        for (DiskType t : types) {
            if (t.getName().equalsIgnoreCase(name)) {
                return -1;
            }
        }
        types.add(new DiskType(nextType, name));
        return nextType++;
    }

    public boolean renameType(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        name = name.trim();
        for (DiskType t : types) {
            if (t.getId() != id && t.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        DiskType t = getType(id);
        if (t == null) {
            return false;
        }
        t.setName(name);
        return true;
    }

    public boolean deleteType(int id) {
        for (Disk d : repo.getAll()) {
            if (d.getTypeId() == id) {
                return false;
            }
        }
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getId() == id) {
                types.remove(i);
                return true;
            }
        }
        return false;
    }

    public DiskType getType(int id) {
        for (DiskType t : types) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public int addCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            return -1;
        }
        name = name.trim();
        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(name)) {
                return -1;
            }
        }
        categories.add(new Category(nextCategory, name));
        return nextCategory++;
    }

    public boolean renameCategory(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        name = name.trim();
        for (Category c : categories) {
            if (c.getId() != id && c.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        Category c = getCategory(id);
        if (c == null) {
            return false;
        }
        c.setName(name);
        return true;
    }

    public boolean deleteCategory(int id) {
        for (Disk d : repo.getAll()) {
            if (d.getCats().contains(id)) {
                return false;
            }
        }
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public Category getCategory(int id) {
        for (Category c : categories) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public int addDisk(String name, String description, int typeId, ArrayList<Integer> cats) {
        if (!validDisk(name, description, typeId, cats)) {
            return -1;
        }
        repo.create(new Disk(nextDisk, name.trim(), description.trim(), typeId, cats));
        return nextDisk++;
    }

    public boolean editDisk(int id, String name, String description, int typeId, ArrayList<Integer> cats) {
        if (repo.getById(id) == null || !validDisk(name, description, typeId, cats)) {
            return false;
        }
        return repo.update(new Disk(id, name.trim(), description.trim(), typeId, cats));
    }

    public boolean deleteDisk(int id) {
        return repo.delete(id);
    }

    public Disk getDisk(int id) {
        return repo.getById(id);
    }

    public ArrayList<Disk> findByDescription(String text) {
        ArrayList<Disk> found = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return found;
        }
        text = text.trim().toLowerCase();
        for (Disk d : repo.getAll()) {
            if (d.getDescription().toLowerCase().contains(text)) {
                found.add(d);
            }
        }
        return found;
    }

    private boolean validDisk(String name, String description, int typeId, ArrayList<Integer> cats) {
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
