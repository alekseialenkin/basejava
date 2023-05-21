package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper<Object> sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper<>(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
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
        sqlHelper.make("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ? ", (helper) -> {
            helper.setString(1, r.getUuid());
            helper.setString(2, r.getFullName());
            helper.setString(3, r.getUuid());
            if (helper.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.make("INSERT INTO resume(UUID, FULL_NAME) VALUES (?,?)", (helper) -> {
            try {
                helper.setString(1, r.getUuid());
                helper.setString(2, r.getFullName());
                helper.execute();
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(null);
                } else {
                    throw new StorageException(null);
                }
            }
            return null;
        });

    }

    @Override
    public Resume get(Resume r) {
        LOG.info("Get " + r);
        return (Resume) sqlHelper.make("SELECT * from resume r WHERE r.uuid = ?", (helper) -> {
            helper.setString(1, r.getUuid());
            ResultSet rs = helper.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(r.getUuid());
            }
            return new Resume(r.getUuid(), rs.getString("full_name"));
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
        sqlHelper.make("SELECT * FROM resume ORDER BY full_name,uuid", (helper) -> {
            ResultSet rs = helper.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return null;
        });
        return resumes;
    }

    @Override
    public int size() {
        LOG.info("Get size");
        return (int) sqlHelper.make("SELECT count(uuid) FROM resume", (helper) -> {
            ResultSet rs = helper.executeQuery();
            return rs.next() ? rs.getInt(1) : rs.next();
        });
    }
}