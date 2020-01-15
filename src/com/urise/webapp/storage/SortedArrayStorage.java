package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void doSave(Resume resume, Object index) {
        int varIndex = (Integer) index * -1 - 1;
        if (size - varIndex >= 0) System.arraycopy(storage, varIndex, storage, varIndex + 1, size - varIndex);
        storage[varIndex] = resume;
    }

    @Override
    protected void doDelete(Object index) {
        System.arraycopy(storage, (Integer) index + 1, storage, (Integer) index, size - (Integer) index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume newResume = new Resume(uuid, " ");
        return Arrays.binarySearch(storage, 0, size, newResume, RESUME_COMPARATOR);
    }
}