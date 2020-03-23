package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isEmptyUuid = uuid == null || uuid.length() == 0;
        Resume resume;
        if (isEmptyUuid) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        doPostContacts(request, resume);
        doPostSections(request, resume);
        if (isEmptyUuid) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "create":
                resume = Resume.EMPTY_RESUME;
                storage.save(resume);
                action = "edit";
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                editSections(resume);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private boolean isEmptyValue(String value) {
        return value == null || value.trim().length() == 0;
    }

    private void doPostContacts(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isEmptyValue(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.addContact(type, value);
            }
        }
    }

    private void doPostSections(HttpServletRequest request, Resume resume) {
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (isEmptyValue(value)) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new ContentSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        resume.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        String orgName = request.getParameter("name");
                        String orgUrl = request.getParameter("url");
                        ArrayList<Organization.Stages> stages = new ArrayList<>();
                        String[] titles = request.getParameterValues("title");
                        String[] startDate = request.getParameterValues("startDate");
                        String[] endDate = request.getParameterValues("endDate");
                        String[] responsibility = request.getParameterValues("responsibility");
                        if (titles != null) {
                            for (int i = 0; i < titles.length; i++) {
                                if (titles[i] != null && titles[i].length() != 0) {
                                    stages.add(new Organization.Stages(LocalDate.parse(startDate[i]), LocalDate.parse(endDate[i]), titles[i], responsibility[i]));
                                }
                            }
                            organizations.add(new Organization(new OrganizationLink(orgName, orgUrl), stages));
                        }
                        resume.addSection(type, new OrganizationSection(organizations));
                }
            }
        }
    }

    private void editSections(Resume resume) {
        for (SectionType type : SectionType.values()) {
            Section section = resume.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = new ContentSection();
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    if (section == null) {
                        section = new ListSection();
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    OrganizationSection orgSection = (OrganizationSection) section;
                    List<Organization> organizations = new ArrayList<>();
                    if (orgSection != null) {
                        for (Organization org : orgSection.getOrganizations()) {
                            List<Organization.Stages> emptyFirstPositions = new ArrayList<>(org.getStages());
                            organizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                        }
                    }
                    section = new OrganizationSection(organizations);
                    break;
            }
            resume.addSection(type, section);
        }
    }
}