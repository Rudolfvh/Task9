package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
@WebServlet("/count")
public class AppServlet extends HttpServlet {
    private Count count = new Count();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pullData();
        count.setCount(count.getCount() + 1);
        pushData();
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<h3> number of entries: " + count.getCount() + "</h3>");
    }

    public void pushData(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("E:\\Count.json"), count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pullData(){
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get("E:\\Count.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            count = objectMapper.readValue(jsonData, Count.class);
        } catch (IOException e) {
            pushData();
        }
    }
}
