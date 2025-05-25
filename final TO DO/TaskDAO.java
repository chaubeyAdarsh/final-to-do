


import java.io.*;
import java.util.*;

public class TaskDAO {
    private final File file = new File("tasks.txt");

    public TaskDAO() {
        try {
            if (!file.exists()) {
                file.createNewFile(); // Auto-create file
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

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

    public void addTask(Task task) {
        List<Task> tasks = getAllTasks();
        tasks.add(task);
        saveTasks(tasks);
    }

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

    public boolean deleteTask(int id) {
        List<Task> tasks = getAllTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) saveTasks(tasks);
        return removed;
    }
}
