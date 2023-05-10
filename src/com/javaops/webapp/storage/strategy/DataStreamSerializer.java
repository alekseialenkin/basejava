package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.model.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
            writeWithException(contacts.entrySet(), dos, (Map.Entry<ContactType, String> entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeWithException(section.entrySet(),dos,(Map.Entry<SectionType, AbstractSection> entry)->{
                dos.writeUTF(entry.getKey().name());
                SectionType type = entry.getKey();
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection text = (TextSection) entry.getValue();
                        dos.writeUTF(text.getText());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection lst = (ListSection) entry.getValue();
                        writeWithException(lst.getStrings(), dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection cs = (CompanySection) entry.getValue();
                        writeWithException(cs.getCompanies(),dos,(company)->{
                            dos.writeUTF(company.getWebsite().getName());
                            dos.writeUTF((company.getWebsite().getUrl() != null) ? company.getWebsite().getUrl() : "null");
                            writeWithException(company.getPeriods(),dos,(period)->{
                                writeData(period.getBegin(), dos);
                                writeData(period.getEnd(), dos);
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF((period.getDescription() != null) ? period.getDescription() : "null");
                            });
                        });
                    }
                }
            });
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
                    case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int size1 = dis.readInt();
                        List<String> list = new ArrayList<>(size1);
                        for (int k = 0; k < size1; k++) {
                            list.add(checkNull(dis.readUTF()));
                        }
                        resume.addSection(type, new ListSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int size1 = dis.readInt();
                        List<Company> list1 = new ArrayList<>(size1);
                        for (int k = 0; k < size1; k++) {
                            list1.add(new Company(new Link(checkNull(dis.readUTF()), checkNull(dis.readUTF())), getPeriods(dis)));
                        }
                        resume.addSection(type, new CompanySection(list1));
                    }
                }
            }
            return resume;
        }
    }

    private String checkNull(String s) {
        return s.equals("null") ? null : s;
    }

    private List<Company.Period> getPeriods(DataInputStream dis) throws IOException {
        int size2 = dis.readInt();
        List<Company.Period> periods = new ArrayList<>();
        for (int j = 0; j < size2; j++) {
            Company.Period p = new Company.Period(readData(dis), readData(dis), checkNull(dis.readUTF()), checkNull(dis.readUTF()));
            periods.add(p);
        }
        return periods;
    }

    private void writeData(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private LocalDate readData(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    @FunctionalInterface
    interface CustomConsumer<T> {
        void write(T t) throws IOException;
    }
}
