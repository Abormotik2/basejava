package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

   private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

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

    private void writeSection(DataOutputStream dos, Map.Entry<SectionType, Section> entry) throws IOException {
        Section section = entry.getValue();
        SectionType type = entry.getKey();
        dos.writeUTF(type.name());
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                dos.writeUTF(((ContentSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                List<String> collection = ((ListSection) section).getItems();
                dos.writeInt(collection.size());
                for (String item : collection) {
                    dos.writeUTF(item);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                dos.writeInt(organizations.size());
                for (Organization organization : organizations) {
                    writeOrganization(dos, organization);
                }
                break;
        }
    }

    private void writeOrganization(DataOutputStream dos, Organization organization) throws IOException {
        OrganizationLink homePage = organization.getHomePage();
        dos.writeUTF(homePage.getName());
        dos.writeUTF(homePage.getUrl() == null ? "" : homePage.getUrl());
        List<Organization.Stages> stages = organization.getStages();
        dos.writeInt(stages.size());
        for (Organization.Stages stage : stages) {
            dos.writeUTF(stage.getStartDate().format(FORMATTER));
            dos.writeUTF(stage.getEndDate().format(FORMATTER));
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
                String sectionTypeName = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(sectionTypeName);
                resume.addSection(sectionType, readSection(dis, sectionType));
            }
            return resume;
        }
    }


    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new ContentSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATION:
                int listSize = dis.readInt();
                List<String> list = new ArrayList<>();
                for (int i = 0; i < listSize; i++) {
                    list.add(dis.readUTF());
                }
                return new ListSection(list);
            case EXPERIENCE:
            case EDUCATION:
                return readOrganization(dis);
        }
        return null;
    }

    private OrganizationSection readOrganization(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            url = url.isEmpty() ? null : url;
            List<Organization.Stages> stages = new ArrayList<>();
            int stagesSize = dis.readInt();
            for (int j = 0; j < stagesSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF(), FORMATTER);
                LocalDate endDate = LocalDate.parse(dis.readUTF(), FORMATTER);
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