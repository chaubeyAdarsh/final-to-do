
public class Task {
    private int id;
    private String title;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return id + "," + title;
    }

    public static Task fromString(String line) {
        String[] parts = line.split(",", 2);
        return new Task(Integer.parseInt(parts[0]), parts[1]);
    }
}
