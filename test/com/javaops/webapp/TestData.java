package com.javaops.webapp;

import com.javaops.webapp.model.*;

import java.io.File;
import java.time.Month;

public class TestData {
    public final static File STORAGEDIR = Config.get().getStorageDir();

    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String FULLNAME_1 = "Petrov Ivan";
    public static final String FULLNAME_2 = "Sidorov Fedor";
    public static final String FULLNAME_3 = "Ivanova Mariya";
    public static final Resume r1;
    public static final Resume r2;
    public static final Resume r3;
    public static final Resume r4 = new Resume("uuid4", "Ivan Ivanov");
    public static final Resume dummy ;

    static {
        r1 = new Resume(UUID_1, FULLNAME_1);
        r2 = new Resume(UUID_2, FULLNAME_2);
        r3 = new Resume(UUID_3, FULLNAME_3);
        dummy = new Resume("dummy", "Dummy");
        r1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        r1.addContact(ContactType.PHONE_NUMBER, "11111");
        r1.addContact(ContactType.SKYPE,"SKYPE");
        r1.addContact(ContactType.GITHUB,"GitHub");
        r1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        r1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        r1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement11", "Achievement12", "Achievement13"));
        r1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        r1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company11", "http://Company11.ru",
                                new Company.Period(2005, Month.JANUARY, "Period1", "content1"),
                                new Company.Period(2001, Month.MARCH, 2005, Month.JANUARY, "Period2", "content2"))));
        r1.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Institute", null,
                                new Company.Period(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Company.Period(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Company("Company12", "http://Company12.ru")));
        r2.addContact(ContactType.SKYPE, "skype2");
        r2.addContact(ContactType.PHONE_NUMBER, "22222");
        r1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company2", "http://Company2.ru",
                                new Company.Period(2015, Month.JANUARY, "Period1", "content1"))));
    }
}
