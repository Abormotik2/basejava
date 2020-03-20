package com.urise.webapp.util;

import com.urise.webapp.model.ContentSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractStorageTest.RESUME1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME1, resume);
    }

    @Test
    public void doWrite() {
        Section section1 = new ContentSection("Object1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}