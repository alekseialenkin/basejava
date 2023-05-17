package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.ConnectionFactory;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        SqlHelper<Resume> sqlHelper = new SqlHelper<>("DELETE from RESUME",connectionFactory);
        sqlHelper.make((helper)->{
           helper.execute();
           return null;
        });
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE from resume")
//        ) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SqlHelper <Resume>sqlHelper = new SqlHelper<>("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ? ",connectionFactory);
        sqlHelper.make((helper)->{
            helper.setString(1,r.getUuid());
            helper.setString(2,r.getFullName());
            helper.setString(3,r.getUuid());
            helper.execute();
            return null;
        });
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ? ")
//        ) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.setString(3, r.getUuid());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SqlHelper<Resume> sqlHelper = new SqlHelper<>("INSERT INTO resume(UUID, FULL_NAME) VALUES (?,?)",connectionFactory);
        sqlHelper.make((helper)->{
            helper.setString(1,r.getUuid());
            helper.setString(2,r.getFullName());
            helper.execute();
            return null;
        });
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(UUID, FULL_NAME) VALUES (?,?)")
//        ) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public Resume get(Resume r) {
        LOG.info("Get " + r);
        SqlHelper<Resume> sqlHelper = new SqlHelper<>("SELECT * from resume r WHERE r.uuid = ?",connectionFactory);
        return sqlHelper.make((helper) -> {
            helper.setString(1,r.getUuid());
            ResultSet rs = helper.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(r.getUuid());
            }
            return new Resume(r.getUuid(),rs.getString("full_name"));
        });

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * from resume r WHERE r.uuid = ?")
//        ) {
//            ps.setString(1, r.getUuid());
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(r.getUuid());
//            }
//            return new Resume(r.getUuid(), rs.getString("full_name"));
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void delete(Resume r) {
        LOG.info("Delete " + r);
        SqlHelper<Resume> sqlHelper = new SqlHelper<>("DELETE from resume where uuid = ?",connectionFactory);
        sqlHelper.make((helper)->{
           helper.setString(1,r.getUuid());
           helper.execute();
           return null;
        });
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE from resume where uuid = ?")
//        ) {
//            ps.setString(1, r.getUuid());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> resumes = new ArrayList<>();
        SqlHelper<Resume> sqlHelper = new SqlHelper<>("SELECT * FROM resume",connectionFactory);
        sqlHelper.make((helper)->{
            ResultSet rs = helper.executeQuery();
            while (rs.next()){
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return null;
        });
        resumes.sort(AbstractStorage.RESUME_COMPARATOR);
        return resumes;
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")
//        ) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
//            }
//            resumes.sort(AbstractStorage.RESUME_COMPARATOR);
//            return resumes;
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

    }

    @Override
    public int size() {
        LOG.info("Get size");
        return getAllSorted().size();
    }
}
