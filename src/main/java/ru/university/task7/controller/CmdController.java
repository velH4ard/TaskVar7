package ru.university.task7.controller;

import java.util.ArrayList;
import java.util.Scanner;
import ru.university.task7.model.Category;
import ru.university.task7.model.Disk;
import ru.university.task7.model.DiskType;
import ru.university.task7.service.DiskService;

public class CmdController {
    private DiskService service;
    private Scanner scanner;

    public CmdController(DiskService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n1 - все диски");
            System.out.println("2 - найти диск по номеру");
            System.out.println("3 - добавить диск");
            System.out.println("4 - изменить диск");
            System.out.println("5 - удалить диск");
            System.out.println("6 - поиск по описанию");
            System.out.println("7 - типы носителей");
            System.out.println("8 - категории");
            System.out.println("0 - выход");
            if (!scanner.hasNextLine()) {
                return;
            }
            String n = readLine("выбор: ");
            if (n.equals("1")) {
                printDisks(service.getAll());
            } else if (n.equals("2")) {
                Disk d = service.getDisk(readNum("номер диска: "));
                if (d == null) {
                    System.out.println("диск не найден");
                } else {
                    printDisk(d);
                }
            } else if (n.equals("3")) {
                addDisk();
            } else if (n.equals("4")) {
                editDisk();
            } else if (n.equals("5")) {
                int id = readNum("номер диска: ");
                System.out.println(service.deleteDisk(id) ? "удалено" : "диск не найден");
            } else if (n.equals("6")) {
                String text = readLine("слово в описании: ");
                printDisks(service.findByDescription(text));
            } else if (n.equals("7")) {
                dictionaryMenu(true);
            } else if (n.equals("8")) {
                dictionaryMenu(false);
            } else if (n.equals("0")) {
                return;
            } else {
                System.out.println("нет такого пункта");
            }
        }
    }

    private void addDisk() {
        showTypes();
        showCategories();
        String name = readLine("название: ");
        String description = readLine("описание: ");
        int type = readNum("номер типа: ");
        ArrayList<Integer> cats = readCats();
        int id = service.addDisk(name, description, type, cats);
        System.out.println(id == -1 ? "не удалось добавить диск" : "добавлен диск " + id);
    }

    private void editDisk() {
        int id = readNum("номер диска: ");
        if (service.getDisk(id) == null) {
            System.out.println("диск не найден");
            return;
        }
        showTypes();
        showCategories();
        String name = readLine("новое название: ");
        String description = readLine("новое описание: ");
        int type = readNum("номер типа: ");
        ArrayList<Integer> cats = readCats();
        System.out.println(service.editDisk(id, name, description, type, cats) ? "изменено" : "не удалось изменить диск");
    }

    private ArrayList<Integer> readCats() {
        ArrayList<Integer> ids = new ArrayList<>();
        String text = readLine("номера категорий через запятую: ");
        if (text.trim().isEmpty()) {
            return ids;
        }
        for (String n : text.split(",")) {
            try {
                ids.add(Integer.parseInt(n.trim()));
            } catch (NumberFormatException e) {
                return new ArrayList<>();
            }
        }
        return ids;
    }

    private void printDisks(ArrayList<Disk> disks) {
        if (disks.isEmpty()) {
            System.out.println("дисков нет");
        }
        for (Disk d : disks) {
            printDisk(d);
        }
    }

    private void printDisk(Disk d) {
        DiskType type = service.getType(d.getTypeId());
        String cats = "";
        for (Integer id : d.getCats()) {
            Category c = service.getCategory(id);
            if (c != null) {
                if (!cats.isEmpty()) {
                    cats += ", ";
                }
                cats += c.getName();
            }
        }
        System.out.println(d.getId() + ". " + d.getName() + " | " +
                (type == null ? "?" : type.getName()) + " | " + cats + " | " + d.getDescription());
    }

    private void dictionaryMenu(boolean types) {
        while (true) {
            System.out.println(types ? "\nтипы носителей" : "\nкатегории");
            System.out.println("1 - показать");
            System.out.println("2 - добавить");
            System.out.println("3 - переименовать");
            System.out.println("4 - удалить");
            System.out.println("0 - назад");
            String n = readLine("выбор: ");
            if (n.equals("0")) {
                return;
            } else if (n.equals("1")) {
                if (types) {
                    showTypes();
                } else {
                    showCategories();
                }
            } else if (n.equals("2")) {
                String name = readLine("название: ");
                int id = types ? service.addType(name) : service.addCategory(name);
                System.out.println(id == -1 ? "не удалось добавить" : "добавлено, номер " + id);
            } else if (n.equals("3")) {
                int id = readNum("номер: ");
                String name = readLine("новое название: ");
                boolean ok = types ? service.renameType(id, name) : service.renameCategory(id, name);
                System.out.println(ok ? "изменено" : "не удалось изменить");
            } else if (n.equals("4")) {
                int id = readNum("номер: ");
                boolean ok = types ? service.deleteType(id) : service.deleteCategory(id);
                System.out.println(ok ? "удалено" : "не удалось удалить: номер не найден или используется");
            } else {
                System.out.println("нет такого пункта");
            }
        }
    }

    private void showTypes() {
        System.out.println("типы:");
        for (DiskType t : service.getTypes()) {
            System.out.println(t.getId() + " - " + t.getName());
        }
    }

    private void showCategories() {
        System.out.println("категории:");
        for (Category c : service.getCategories()) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }

    private int readNum(String text) {
        try {
            return Integer.parseInt(readLine(text).trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String readLine(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }
}
