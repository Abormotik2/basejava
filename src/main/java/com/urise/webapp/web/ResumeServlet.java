package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();

    }

    private boolean notEmptyValue(String value) {
        return value != null && value.trim().length() != 0;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isEmptyUuid = uuid == null || uuid.length() == 0;
        Resume resume = newResume(uuid, fullName, isEmptyUuid);
        saveUpdate(request, isEmptyUuid, resume);
        response.sendRedirect("resume");
    }

    private Resume newResume(String uuid, String fullName, boolean isEmptyUuid) {
        Resume resume;
        if (isEmptyUuid) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        return resume;
    }

    private void doPostContacts(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (notEmptyValue(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
    }

    private void doPostSections(HttpServletRequest request, Resume resume) {
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (notEmptyValue(value)) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new ContentSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        resume.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                        break;
//                     case EXPERIENCE:
//                      case EDUCATION:
//                          resume.addSection(type, new OrganizationSection(null));
                }
            } else {
                resume.getSections().remove(type);
            }
        }
    }

    private void saveUpdate(HttpServletRequest request, boolean isEmptyUuid, Resume resume) {
        doPostContacts(request, resume);
        doPostSections(request, resume);
        if (isEmptyUuid) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
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
                resume = Resume.getEmptyResume();
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
//                        case EDUCATION:
//                        case EXPERIENCE:
//                            if(section == null){
//                                section = new OrganizationSection();
//                            }
            }
            resume.addSection(type, section);
        }
    }
}