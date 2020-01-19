package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        Map<ContactType, String> contactInfo = resume.getContacts();
        Map<SectionType, Section> sections = resume.getSections();
        contactInfo.put(MOBILE, "+7(921) 855-0482");
        contactInfo.put(SKYPE, "grigory.kislin");
        contactInfo.put(MAIL, "gkislin@yandex.ru");
        contactInfo.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contactInfo.put(GITHUB, "https://github.com/gkislin");
        contactInfo.put(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contactInfo.put(HOME_PAGE, "http://gkislin.ru/");
        sections.put(OBJECTIVE, new ContentSection<>("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sections.put(PERSONAL, new ContentSection<>("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        List<ContentSection<String>> achievements = new ArrayList<>();
        achievements.add(new ContentSection<>("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников."));
        achievements.add(new ContentSection<>("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."));
        achievements.add(new ContentSection<>("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
        achievements.add(new ContentSection<>("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга."));
        achievements.add(new ContentSection<>("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)."));
        achievements.add(new ContentSection<>("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        sections.put(ACHIEVEMENT, new ListSection<>(achievements));
        List<ContentSection<String>> qualifications = new ArrayList<>();
        qualifications.add(new ContentSection<>("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"));
        qualifications.add(new ContentSection<>("Version control: Subversion, Git, Mercury, ClearCase, Perforce"));
        qualifications.add(new ContentSection<>("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,"));
        qualifications.add(new ContentSection<>("MySQL, SQLite, MS SQL, HSQLDB"));
        qualifications.add(new ContentSection<>("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,"));
        qualifications.add(new ContentSection<>("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,"));
        qualifications.add(new ContentSection<>("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)."));
        qualifications.add(new ContentSection<>("Python: Django"));
        qualifications.add(new ContentSection<>("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"));
        qualifications.add(new ContentSection<>("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka"));
        qualifications.add(new ContentSection<>("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT."));
        qualifications.add(new ContentSection<>("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,"));
        qualifications.add(new ContentSection<>("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer."));
        qualifications.add(new ContentSection<>("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования"));
        qualifications.add(new ContentSection<>("Родной русский, английский \"upper intermediate\""));
        sections.put(QUALIFICATION, new ListSection<>(qualifications));
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(
                new ContactsOrg("Java Online Projects", "http://javaops.ru/")
                , LocalDate.parse("10.2013", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.now()
                , "Автор проекта."
                , "Создание, организация и проведение Java онлайн проектов и стажировок."));
        organizationList.add(new Organization(
                new ContactsOrg("Wrike", "https://www.wrike.com/")
                , LocalDate.parse("10.2014", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("01.2016", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Старший разработчик (backend)"
                , "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        organizationList.add(new Organization(
                new ContactsOrg("RIT Center", "")
                , LocalDate.parse("04.2012", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("10.2014", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Java архитектор"
                , "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        organizationList.add(new Organization(
                new ContactsOrg("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/")
                , LocalDate.parse("12.2010", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("04.2012", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Ведущий программист"
                , "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        organizationList.add(new Organization(
                new ContactsOrg("Yota", "https://www.yota.ru/")
                , LocalDate.parse("06.2008", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("01.2010", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Ведущий специалист"
                , "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        organizationList.add(new Organization(
                new ContactsOrg("Enkata", "http://enkata.com/")
                , LocalDate.parse("03.2007", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("06.2008", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Разработчик ПО"
                , "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        organizationList.add(new Organization(
                new ContactsOrg("Siemens AG", "https://www.siemens.com/ru/ru/home.html")
                , LocalDate.parse("01.2005", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("02.2007", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Разработчик ПО"
                , "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        organizationList.add(new Organization(
                new ContactsOrg("Alcatel", "http://www.alcatel.ru/")
                , LocalDate.parse("09.1997", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("01.2005", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Инженер по аппаратному и программному тестированию"
                , "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        sections.put(EXPERIENCE, new ListSection<>(organizationList));
        List<Organization> studyList = new ArrayList<>();
        studyList.add(new Organization(new ContactsOrg("Coursera", "https://www.coursera.org/course/progfun")
                , LocalDate.parse("03.2013", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("05.2013", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        studyList.add(new Organization(new ContactsOrg("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
                , LocalDate.parse("03.2011", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("04.2011", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        studyList.add(new Organization(new ContactsOrg("Siemens AG", "http://www.siemens.ru/")
                , LocalDate.parse("01.2005", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("04.2005", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "3 месяца обучения мобильным IN сетям (Берлин)"));
        studyList.add(new Organization(new ContactsOrg("Alcatel", "http://www.alcatel.ru/")
                , LocalDate.parse("09.1997", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("03.1998", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "6 месяцев обучения цифровым телефонным сетям (Москва)"));
        studyList.add(new Organization(new ContactsOrg("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/")
                , LocalDate.parse("09.1993", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("07.1996", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Аспирантура (программист С, С++)"));
        studyList.add(new Organization(new ContactsOrg("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/")
                , LocalDate.parse("09.1987", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("07.1993", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Инженер (программист Fortran, C)"));
        studyList.add(new Organization(new ContactsOrg("Yota", "https://www.yota.ru/")
                , LocalDate.parse("09.1984", DateTimeFormatter.ofPattern("MM.yyyy"))
                , LocalDate.parse("01.1987", DateTimeFormatter.ofPattern("MM.yyyy"))
                , "Закончил с отличием"));
        sections.put(EDUCATION, new ListSection<>(studyList));
        System.out.println(resume);
    }
}