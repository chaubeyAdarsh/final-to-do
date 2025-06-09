
/**
 * Represents a single task with an ID, title, and completion status.
 */
public class Task {
    private int id;
    private String title;
    private boolean completed;  // true if task is done, false otherwise

    /**
     * Constructs a new Task with given id and title. Defaults to not completed.
     */
    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    /**
     * Constructs a Task with all fields specified.
     */
    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    /**
     * Returns a string suitable for file storage, including completion status.
     */
    @Override
    public String toString() {
        return id + "," + title + "," + completed;
    }

    /**
     * Parses a line from the file and creates a Task object.
     */
    public static Task fromString(String line) {
        String[] parts = line.split(",", 3);
        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        boolean completed = parts.length > 2 && Boolean.parseBoolean(parts[2]);
        return new Task(id, title, completed);
    }
}
