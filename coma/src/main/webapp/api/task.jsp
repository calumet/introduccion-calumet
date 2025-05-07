<%@page import="com.mycompany.coma.tasks.Task"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.coma.tasks.TaskController"%>
<%@page contentType="application/json;charset=utf-8" language="java" pageEncoding="UTF-8"%>

<%
    List<String> process = Arrays.asList(
            "actualizar",
            "eliminar"
    );

    String bandera = request.getParameter("bandera");

    if (process.contains(bandera)) {
        TaskController tasks = new TaskController();

        if (bandera.equals("actualizar")) {
            String id = request.getParameter("id");

            boolean done = tasks.markTask(id);

            response.setContentType("application/json;charset=utf-8");
            out.print(String.format("{\"status\": %s,\"title\": \"%s\"}", done, tasks.getTask(id).getTitle()));
        } else if (bandera.equals("eliminar")) {
            String id = request.getParameter("id");

            boolean done = tasks.deleteTask(id);

            response.setContentType("application/json;charset=utf-8");
            out.print(String.format("{\"status\": %s}", done));
        }
    } else {
        response.setContentType("application/json;charset=utf-8");
        out.print(String.format("{\"status\": false, \"error\": true}"));
    }
%>