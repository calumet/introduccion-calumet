<%@page import="tasks.TaskController"%>
<%@page import="tasks.Task"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    boolean created = true;
    TaskController tasks = new TaskController();
    
    if (request.getMethod().equals("POST")) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        created = tasks.addTask(title, description);
    }

    java.util.List<Task> taskList = tasks.getTasks();
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>To Do</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="./styles/main.css" rel="stylesheet">
    </head>
    <body>
        <article class="row m-3">
            <!-- Formulario para crear tarea -->
            <form method="POST" class="col col-4">
                <h2>Tareas</h2>
                <input type="hidden" name="bandera" value="crear">
                <div class="input-group mb-2">
                    <input type="text" name="title" title="title" class="form-control" placeholder="Nueva tarea..." required>
                    <button class="btn btn-primary" type="submit">+</button>
                </div>
                <label class="form-label">Descripción:</label>
                <div class="mb-3">
                    <textarea name="description" title="description" class="form-control" rows="3"></textarea>
                </div>
            </form>

            <!-- Lista de tareas -->
            <article class="col col-8">
                <ul class="gap-2 p-0 list-unstyled">
                    <% for (Task task : taskList) {%>
                    <li class="col mb-3">
                        <div class="w-100 card <%= task.isDone() ? "task-success" : ""%>">
                            <div class="card-body">
                                <h5 class="card-title col"><%= task.getTitle()%></h5>
                                <p class="card-text"><%= task.getDescription()%></p>
                                <div class="row px-2 gap-2">
                                    <button data-id="<%= task.getId()%>" class="btn btn-danger btn-sm col col-2 delete-btn">🗑️</button>
                                    <% if (task.isDone()) {%>
                                        <button data-id="<%= task.getId()%>" class="btn btn-danger btn-sm col col-2 mark-btn">❌</button>
                                    <% } else { %>
                                        <button data-id="<%= task.getId()%>" class="btn btn-success btn-sm col col-2 mark-btn">✅</button>
                                    <%}%>
                                </div>
                            </div>
                        </div>
                    </li>
                    <% }%>
                </ul>
            </article>
        </article>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const taskCreated = <%=created%>;
                if (!taskCreated) {
                    alert("Hubo un problema creando la tarea");
                }
                
                function sendAction(bandera, id) {
                    const params = new URLSearchParams({
                        bandera,
                        id
                    });

                    fetch('api/task.jsp?' + params)
                            .then(async (res) => {
                                const data = await res.json();
                                if (data.status) {
                                    location.reload();
                                } else {
                                    alert("Hubo un problema!");
                                }
                            })
                            .catch(err => console.error('Error:', err));
                }

                document.querySelectorAll('.delete-btn').forEach(btn => {
                    btn.addEventListener('click', () => {
                        const id = btn.dataset.id;
                        sendAction('eliminar', id);
                    });
                });

                document.querySelectorAll('.mark-btn').forEach(btn => {
                    btn.addEventListener('click', () => {
                        const id = btn.dataset.id;
                        sendAction('actualizar', id);
                    });
                });
            });
        </script>
    </body>
</html>
