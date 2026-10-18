package ru.university.task7;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.university.task7.service.DiskService;
import ru.university.task7.repository.DiskRepositoryInMemoryImpl;

import static org.junit.jupiter.api.Assertions.*;

class DiskServiceTest {
    private DiskService service = new DiskService(new DiskRepositoryInMemoryImpl());

    private ArrayList<Integer> cats(Integer... ids) {
        return new ArrayList<>(Arrays.asList(ids));
    }

    @Test
    void addEditDeleteDisk() {
        int id = service.addDisk("  мой диск  ", "  старое описание  ", 1, cats(1, 2));
        assertEquals(1, id);
        assertEquals("мой диск", service.getDisk(id).getName());
        assertEquals("старое описание", service.getDisk(id).getDescription());
        assertEquals(cats(1, 2), service.getDisk(id).getCats());

        assertTrue(service.editDisk(id, "другой диск", "новое описание", 2, cats(3)));
        assertEquals("другой диск", service.getDisk(id).getName());
        assertEquals(2, service.getDisk(id).getTypeId());
        assertEquals(cats(3), service.getDisk(id).getCats());
        assertEquals(1, service.getAll().size());

        assertTrue(service.deleteDisk(id));
        assertNull(service.getDisk(id));
        assertFalse(service.deleteDisk(id));
    }

    @Test
    void checkWrongData() {
        assertEquals(-1, service.addDisk(" ", "описание", 1, cats(1)));
        assertEquals(-1, service.addDisk("диск", " ", 1, cats(1)));
        assertEquals(-1, service.addDisk("диск", "описание", 99, cats(1)));
        assertEquals(-1, service.addDisk("диск", "описание", 1, cats()));
        assertEquals(-1, service.addDisk("диск", "описание", 1, cats(1, 1)));
        assertEquals(-1, service.addDisk("диск", "описание", 1, cats(99)));
        assertEquals(1, service.addDisk("диск", "описание", 1, cats(1)));
        assertFalse(service.editDisk(1, "диск", "описание", 1, cats(99)));
        assertEquals("описание", service.getDisk(1).getDescription());
    }

    @Test
    void typeAndCategory() {
        int t = service.addType("Blu-ray");
        int c = service.addCategory("игры");
        assertEquals(-1, service.addType("blu-RAY"));
        assertEquals(-1, service.addCategory("ИГРЫ"));
        assertTrue(service.renameType(t, "новый тип"));
        assertTrue(service.renameCategory(c, "новая категория"));
        assertEquals("новый тип", service.getType(t).getName());
        assertEquals("новая категория", service.getCategory(c).getName());

        int id = service.addDisk("диск", "описание", t, cats(c));
        assertFalse(service.deleteType(t));
        assertFalse(service.deleteCategory(c));
        assertTrue(service.deleteDisk(id));
        assertTrue(service.deleteType(t));
        assertTrue(service.deleteCategory(c));
        assertNull(service.getType(t));
        assertNull(service.getCategory(c));
    }

    @Test
    void searchDescription() {
        service.addDisk("диск 1", "Домашнее видео", 1, cats(1));
        service.addDisk("диск 2", "Музыка", 2, cats(2));
        service.addDisk("диск 3", "Видео из отпуска", 3, cats(1));
        assertEquals(2, service.findByDescription("ВИДЕО").size());
        assertEquals(0, service.findByDescription("не найдено").size());
        assertEquals(0, service.findByDescription(" ").size());
    }
}
