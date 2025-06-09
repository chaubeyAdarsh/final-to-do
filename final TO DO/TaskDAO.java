import java.io.*;
import java.util.*;

/**
 * Data Access Object for Task objects.
 * Handles file-based storage and retrieval of tasks.
 */
public class TaskDAO {
    private final File file = new File("tasks.txt");

    /**
     * Constructor initializes the file if it doesn't exist.
     */
    public TaskDAO() {
        try {
            if (!file.exists()) {
                file.createNewFile(); // Auto-create file
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Reads all tasks from the file.
     * @return List of all tasks.
     */
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves all tasks to the file, overwriting existing content.
     * @param tasks List of tasks to save.
     */
    public void saveTasks(List<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task t : tasks) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Adds a new task to the storage.
     * @param task Task to add.
     */
    public void addTask(Task task) {
        List<Task> tasks = getAllTasks();
        tasks.add(task);
        saveTasks(tasks);
    }

    /**
     * Updates the title of a task with the given ID.
     * @param id Task ID.
     * @param newTitle New title.
     * @return true if update was successful, false if task not found.
     */
    public boolean updateTask(int id, String newTitle) {
        List<Task> tasks = getAllTasks();
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setTitle(newTitle);
                saveTasks(tasks);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the task with the given ID.
     * @param id Task ID.
     * @return true if deletion was successful, false if task not found.
     */
    public boolean deleteTask(int id) {
        List<Task> tasks = getAllTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) saveTasks(tasks);
        return removed;
    }
}

