package ru.university.task7.repository;

import java.util.ArrayList;
import ru.university.task7.model.Disk;

public interface DiskRepository {
    ArrayList<Disk> getAll();
    Disk getById(int id);
    void create(Disk disk);
    boolean update(Disk disk);
    boolean delete(int id);
}
