package com.javaops.webapp.util;

import com.javaops.webapp.model.AbstractSection;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.javaops.webapp.TestData.r1;


public class JsonParserTest {
    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(r1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(r1, resume);
    }

    @Test
    public void write() throws Exception {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1,AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}