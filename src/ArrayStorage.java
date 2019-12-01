import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i].uuid = null;
            }
        }
        for (int i = 0; i < storage.length; i++) {
            if (!storage[i].uuid.equals(uuid)) {
                storage[i + 1] = storage[i];
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                return Arrays.copyOf(storage, i);
            }
        }
        return storage;
    }

    int size() {
        int iter = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                iter++;
            }
        }return iter;
    }
}
