package ru.university.task7.repository;

import java.util.ArrayList;
import ru.university.task7.model.Disk;

public class DiskRepositoryInMemoryImpl implements DiskRepository {
    private ArrayList<Disk> disks = new ArrayList<>();

    public ArrayList<Disk> getAll() {
        return new ArrayList<>(disks);
    }

    public Disk getById(int id) {
        for (Disk d : disks) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public void create(Disk disk) {
        disks.add(disk);
    }

    public boolean update(Disk disk) {
        for (int i = 0; i < disks.size(); i++) {
            if (disks.get(i).getId() == disk.getId()) {
                disks.set(i, disk);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        for (int i = 0; i < disks.size(); i++) {
            if (disks.get(i).getId() == id) {
                disks.remove(i);
                return true;
            }
        }
        return false;
    }
}
