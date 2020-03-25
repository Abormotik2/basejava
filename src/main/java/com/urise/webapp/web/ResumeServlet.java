package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.util.DateUtil.dataParser;

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

    public static boolean isEmptyValue(String value) {
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
            String[] values = request.getParameterValues(type.name());
            if (isEmptyValue(value) && (values == null || values.length < 2)) {
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
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!isEmptyValue(name)) {
                                List<Organization.Stages> stages = new ArrayList<>();
                                String count = type.name() + i;
                                String[] startDates = request.getParameterValues(count + "startDate");
                                String[] endDates = request.getParameterValues(count + "endDate");
                                String[] titles = request.getParameterValues(count + "title");
                                String[] responsibility = request.getParameterValues(count + "responsibility");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!isEmptyValue(titles[j])) {
                                        stages.add(
                                                new Organization.Stages(
                                                        dataParser(startDates[j]),
                                                        dataParser(endDates[j]),
                                                        titles[j],
                                                        responsibility[j]));
                                    }
                                }
                                organizations.add(new Organization(name, urls[i], stages));
                            }
                        }
                        resume.addSection(type, new OrganizationSection(organizations));
                        break;
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
                    if (section == null) section = new ContentSection();
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    if (section == null) section = new ListSection();
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    OrganizationSection organizationSection = (OrganizationSection) section;
                    List<Organization> emptyOrganizations = new ArrayList<>();
                   // emptyOrganizations.add(new Organization());
                    if (section != null) {
                        for (Organization organization : organizationSection.getOrganizations()) {
                            List<Organization.Stages> stages = organization.getStages();
                            List<Organization.Stages> emptyStages = new ArrayList<>();
                            emptyStages.add(new Organization.Stages());
                            emptyStages.addAll(stages);
                            organization.setStages(emptyStages);
                            emptyOrganizations.add(organization);
                        }
                        resume.addSection(type, new OrganizationSection(emptyOrganizations));
                    }
                    break;
            }
        }
    }
}