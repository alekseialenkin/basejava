package com.javaops.webapp.web;

import com.javaops.webapp.Config;
import com.javaops.webapp.model.*;
import com.javaops.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            getStartedPage(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> r = storage.get(uuid);
            case "edit" -> {
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()){
                    AbstractSection section = r.getSection(type);
                    switch (type){
                        case PERSONAL, OBJECTIVE -> {
                            if (section == null){
                                r.addSection(type,new TextSection(""));
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            if (section == null){
                                r.addSection(type,new ListSection(""));
                            }
                        }
                        case EDUCATION, EXPERIENCE->{
                            if (section==null){
                                r.addSection(type,new CompanySection(new Company("", "",
                                        new Company.Period(0, Month.JANUARY,
                                                "", ""))));
                            }
                        }
                    }
                }
            }
            case "new" -> {
                r = new Resume("");
                storage.save(r);
            }
            default -> throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName.trim().length() == 0) {
            storage.delete(uuid);
            getStartedPage(request, response);
            return;
        }
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] valuesURL = request.getParameterValues(type.name() + "url");
            String[] valuesName = request.getParameterValues(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> r.addSection(type, new TextSection(value.trim()));
                    case ACHIEVEMENT, QUALIFICATIONS -> r.addSection(type, new ListSection(value.split("\n")));
                    case EDUCATION, EXPERIENCE -> {
                        List<Company> companies = new ArrayList<>();
                        for (int i = 0; i < valuesName.length; i++) {
                            String[] valuesBegin = request.getParameterValues(type.name() + i + "begin");
                            String[] valuesEnd = request.getParameterValues(type.name() + i + "end");
                            String[] valuesTitle = request.getParameterValues(type.name() + i + "title");
                            String[] valuesDescription = request.getParameterValues(type.name() + i + "description");
                            List<Link> links = new ArrayList<>();
                            for (int j = 0; j < valuesURL.length; j++) {
                                links.add(new Link(valuesName[j].trim(), valuesURL[j].trim()));
                            }
                            List<Company.Period> periods = new ArrayList<>();
                            if (valuesTitle != null) {
                                for (int j = 0; j < valuesTitle.length; j++) {
                                    periods.add(new Company.Period(valuesTitle[j].trim(), valuesDescription[j].trim()));
                                }
                            }
                            companies.add(new Company(links.get(i), periods));
                        }
                        r.addSection(type, new CompanySection(companies));
                    }
                }
            } else {
                r.getSections().remove(type);
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    private void getStartedPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }
}
