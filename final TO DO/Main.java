


import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final TaskDAO dao = new TaskDAO();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- To-Do List ---");
            System.out.println("1. View Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // Clear newline

            switch (choice) {
                case 1 -> viewTasks();
                case 2 -> addTask();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    private static void viewTasks() {
        List<Task> tasks = dao.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        System.out.println("Tasks:");
        for (Task t : tasks) {
            System.out.println("ID: " + t.getId() + " | Title: " + t.getTitle());
        }
    }

    private static void addTask() {
        System.out.print("Enter task ID (number): ");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid ID. Enter a number: ");
            sc.next();
        }
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter task title: ");
        String title = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        dao.addTask(new Task(id, title));
        System.out.println("Task added successfully.");
    }

    private static void updateTask() {
        System.out.print("Enter task ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new title: ");
        String title = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        if (dao.updateTask(id, title)) {
            System.out.println("Task updated.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteTask() {
        System.out.print("Enter task ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (dao.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }
}
