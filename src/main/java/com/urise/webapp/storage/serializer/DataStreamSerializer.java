package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                writeSection(dos, entry);
            }
        }
    }

    private void writeSection(DataOutputStream dos, Map.Entry<SectionType, Section> type) throws IOException {
        dos.writeUTF(type.getKey().name());
        Section section = type.getValue();
        if (section instanceof ListSection) {
            ListSection listSection = (ListSection) section;
            dos.writeUTF(ListSection.class.getName());
            dos.writeUTF(listSection.getItems().stream().reduce((s, s2) -> s + "," + s2).orElse(""));
        } else if (section instanceof ContentSection) {
            dos.writeUTF(ContentSection.class.getName());
            dos.writeUTF(((ContentSection) section).getContent());
        } else if (section instanceof OrganizationSection) {
            dos.writeUTF(OrganizationSection.class.getName());
            OrganizationSection organizationSection = (OrganizationSection) section;
            List<Organization> organizations = organizationSection.getOrganizations();
            dos.writeInt(organizations.size());
            for (Organization organization : organizations) {
                writeOrganization(dos, organization);
            }
        } else {
            throw new IllegalArgumentException("Неверный тип section");
        }
    }

    private void writeOrganization(DataOutputStream dos, Organization organization) throws IOException {
        OrganizationLink homePage = organization.getHomePage();
        dos.writeUTF(homePage.getName());
        dos.writeUTF(homePage.getUrl() == null ? "" : homePage.getUrl());
        List<Organization.Stages> stages = organization.getStages();
        dos.writeInt(stages.size());
        for (Organization.Stages stage : stages) {
            dos.writeUTF(stage.getStartDate().format(formatter));
            dos.writeUTF(stage.getEndDate() != null ? stage.getEndDate().format(formatter) : "");
            dos.writeUTF(stage.getTitle());
            dos.writeUTF(stage.getResponsibility() == null ? "" : stage.getResponsibility());
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                resume.addSection(SectionType.valueOf(dis.readUTF()), readSection(dis));
            }
            return resume;
        }
    }

    private Section readSection(DataInputStream dis) throws IOException {
        String className = dis.readUTF();
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Error format type", e);
        }
        if (aClass.equals(ListSection.class)) {
            return new ListSection(dis.readUTF().split(","));
        } else if (aClass.equals(ContentSection.class)) {
            return new ContentSection(dis.readUTF());
        } else if (aClass.equals(OrganizationSection.class)) {
            return readOrganization(dis);
        } else {
            throw new IllegalArgumentException("Error section type");
        }
    }

    private OrganizationSection readOrganization(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int size = dis.readInt();

        for (int i = 0; i < size; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            List<Organization.Stages> stages = new ArrayList<>();
            int stagesSize = dis.readInt();
            for (int j = 0; j < stagesSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF(), formatter);
                String endDateStr = dis.readUTF();
                LocalDate endDate = endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr, formatter);
                String title = dis.readUTF();
                String responsibility = dis.readUTF();
                stages.add(new Organization.Stages(
                        startDate,
                        endDate,
                        title,
                        responsibility.isEmpty() ? null : responsibility));
            }
            organizations.add(new Organization(new OrganizationLink(name, url), stages));
        }

        return new OrganizationSection(organizations);
    }
}