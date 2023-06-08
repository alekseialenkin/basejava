package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public final void update(Resume r) {
        LOG.info("Update" + r);
        doUpdate(getNotExistingSearchKey(r.getUuid()), r);
    }

    @Override
    public final void save(Resume r) {
        LOG.info("Save" + r);
        doSave(getExistingSearchKey(r.getUuid()), r);
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get" + uuid);
        return doGet(getNotExistingSearchKey(uuid));
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete" + uuid);
        doDelete(getNotExistingSearchKey(uuid));
    }

    protected final static Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public final List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        doGetAll().sort(RESUME_COMPARATOR);
        return doGetAll();
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract List<Resume> doGetAll();

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume r);

    protected abstract void doDelete(SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume r);
}
