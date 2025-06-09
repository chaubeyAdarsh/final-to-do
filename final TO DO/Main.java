import java.util.*;

/**
 * Main class for To-Do List application.
 * Handles user interaction and uses TaskDAO for persistence.
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final TaskDAO dao = new TaskDAO();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1 -> viewTasks();
                case 2 -> addTask();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> toggleTaskCompletion();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    /**
     * Prints the main menu options.
     */
    private static void printMenu() {
        System.out.println("\n--- To-Do List ---");
        System.out.println("1. View Tasks");
        System.out.println("2. Add Task");
        System.out.println("3. Update Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Mark Task Completed/Incomplete");
        System.out.println("0. Exit");
    }

    /**
     * Safely reads an integer input from the user.
     */
    private static int getIntInput(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(sc.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Displays all tasks with their status.
     */
    private static void viewTasks() {
        List<Task> tasks = dao.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        System.out.println("Tasks:");
        for (Task t : tasks) {
            String status = t.isCompleted() ? "[Completed]" : "[Pending]";
            System.out.printf("ID: %d | Title: %s %s%n", t.getId(), t.getTitle(), status);
        }
    }

    /**
     * Adds a new task with auto-generated unique ID.
     */
    private static void addTask() {
        System.out.print("Enter task title: ");
        String title = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        int id = generateUniqueId();
        dao.addTask(new Task(id, title));
        System.out.println("Task added successfully with ID: " + id);
    }

    /**
     * Generates a unique task ID by finding max current ID and adding 1.
     */
    private static int generateUniqueId() {
        List<Task> tasks = dao.getAllTasks();
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Updates the title of an existing task.
     */
    private static void updateTask() {
        int id = getIntInput("Enter task ID to update: ");
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

    /**
     * Deletes a task by ID.
     */
    private static void deleteTask() {
        int id = getIntInput("Enter task ID to delete: ");
        if (dao.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

    /**
     * Toggles the completion status of a task.
     */
    private static void toggleTaskCompletion() {
        int id = getIntInput("Enter task ID to toggle completion: ");
        List<Task> tasks = dao.getAllTasks();
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setCompleted(!t.isCompleted());
                dao.saveTasks(tasks);
                System.out.println("Task marked as " + (t.isCompleted() ? "completed." : "incomplete."));
                return;
            }
        }
        System.out.println("Task not found.");
    }
}
