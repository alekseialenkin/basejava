package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.*;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.make("DELETE from RESUME", (helper) -> {
            helper.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement helper = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                helper.setString(1, r.getFullName());
                helper.setString(2, r.getUuid());
                if (helper.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
                deleteContacts(conn, r);
                setContacts(conn, r);
                deleteSections(conn, r);
                setSections(conn, r);
            }
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement helper = conn.prepareStatement("INSERT INTO resume (UUID, FULL_NAME) VALUES (?,?)")) {
                helper.setString(1, r.getUuid());
                helper.setString(2, r.getFullName());
                helper.execute();
            }
            setContacts(conn, r);
            setSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(Resume r) {
        LOG.info("Get " + r);
        return sqlHelper.make(
                """
                        SELECT * FROM resume r
                        LEFT JOIN contact c
                        ON r.uuid = c.resume_uuid
                        LEFT JOIN section s
                        ON c.resume_uuid = s.resume_uuid
                        WHERE r.uuid = ?""",
                (helper) -> {
                    helper.setString(1, r.getUuid());
                    ResultSet rs = helper.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    Resume resume = new Resume(r.getUuid(), rs.getString("full_name"));
                    do {
                        addContactToResume(rs, resume);
                        addSectionToResume(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(Resume r) {
        LOG.info("Delete " + r);
        sqlHelper.make("DELETE from resume where uuid = ?", (helper) -> {
            helper.setString(1, r.getUuid());
            if (helper.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from resume ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    addToResume("SELECT * FROM contact WHERE resume_uuid=?",conn,r);
                    addToResume("SELECT * FROM section where resume_uuid =?",conn,r);
                    resumes.add(r);
                }
            }
            System.out.println(resumes);
            return null;
        });
        return resumes;
    }

    @Override
    public int size() {
        LOG.info("Get size");
        return sqlHelper.make("SELECT count(uuid) FROM resume", (helper) -> {
            ResultSet rs = helper.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        });
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement helper = conn.prepareStatement("DELETE from contact where resume_uuid = ?")) {
            helper.setString(1, r.getUuid());
            helper.execute();
        }
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement helper = conn.prepareStatement("DELETE from section where resume_uuid = ?")) {
            helper.setString(1, r.getUuid());
            helper.execute();
        }
    }

    private void setContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement helper = conn.prepareStatement("INSERT INTO contact (resume_uuid,type,value ) VALUES (?,?,?) ")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                helper.setString(1, r.getUuid());
                helper.setString(2, e.getKey().name());
                helper.setString(3, e.getValue());
                helper.addBatch();
            }
            helper.executeBatch();
        }
    }

    private void setSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement helper = conn.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                helper.setString(1, r.getUuid());
                SectionType type = e.getKey();
                helper.setString(2, type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> helper.setString(3, ((TextSection) e.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        StringBuilder st = new StringBuilder();
                        ((ListSection) e.getValue()).getStrings().forEach(s -> st.append(s).append("\n"));
                        helper.setString(3, st.toString());
                    }
                }
                helper.addBatch();
            }
            helper.executeBatch();
        }
    }

    private void addContactToResume(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSectionToResume(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("section_value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("section_type"));
            switch (type) {
                case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value));
                case ACHIEVEMENT, QUALIFICATIONS ->
                        resume.addSection(type, new ListSection(value.replaceAll("\n", ",").split(",")));
            }
        }
    }

    private void addToResume(String sqlCommand, Connection conn, Resume r) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, r.getUuid());
            ResultSet execute = preparedStatement.executeQuery();
            while (execute.next()) {
                if (sqlCommand.contains("contact")) {
                    addContactToResume(execute, r);
                } else {
                    addSectionToResume(execute, r);
                }
            }
        }
    }
}
