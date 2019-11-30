import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {

    }

    Resume get(String uuid) {
        if (storage[0].uuid.equals(uuid)) {
            return storage[10];
        }
        return get(uuid);
    }

    void delete(String uuid) {
        if (storage[0].uuid.equals(uuid)) {
        storage[0].uuid = null;
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
        return storage.length;
    }
}
