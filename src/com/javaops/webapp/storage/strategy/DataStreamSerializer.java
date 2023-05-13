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
            writeWithException(section.entrySet(), dos, (Map.Entry<SectionType, AbstractSection> entry) -> {
                dos.writeUTF(entry.getKey().name());
                SectionType type = entry.getKey();
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) entry.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeWithException(((ListSection) entry.getValue()).getStrings(), dos, dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            writeWithException(((CompanySection) entry.getValue()).getCompanies(), dos, (company) -> {
                                dos.writeUTF(company.getWebsite().getName());
                                dos.writeUTF((company.getWebsite().getUrl() != null) ? company.getWebsite().getUrl() : "null");
                                writeWithException(company.getPeriods(), dos, (period) -> {
                                    writeData(period.getBegin(), dos);
                                    writeData(period.getEnd(), dos);
                                    dos.writeUTF(period.getTitle());
                                    dos.writeUTF((period.getDescription() != null) ? period.getDescription() : "null");
                                });
                            });
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
            readWithException(dis,()->resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis,()-> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, new ListSection(getSectionList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION -> resume.addSection(type, new CompanySection(getSectionList(dis, () -> new Company(new Link(checkNull(dis.readUTF()), checkNull(dis.readUTF())), getSectionList(dis,
                            () -> new Company.Period(readData(dis), readData(dis), dis.readUTF(), dis.readUTF()))))));
                }
            });
            return resume;
        }
    }

    private String checkNull(String s) {
        return s.equals("null") ? null : s;
    }

    private void writeData(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private LocalDate readData(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumerWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private <T> List<T> getSectionList(DataInputStream dis, CustomConsumerReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }
    private void readWithException(DataInputStream dis, Action action) throws IOException{
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.make();
        }
    }
    @FunctionalInterface
    interface Action {
        void make() throws IOException;
    }
    @FunctionalInterface
    interface CustomConsumerWriter<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    interface CustomConsumerReader<T> {
        T read() throws IOException;

    }
}
