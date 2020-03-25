package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
            if (!isExistParam(request, type)) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new ContentSection(request.getParameter(type.name())));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        resume.addSection(type, new ListSection(Arrays.asList(request.getParameter(type.name()).split("\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Map<String, String[]>> convertedData = convertArrayDataToList(request, type);
                        List<Organization> organizations = new ArrayList<>(convertedData.size());

                        for (Map<String, String[]> data : convertedData) {
                            Organization organization = new Organization(data.get("name")[0], data.get("url")[0], new ArrayList<>());
                            String[] stages = data.getOrDefault("stages", new String[0]);
                            for (int i = 0; i < stages.length; ) {
                                String startDates = stages[i++];
                                String endDates = stages[i++];
                                String titles = stages[i++];
                                String responsibility = stages[i++];
                                organization.getStages().add(new Organization.Stages(
                                        dataParser(startDates),
                                        dataParser(endDates),
                                        titles,
                                        responsibility));
                            }
                            organizations.add(organization);
                        }
                        resume.addSection(type, new OrganizationSection(organizations));
                        break;
                }
            }
        }
    }

    private List<Map<String, String[]>> convertArrayDataToList(HttpServletRequest request, SectionType type) {

        Map<String, String[]> dataByType = request.getParameterMap()
                .entrySet()
                .stream()
                .filter(stringEntry -> stringEntry.getKey().startsWith(type.name()))
                .collect(Collectors.toMap(stringEntry -> stringEntry.getKey().substring(stringEntry.getKey().indexOf('-') + 1),
                        Map.Entry::getValue));
        Map<Integer, Map<String, String[]>> dataBuffer = new TreeMap<>();

        for (Map.Entry<String, String[]> entry : dataByType.entrySet()) {
            String[] keys = entry.getKey().split("-");
            int count = Integer.parseInt(keys[0]);
            Map<String, String[]> innerData = dataBuffer.computeIfAbsent(count, k -> new HashMap<>());
            innerData.put(keys[1], entry.getValue());
        }

        List<Map<String, String[]>> data = new ArrayList<>();
        for (Integer key : dataBuffer.keySet()) {
            data.add(dataBuffer.get(key));
        }

        return data;
    }

    private boolean isExistParam(HttpServletRequest request, SectionType type) {
        String parameter = request.getParameter(type.name());
        String[] parameters = request.getParameterValues(type.name());
        return request.getParameterMap()
                .keySet().stream()
                .anyMatch(keyParam -> SectionType.parseOf(keyParam) == type || keyParam.startsWith(type.name()))
                || !isEmptyValue(parameter)
                || (parameters != null && parameters.length >= 2);
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
                    emptyOrganizations.add(new Organization());
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