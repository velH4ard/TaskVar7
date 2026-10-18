package ru.university.task7;

import java.util.Scanner;
import ru.university.task7.controller.CmdController;
import ru.university.task7.repository.DiskRepository;
import ru.university.task7.repository.DiskRepositoryInMemoryImpl;
import ru.university.task7.service.DiskService;

public class Main {
    public static void main(String[] args) {
        DiskRepository repo = new DiskRepositoryInMemoryImpl();
        DiskService service = new DiskService(repo);
        CmdController controller = new CmdController(service, new Scanner(System.in));
        controller.run();
    }
}
