package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteItems(conn, "DELETE  FROM contact WHERE resume_uuid=?", resume.getUuid());
            deleteItems(conn, "DELETE  FROM section WHERE resume_uuid=?", resume.getUuid());
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            getItems(conn, "SELECT * FROM contact WHERE resume_uuid =?", resume, this::completeContacts);
            getItems(conn, "SELECT * FROM section WHERE resume_uuid =?", resume, this::completeSections);
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String name = rs.getString("full_name");
                    map.computeIfAbsent(uuid, k -> new Resume(uuid, name));
                }
            }
            getAllItems(conn, "SELECT * FROM contact", map, this::completeContacts);
            getAllItems(conn, "SELECT * FROM section", map, this::completeSections);
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : rs.getInt(0);
        });
    }

    private void deleteItems(Connection conn, String sql, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void getItems(Connection conn, String sql, Resume resume, Complete full) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                full.complete(resume, rs);
            }
        }
    }

    private void getAllItems(Connection conn, String sql, Map<String, Resume> map, Complete full) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = map.get(rs.getString("resume_uuid"));
                full.complete(resume, rs);
            }
        }
    }

    private interface Complete {
        void complete(Resume resume, ResultSet rs) throws SQLException;
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section(resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                Section section = e.getValue();
                SectionType type = e.getKey();
                ps.setString(1, resume.getUuid());
                ps.setString(2, type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(3, ((ContentSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> list = ((ListSection) section).getItems();
                        ps.setString(3, String.join("\n", list));
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void completeContacts(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.addContact(type, value);
        }
    }

    private void completeSections(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSection(type, new ContentSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    List<String> list = new ArrayList<>(Arrays.asList(value.split("\n")));
                    resume.addSection(type, new ListSection(list));
                    break;
            }
        }
    }
}



