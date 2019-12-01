import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            }
            storage[i] = null;
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < storage.length; j++) {
                    storage[j] = j + 1 >= storage.length ? null : storage[j + 1];
                    if (storage[j] == null) {
                        i = storage.length;
                        break;
                    }
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, this.size());
    }

    int size() {
        int iter = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                break;
            }
            iter++;
        }
        return iter;
    }
}