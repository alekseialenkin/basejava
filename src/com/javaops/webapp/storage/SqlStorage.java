package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
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
        sqlHelper.setSqlCommand("DELETE from RESUME");
        sqlHelper.make((helper) -> {
            helper.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.setSqlCommand("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ? ");
        sqlHelper.make((helper) -> {
            helper.setString(1, r.getUuid());
            helper.setString(2, r.getFullName());
            helper.setString(3, r.getUuid());
            helper.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.setSqlCommand("INSERT INTO resume(UUID, FULL_NAME) VALUES (?,?)");
        sqlHelper.make((helper) -> {
            helper.setString(1, r.getUuid());
            helper.setString(2, r.getFullName());
            helper.execute();
            return null;
        });
    }

    @Override
    public Resume get(Resume r) {
        LOG.info("Get " + r);
        sqlHelper.setSqlCommand("SELECT * from resume r WHERE r.uuid = ?");
        return (Resume) sqlHelper.make((helper) -> {
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
        sqlHelper.setSqlCommand("DELETE from resume where uuid = ?");
        sqlHelper.make((helper) -> {
            helper.setString(1, r.getUuid());
            helper.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.setSqlCommand("SELECT * FROM resume ORDER BY full_name,uuid");
        sqlHelper.make((helper) -> {
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
        sqlHelper.setSqlCommand("SELECT count(uuid) FROM resume");
        return (int) sqlHelper.make((helper)->{
            ResultSet rs = helper.executeQuery();
            int size = -1;
            while(rs.next()){
                size=rs.getInt(1);
            }
            return size;
        });
    }
}
