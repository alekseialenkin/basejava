package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.model.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.time.Month;
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
                        for (String s : lst.getStrings()) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection cs = (CompanySection) entry.getValue();
                        for (Company c : cs.getCompanies()) {
                            dos.writeUTF(c.getWebsite().getName());
                            if (c.getWebsite().getUrl() != null) {
                                dos.writeUTF(c.getWebsite().getUrl());
                            } else {
                                dos.writeUTF("null");
                            }
                            for (Company.Period p : c.getPeriods()) {
                                dos.writeUTF(p.getTitle());
                                if (p.getDescription() != null){
                                dos.writeUTF(p.getDescription());}
                                else {dos.writeUTF("null");}
                                dos.write(p.getBegin().getDayOfMonth());
                                dos.write(p.getBegin().getMonth().getValue());
                                dos.write(p.getBegin().getYear());
                                dos.write(p.getEnd().getDayOfMonth());
                                dos.write(p.getEnd().getMonth().getValue());
                                dos.write(p.getEnd().getYear());
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
            for (int i = size + 1; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL -> resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                    case OBJECTIVE -> resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT -> resume.addSection(SectionType.ACHIEVEMENT, new ListSection(dis.readUTF()));
                    case QUALIFICATIONS ->
                            resume.addSection(SectionType.QUALIFICATIONS, new ListSection(dis.readUTF()));
                    case EXPERIENCE ->
                            resume.addSection(SectionType.EXPERIENCE, new CompanySection(new Company(dis.readUTF(), dis.readUTF(), new Company.Period(dis.readInt(), Month.of(dis.readInt()), dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()))));
                    case EDUCATION ->
                            resume.addSection(SectionType.EDUCATION, new CompanySection(new Company(dis.readUTF(), dis.readUTF(), new Company.Period(dis.readInt(), Month.of(dis.readInt()), dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()))));
                }
            }
            return resume;
        }
    }
}
