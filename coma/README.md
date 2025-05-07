## Retos

1. **La nueva tarea no aparece tras crearla**  
   Tras enviar el formulario, la aplicación recarga pero la tarea recién añadida no se muestra.

   <details>
   <summary>Solución (diff)</summary>

   ```diff
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
   Al recuperar las tareas, el campo `title` y la `description` vienen invertidos.

   <details>
   <summary>Solución (diff)</summary>

   ```diff
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
   El query que actualiza `done` perdió el `NOT`, por lo que el campo nunca se invierte.

   <details>
   <summary>Solución (diff)</summary>

   ```diff
   @@ public boolean markTask(String id) {
   -        String query = String.format(
   -            "UPDATE tasks SET done = done WHERE id = \"%s\";", id
   -        );
   +        String query = String.format(
   +            "UPDATE tasks SET done = NOT done WHERE id = \"%s\";", id
   +        );
   ```

   </details>