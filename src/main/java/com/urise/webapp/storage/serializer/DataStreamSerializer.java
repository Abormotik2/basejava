package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> section = resume.getSections();
            writeCollection(dos, section.entrySet(), entry -> writeSection(dos, entry));
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
                writeCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCollection(dos, ((OrganizationSection) section).getOrganizations(), organization -> writeOrganization(dos, organization));
                break;
        }
    }

    private void writeOrganization(DataOutputStream dos, Organization organization) throws IOException {
        OrganizationLink homePage = organization.getHomePage();
        dos.writeUTF(homePage.getName());
        dos.writeUTF(homePage.getUrl());
        writeCollection(dos, organization.getStages(), stages -> {
            dos.writeUTF(stages.getStartDate().format(FORMATTER));
            dos.writeUTF(stages.getEndDate().format(FORMATTER));
            dos.writeUTF(stages.getTitle());
            dos.writeUTF(stages.getResponsibility());
        });
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
                return new ListSection(readCollection(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return readOrganization(dis);
        }
        return null;
    }

    private OrganizationSection readOrganization(DataInputStream dis) throws IOException {
            return new OrganizationSection(readCollection(dis, () ->
                    new Organization(new OrganizationLink(dis.readUTF(), dis.readUTF()),
                            readCollection(dis, () -> new Organization.Stages(LocalDate.parse(dis.readUTF(), FORMATTER), LocalDate.parse(dis.readUTF(), FORMATTER), dis.readUTF(), dis.readUTF())))));

    }
    private interface Writer<T> {
        void write(T t) throws IOException;
    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readCollection(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T collections : collection) {
            writer.write(collections);
        }
    }
}