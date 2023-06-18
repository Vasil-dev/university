package ua.foxminded.university;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Scanner;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final GroupServiceImpl groupService;
    private final StudentServiceImpl studentService;
    public ApplicationRunner(GroupServiceImpl groupService, StudentServiceImpl studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @Override
    public void run(String... args)  {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    getAll(scanner);
                }

                case 0 -> exit = true;
                default -> System.err.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        System.out.println("Exiting the application...");
    }

    private void displayMenu() {
        System.out.println("===== Console Menu =====");
        System.out.println("1. Get all...");
        System.out.print("Enter your choice: ");
    }
    private void getAll(Scanner scanner) {
        System.out.println("Get all from:");
        System.out.println(" 1: Groups \n 2: Students \n 3: Courses");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                List<Group> groups = groupService.getAll();
                for (Group group : groups) {
                    System.out.println(group);
                }
            }
            case 2 -> {
                List<Student> students = studentService.getAll();
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        }
    }}
