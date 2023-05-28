package com.javaops.webapp.web;

import com.javaops.webapp.Config;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;
    public void init(){
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        List<Resume> list = storage.getAllSorted();
        response.getWriter().write("""
                <style>
                    table, th, td {
                        border:1px solid black;
                    }
                </style>
                <body>

                <h2>Resume table</h2>

                <table style="width:20%">
                    <col style="width: 30%">
                    <col style="width: 70%">
                    <tr>
                        <th>uuid</th>
                        <th>fullName</th>
                    </tr>""");
        for(Resume r :list){
            response.getWriter().write("<tr>\n" +
                    "        <td>" + r.getUuid() + "</td>\n" +
                    "        <td>" + r.getFullName() + "</td>\n" +
                    "    </tr>\n");
        }
        response.getWriter().write("""
                </table>
                </body>
                </html>""");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
