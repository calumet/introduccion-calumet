package tasks;

import db.DBConnection;
import java.util.ArrayList;
import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TaskController {

    private static final Logger logger = Logger.getLogger(TaskController.class.getName());

    public boolean addTask(String title, String description) {
        String uuid = UUID.randomUUID().toString();
        Task newTask = new Task(uuid, title, description, false);
        return saveTask(newTask);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        DBConnection conn = new DBConnection();
        ResultSet res = conn.query("SELECT * FROM tasks;");
        try {
            while (res.next()) {
                Task t = new Task(
                    res.getString("id"),
                    res.getString("title"),
                    res.getString("description"),
                    res.getBoolean("done")
                );
                tasks.add(t);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        } finally {
            conn.close();
        }
        return tasks;
    }

    public Task getTask(String id) {
        DBConnection conn = new DBConnection();
        ResultSet res = conn.query(
            String.format("SELECT * FROM tasks WHERE id = \"%s\";", id)
        );
        try {
            if (res.next()) {
                return new Task(
                    res.getString("id"),
                    res.getString("title"),
                    res.getString("description"),
                    res.getBoolean("done")
                );
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        } finally {
            conn.close();
        }
        return null;
    }

    public boolean saveTask(Task task) {
        DBConnection conn = new DBConnection();
        String query = String.format(
            "INSERT INTO tasks (id, title, description, done) VALUES (\"%s\", \"%s\", \"%s\", %s);",
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isDone()
        );

        if (conn.setAutoCommit(false)) {
            if (conn.insert(query)) {
                conn.commit();
                conn.close();
                return true;
            } else {
                conn.rollbackBD();
            }
        }
        conn.close();
        return false;
    }

    public boolean deleteTask(String id) {
        DBConnection conn = new DBConnection();
        String query = String.format(
            "DELETE FROM tasks WHERE id = \"%s\";", id
        );

        boolean success = conn.delete(query);
        conn.close();
        return success;
    }

    public boolean markTask(String id) {
        DBConnection conn = new DBConnection();
        String query = String.format(
            "UPDATE tasks SET done = NOT done WHERE id = \"%s\";", id
        );

        boolean success = conn.update(query);
        conn.close();
        return success;
    }
}
