package seedu.duke.items.characteristics;

import seedu.duke.items.mainlists.EventCatalog;
import seedu.duke.items.Task;

import java.util.ArrayList;

public class Member {

    private String name;
    private ArrayList<Task> assignedTasks = new ArrayList<>();
    public static final String MEMBER_DATA_ARGS_DELIMITER = "\\s*\\|\\s*";


    public Member(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        assignedTasks.add(task);
    }

    public void deleteTask(int index) {
        assignedTasks.remove(index);
    }

    public String getName() {
        return name;
    }

    public String getTasks() {
        StringBuilder tasks = new StringBuilder();
        for (Task assignedTask : assignedTasks) {
            tasks.append(assignedTask).append("\n");
        }
        return tasks.toString();
    }

    public void sortTasks() {
        EventCatalog.bubbleSortItems(assignedTasks);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
