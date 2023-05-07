package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.model.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serialization<Data> {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            Map<SectionType, AbstractSection> section = r.getSections();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(section.size());
            for (Map.Entry<SectionType, AbstractSection> entry : section.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                SectionType type = entry.getKey();
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection text = (TextSection) entry.getValue();
                        dos.writeUTF(text.getText());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection lst = (ListSection) entry.getValue();
                        dos.writeInt(lst.getStrings().size());
                        for (String s : lst.getStrings()) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection cs = (CompanySection) entry.getValue();
                        dos.writeInt(cs.getCompanies().size());
                        for (Company c : cs.getCompanies()) {
                            dos.writeUTF(c.getWebsite().getName());
                            dos.writeUTF((c.getWebsite().getUrl() != null) ? c.getWebsite().getUrl() : "null");
                            dos.writeInt(c.getPeriods().size());
                            for (Company.Period p : c.getPeriods()) {
                                writeData(p.getBegin(), dos);
                                writeData(p.getEnd(), dos);
                                dos.writeUTF(p.getTitle());
                                dos.writeUTF((p.getDescription() != null) ? p.getDescription() : "null");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL -> resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                    case OBJECTIVE -> resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT -> {
                        int size1 = dis.readInt();
                        List<String> list = new ArrayList<>(size1);
                        for (int k = 0; k < size1; k++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(list));
                    }
                    case QUALIFICATIONS -> {
                        int size1 = dis.readInt();
                        List<String> list = new ArrayList<>(size1);
                        for (int k = 0; k < size1; k++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(list));
                    }
                    case EXPERIENCE -> {
                        int size1 = dis.readInt();
                        List<Company> list1 = new ArrayList<>(size1);
                        for (int k = 0; k < size1; k++) {
                            list1.add(new Company(new Link(dis.readUTF(),dis.readUTF()),getList(dis)));
                        }
                        resume.addSection(SectionType.EXPERIENCE, new CompanySection(list1));
                    }
                    case EDUCATION -> {
                        int size1 = dis.readInt();
                        List<Company> list1 = new ArrayList<>(size1);
                        for (int k = 0; k < size1; k++) {
                            list1.add(new Company(new Link(dis.readUTF(),dis.readUTF()),getList(dis)));
                        }
                        resume.addSection(SectionType.EDUCATION, new CompanySection(list1));
                    }
                }
            }
            return resume;
        }
    }
    private static List<Company.Period> getList(DataInputStream dis) throws IOException {
        int size2 = dis.readInt();
        List<Company.Period> periods = new ArrayList<>();
        for (int j = 0; j < size2;j++){
            Company.Period p = new Company.Period(readData(dis), readData(dis), dis.readUTF(), dis.readUTF());
            periods.add(p);
        }
        return periods;
    }
    private static void writeData(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }

    private static LocalDate readData(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(),1);
    }
}
