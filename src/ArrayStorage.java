/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = size; i > 0; i--) {
            storage[i] = null;
            size--;
        }
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = storage[i++];
                for (int j = i - 1; j < size; j++) {
                    storage[j] = storage[j++];
                }
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size(); j++) {
                resumes[j] = storage[i];
            }
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
