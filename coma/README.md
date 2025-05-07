## Ejemplo de proyecto para COMA

Este es un ejemplo creado para contener las estructuras y el código que más se repiten dentro del proyecto para ejemplificar cómo es trabajar dentro del proyecto principal.

## Retos

1. **La nueva tarea no aparece tras crearla**  

   <details>
   <summary>Solución (diff)</summary>

   ```diff
   diff --git a/web/index.jsp b/web/index.jsp
   @@
   -    if (request.getMethod().equals("POST")) {
   -        String title = request.getParameter("title");
   -        String description = request.getParameter("description");
   -
   -        created = tasks.addTask(title, description);
   -    }
   -
   -    java.util.List<Task> taskList = tasks.getTasks();
   +    java.util.List<Task> taskList = tasks.getTasks(); // Se consulta antes de crear
   +
   +    if (request.getMethod().equals("POST")) {
   +        String title = request.getParameter("title");
   +        String description = request.getParameter("description");
   +
   +        created = tasks.addTask(title, description);
   +    }
   ```

   </details>

2. **Título y descripción intercambiados al listar tareas**  

   <details>
   <summary>Solución (diff)</summary>

   ```diff
   diff --git a/src/java/tasks/TaskController.java b/src/java/tasks/TaskController.java
   @@ public ArrayList<Task> getTasks() {
   -                Task t = new Task(
   -                    res.getString("id"),
   -                    res.getString("title"),
   -                    res.getString("description"),
   -                    res.getBoolean("done")
   -                );
   +                Task t = new Task(
   +                    res.getString("id"),
   +                    res.getString("description"), // TITLE ↔ DESCRIPTION intercambiados
   +                    res.getString("title"),       // TITLE ↔ DESCRIPTION intercambiados
   +                    res.getBoolean("done")
   +                );
   ```

   </details>

3. **La tarea no cambia de estado al marcar/desmarcar**  

   <details>
   <summary>Solución (diff)</summary>

   ```diff
   diff --git a/src/java/tasks/TaskController.java b/src/java/tasks/TaskController.java
   @@ public boolean markTask(String id) {
   -        String query = String.format(
   -            "UPDATE tasks SET done = done WHERE id = \"%s\";", id
   -        );
   +        String query = String.format(
   +            "UPDATE tasks SET done = NOT done WHERE id = \"%s\";", id
   +        );
   ```

   </details>
