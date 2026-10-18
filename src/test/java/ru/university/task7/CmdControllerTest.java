package ru.university.task7;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import ru.university.task7.controller.CmdController;
import ru.university.task7.repository.DiskRepositoryInMemoryImpl;
import ru.university.task7.service.DiskService;

import static org.junit.jupiter.api.Assertions.*;

class CmdControllerTest {
    @Test
    void menuWorks() throws Exception {
        String input = "3\nпервый диск\nфильм на вечер\n1\n1,2\n1\n4\n1\nвторой диск\nфильм на выходные\n2\n1\n6\nвыходные\n5\n1\n1\n0\n";
        DiskService service = new DiskService(new DiskRepositoryInMemoryImpl());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream old = System.out;
        try {
            System.setOut(new PrintStream(bytes, true, "UTF-8"));
            new CmdController(service, new Scanner(input)).run();
        } finally {
            System.setOut(old);
        }

        String result = bytes.toString(StandardCharsets.UTF_8.name());
        assertTrue(result.contains("добавлен диск 1"));
        assertTrue(result.contains("изменено"));
        assertTrue(result.contains("второй диск"));
        assertTrue(result.contains("фильм на выходные"));
        assertTrue(result.contains("удалено"));
        assertTrue(result.contains("дисков нет"));
        assertTrue(service.getAll().isEmpty());
    }
}
