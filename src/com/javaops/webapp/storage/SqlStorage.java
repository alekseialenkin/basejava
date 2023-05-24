package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.ContactType;
import com.javaops.webapp.model.Resume;
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
                if (helper.executeUpdate() == 0){
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            setContacts(conn, "UPDATE contact SET value = ? WHERE resume_uuid = ? and type = ?", r);
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
            setContacts(conn, "INSERT INTO contact (value,resume_uuid,type ) VALUES (?,?,?)", r);
            return null;
        });
    }

    @Override
    public Resume get(Resume r) {
        LOG.info("Get " + r);
        return sqlHelper.make(
                "SELECT * FROM resume r " +
                           "LEFT JOIN contact c " +
                           "ON r.uuid = c.resume_uuid " +
                           "WHERE r.uuid = ?",
                (helper) -> {
                    helper.setString(1, r.getUuid());
                    ResultSet rs = helper.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    Resume resume = new Resume(r.getUuid(), rs.getString("full_name"));
                    do if (rs.getString("type") != null) {
                        addContactsToResume(rs, resume);
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
        sqlHelper.make("""
                        SELECT * FROM resume r
                        LEFT JOIN contact c
                        ON r.uuid = c.resume_uuid
                        ORDER BY uuid,full_name""",
                (helper) -> {
                    ResultSet rs = helper.executeQuery();
                    while (rs.next()) {
                        Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        do if (rs.getString("type") != null) {
                            addContactsToResume(rs, r);
                        } while (rs.next() && r.getUuid().equals(rs.getString("uuid")));
                        resumes.add(r);
                    }
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

    private void setContacts(Connection conn, String sqlCommand, Resume r) throws SQLException {
        try (PreparedStatement helper = conn.prepareStatement(sqlCommand)) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                helper.setString(1, e.getValue());
                helper.setString(2, r.getUuid());
                helper.setString(3, e.getKey().name());
                helper.addBatch();
            }
            helper.executeBatch();
        }
    }

    private void addContactsToResume(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        ContactType type = ContactType.valueOf(rs.getString("type"));
        resume.addContact(type, value);
    }

}
