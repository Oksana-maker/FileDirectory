package org.mashurova;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;

//        1. Завдання "Обробка CSV-файлів за допомогою символьних потоків":
//
//        Зчитайте CSV-файл, який містить дані про студентів (ім'я, прізвище,
//        вік, оцінка тощо).
//        Реалізуйте функціонал виведення загальної кількості студентів у файлі.
//        Обчисліть середній вік студентів і виведіть його на екран.
//
//        2. Рекурсивне копіювання директорії. Користувач вказує вихідну папку,
//        і куди зробити копію.
//                Копіювати потрібно всі вкладені папки і файли.
record Student(String firstName, String lastName, String age, String grade) implements Serializable {
}

public class Main  {
    public static void main(String[] args) throws IOException {
        //task1();
        task2();

    }
    private static void task2() throws IOException {
        for ( var f:Files.walk(Path.of("soursTest")).toList()) {
            Path destTest = Path.of("destTest", f.toString());
            Files.createDirectories(destTest);
            Path newPath = destTest;
            Files.copy(f, Path.of(newPath.toString()), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    static void task1() {
        //SaveObjects();
        System.out.println(ReadObjects());
        CountStudents(ReadObjects());

    }

    private static void CountStudents(List<Student> students) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        int count = 0;
        double gpa = 0;
        for (Student s : students) {
            gpa += Double.parseDouble(s.grade());
            count++;
        }
        System.out.println("Amount of student: " + count + "\n" + "GPA: " + decimalFormat.format(gpa/count));
    }

    private static List<Student> ReadObjects() {
        var students = new ArrayList<Student>();
        try (InputStream in = new FileInputStream("students.csv");
             Scanner scanner = new Scanner(in)) {
            while (true) {
                try {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    students.add(new Student(parts[0].trim(), parts[1].trim(), parts[2], parts[3].trim()));
                } catch (NoSuchElementException ex) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    private static void SaveObjects() {
        List<Student> students = getStudent();
        try (OutputStream out = new FileOutputStream("students.csv")) {
            for (Student student : students) {
                String line = student.firstName() + ", " + student.lastName()
                        + ", " + student.age() + ", " + student.grade() + "\n";
                out.write(line.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Student> getStudent() {
        return List.of(
                new Student("Markov", "Denis", "21", "6.0"),
                new Student("Vogova", "Claudia", "19", "5.5"),
                new Student("Pupkina", "Taisia", "20", "4.8")
        );
    }
}
