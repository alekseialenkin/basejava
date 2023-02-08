/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
                size --;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                size++;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume != null) {
                if (uuid.equals(resume.toString())) {
                    return resume;
                }
            }
            else {
                break;}
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                if (uuid.equals(storage[i].toString())) {
                    storage[i] = storage[i + 1];
                    storage[i] = null;
                }
            } else if (i < storage.length - 1) {
                storage[i] = storage[i + 1];
                storage[i] = null;
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        for (Resume resume : storage) {
            if (resume != null) {
                for (int j = 0; j < resumes.length; j++) {
                    if (resumes[j] == null) {
                        resumes[j] = resume;
                        break;
                    }
                }
            }
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
