package com.javaops.webapp;

import com.javaops.webapp.model.*;
import com.javaops.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class ResumeTestData {
    public static Resume resumeTest(String fullName,String uuid){
        Resume r = new Resume(uuid,fullName);
        r.getContacts().put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        r.getContacts().put(ContactType.SKYPE, "skype:skype:grigory.kislin");
        r.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        r.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        r.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        AbstractSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям.");
        r.getSections().put(SectionType.OBJECTIVE, objective);
        AbstractSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        r.getSections().put(SectionType.PERSONAL, personal);
        AbstractSection achievements = new TextSection("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет" +
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников." +
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk." +
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера." +
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга." +
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)." +
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        r.getSections().put(SectionType.ACHIEVEMENT, achievements);
        AbstractSection qualifications = new TextSection("""
                JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2
                Version control: Subversion, Git, Mercury, ClearCase, Perforce
                DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB
                Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy
                XML/XSD/XSLT, SQL, C/C++, Unix shell scripts
                Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).
                Python: Django.
                JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js
                Scala: SBT, Play2, Specs2, Anorm, Spray, Akka
                Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.
                Инструменты: Maven + plugin development, Gradle, настройка Ngnix
                администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer
                Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования
                Родной русский, английский "upper intermediate\"""");
        r.getSections().put(SectionType.QUALIFICATIONS, qualifications);
        Period MFTIschool = new Period(DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE), "Заочная физико-техническая школа при МФТИ", "Закончил с отличием");
        List<Period> MFTIStudying = List.of(MFTIschool);
        Company mfti = new Company("МФТИ", "https://mipt.ru/", MFTIStudying);
        Period engineer = new Period(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "Инженер (программист Fortran, C)");
        Period phD = new Period(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "Аспирантура (программист С, С++)");
        List<Period> SPBNRUITMOstudying = List.of(engineer, phD);
        Company SPBNRUITMO = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", SPBNRUITMOstudying);
        AbstractSection education = new CompanySection(List.of(mfti, SPBNRUITMO));
        r.getSections().put(SectionType.EDUCATION, education);
        Period ritCenterWorking = new Period(DateUtil.of(2012, Month.of(4)), DateUtil.of(2014, Month.of(10)), "Java architector", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO." +
                " Архитектура БД и серверной части системы. " +
                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Company ritCenter = new Company("RIT Center", "null", List.of(ritCenterWorking));
        Company wrike = new Company("Wrike", "https://www.wrike.com/", List.of(new Period(DateUtil.of(2014, Month.of(10)), DateUtil.of(2016, Month.of(1)), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        Period javaOnlineProjectsPeriod = new Period(DateUtil.of(2013,Month.of(10)), LocalDate.now(),"Java Online Projects","Автор проекта");
        Company JOP = new Company("Java online Projects", "http://javaops.ru/",List.of(javaOnlineProjectsPeriod));
        CompanySection job = new CompanySection(List.of(ritCenter, wrike));
        r.getSections().put(SectionType.EXPERIENCE, job);
        return r;
    }
    public static void main(String[] args) {
        Resume r = resumeTest("Григорий Кислин", "uuid1");
        System.out.println(r);
        System.out.println(r.getContacts());
        System.out.println(r.getSections());
    }
}
