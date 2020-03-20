package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static com.urise.webapp.storage.AbstractStorageTest.*;
import static com.urise.webapp.util.DateUtil.NOW;

public class ResumeTestData {


    public static void main(String[] args) {
        System.out.println(getFirstResume());
        System.out.println(getSecondResume());
        System.out.println(getThirdResume());
        System.out.println(getFourthResume());
    }

    public static Resume getFirstResume() {
        Resume firstResume = new Resume(UUID_1, "Григорий Кислин");
        firstResume.addContact(MOBILE, "+7(921) 855-0482");
        firstResume.addContact(SKYPE, "grigory.kislin");
        firstResume.addContact(MAIL, "gkislin@yandex.ru");
        firstResume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        firstResume.addContact(GITHUB, "https://github.com/gkislin");
        firstResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        firstResume.addContact(HOME_PAGE, "http://gkislin.ru/");
        firstResume.addSection(OBJECTIVE, new ContentSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        firstResume.addSection(PERSONAL, new ContentSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        firstResume.addSection(ACHIEVEMENT, new ListSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        firstResume.addSection(QUALIFICATION, new ListSection(qualifications));
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(
                new OrganizationLink("Java Online Projects", "http://javaops.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2013, 10, 1)
                , NOW
                , "Автор проекта."
                , "Создание, организация и проведение Java онлайн проектов и стажировок."))));
        organizationList.add(new Organization(
                new OrganizationLink("Wrike", "https://www.wrike.com/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2014, 10, 1)
                , LocalDate.of(2016, 1, 1)
                , "Старший разработчик (backend)"
                , "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))));
        organizationList.add(new Organization(
                new OrganizationLink("RIT Center", null)
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2012, 4, 1)
                , LocalDate.of(2014, 10, 1)
                , "Java архитектор"
                , "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"))));
        organizationList.add(new Organization(
                new OrganizationLink("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2010, 12, 1)
                , LocalDate.of(2012, 4, 1)
                , "Ведущий программист"
                , "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."))));
        organizationList.add(new Organization(
                new OrganizationLink("Yota", "https://www.yota.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2008, 6, 1)
                , LocalDate.of(2010, 1, 1)
                , "Ведущий специалист"
                , "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"))));
        organizationList.add(new Organization(
                new OrganizationLink("Enkata", "http://enkata.com/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2007, 3, 1)
                , LocalDate.of(2008, 6, 1)
                , "Разработчик ПО"
                , "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."))));
        organizationList.add(new Organization(
                new OrganizationLink("Siemens AG", "https://www.siemens.com/ru/ru/home.html")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2005, 1, 1)
                , LocalDate.of(2007, 2, 1)
                , "Разработчик ПО"
                , "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."))));
        organizationList.add(new Organization(
                new OrganizationLink("Alcatel", "http://www.alcatel.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(1997, 9, 1)
                , LocalDate.of(2005, 1, 1)
                , "Инженер по аппаратному и программному тестированию"
                , "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."))));
        firstResume.addSection(EXPERIENCE, new OrganizationSection(organizationList));
        List<Organization> studyList = new ArrayList<>();
        studyList.add(new Organization(new OrganizationLink("Coursera", "https://www.coursera.org/course/progfun")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2013, 3, 1)
                , LocalDate.of(2013, 5, 1)
                , "\"Functional Programming Principles in Scala\" by Martin Odersky", null))));
        studyList.add(new Organization(new OrganizationLink("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2011, 3, 1)
                , LocalDate.of(2011, 4, 1)
                , "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null))));
        studyList.add(new Organization(new OrganizationLink("Siemens AG", "http://www.siemens.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2005, 1, 1)
                , LocalDate.of(2005, 4, 1)
                , "3 месяца обучения мобильным IN сетям (Берлин)", null))));
        studyList.add(new Organization(new OrganizationLink("Alcatel", "http://www.alcatel.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(1997, 4, 1)
                , LocalDate.of(1998, 3, 1)
                , "6 месяцев обучения цифровым телефонным сетям (Москва)", null))));
        studyList.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/"
                , new Organization.Stages(1993, Month.SEPTEMBER, 1996, Month.JULY, "Аспирантура (программист С, С++)", null)
                , new Organization.Stages(1987, Month.SEPTEMBER, 1993, Month.JULY, "Инженер (программист Fortran, C)", null)));
        studyList.add(new Organization(new OrganizationLink("Yota", "https://www.yota.ru/")
                , new ArrayList<>(Collections.singleton(new Organization.Stages(LocalDate.of(1984, 9, 1)
                , LocalDate.of(1987, 1, 1)
                , "Закончил с отличием", null)))));
        firstResume.addSection(EDUCATION, new OrganizationSection(studyList));
        return firstResume;
     // return new Resume(UUID_1,"Григорий Кислин");
    }

    public static Resume getSecondResume() {
       Resume secondResume = new Resume(UUID_2, "Николай Жириновский");
        secondResume.addContact(MOBILE, "+7(999) 999-8888");
        secondResume.addContact(SKYPE, "nikolay.girinovsky");
        secondResume.addContact(MAIL, "ngirinovsky@yandex.ru");
        secondResume.addContact(LINKEDIN, "https://www.linkedin.com/in/ngirinovsky");
        secondResume.addContact(GITHUB, "https://github.com/ngirinovsky");
        secondResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/ngirinovsky");
        secondResume.addContact(HOME_PAGE, "http://ngirinovsky.ru/");
        secondResume.addSection(OBJECTIVE, new ContentSection("Уборщик"));
        secondResume.addSection(PERSONAL, new ContentSection("Идеальные полы, чистые стекла, быстрая работа, все, что вам нужно"));
        List<String> achievements = new ArrayList<>();
        achievements.add("101+ сертификатов по уборке");
        secondResume.addSection(ACHIEVEMENT, new ListSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("Швабра, тряпки, пена и все прибамбасы");
        secondResume.addSection(QUALIFICATION, new ListSection(qualifications));
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(
                new OrganizationLink("DvorProduction", "http://DvorProduction.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(1992, 10, 1)
                , NOW
                , "Генеральный директор клининга"
                , "Уборка территории"))));
        secondResume.addSection(EXPERIENCE, new OrganizationSection(organizationList));
        List<Organization> studyList = new ArrayList<>();
        studyList.add(new Organization(new OrganizationLink("Dvor", "https://www.dvor.com")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(1980, 1, 1)
                , LocalDate.of(1992, 1, 1)
                , "Cleaning school", null))));
        secondResume.addSection(EDUCATION, new OrganizationSection(studyList));
        return secondResume;
        //return new Resume(UUID_2,"Николай Жириновский");
    }

    public static Resume getThirdResume() {
        Resume thirdResume = new Resume(UUID_3, "Денис Жириновский");
        thirdResume.addContact(MOBILE, "+7(888) 888-8888");
        thirdResume.addContact(SKYPE, "denis.gyrinovsky");
        thirdResume.addContact(MAIL, "dgyrinovsky@yandex.ru");
        thirdResume.addContact(LINKEDIN, "https://www.linkedin.com/in/dgyrinovsky");
        thirdResume.addContact(GITHUB, "https://github.com/dgyrinovsky");
        thirdResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/dgyrinovsky");
        thirdResume.addContact(HOME_PAGE, "http://dgyrinovsky.ru/");
        thirdResume.addSection(OBJECTIVE, new ContentSection("Ведущий журналист Гретты Тунберг"));
        thirdResume.addSection(PERSONAL, new ContentSection("\"Везде плохая экология,отвечаю\""));
        List<String> achievements = new ArrayList<>();
        achievements.add("С 2016 года: Собираю коллекцию мемов от Гретты");
        thirdResume.addSection(ACHIEVEMENT, new ListSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("Купил зеркалку,я про!");
        thirdResume.addSection(QUALIFICATION, new ListSection(qualifications));
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(
                new OrganizationLink("Gretta love organization", "http://glo.com/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2013, 10, 1)
                , NOW
                , "Автор проекта."
                , "Самостоятельно научился делать скрины мемов с Греттой"))));
        thirdResume.addSection(EXPERIENCE, new OrganizationSection(organizationList));
        List<Organization> studyList = new ArrayList<>();
        studyList.add(new Organization(new OrganizationLink("NewTravelSchool", "https://www.NTS.com")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2013, 3, 1)
                , LocalDate.of(2013, 5, 1)
                , "\"6 классов уличной школы жизни\"", null))));
        thirdResume.addSection(EDUCATION, new OrganizationSection(studyList));
        return thirdResume;
       // return new Resume(UUID_3,"Денис Жириновский");
    }

    public static Resume getFourthResume() {
        Resume fourthResume = new Resume(UUID_4, "Владимир Жириновский");
        fourthResume.addContact(MOBILE, "+7(777) 777-7777");
        fourthResume.addContact(SKYPE, "vladimir.girinovsky");
        fourthResume.addContact(MAIL, "vladimir.girinovsky@yandex.ru");
        fourthResume.addContact(LINKEDIN, "https://www.linkedin.com/in/vgirinovsky");
        fourthResume.addContact(GITHUB, "https://github.com/vgirinovsky");
        fourthResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/vgirinovsky");
        fourthResume.addContact(HOME_PAGE, "http://vgirinovsky.ru/");
        fourthResume.addSection(OBJECTIVE, new ContentSection("ЛДПР рулит"));
        fourthResume.addSection(PERSONAL, new ContentSection("Тащу партию с 1993г"));
        List<String> achievements = new ArrayList<>();
        achievements.add("У всех все будет!");
        achievements.add("Особенно, у меня всё будет!");
        fourthResume.addSection(ACHIEVEMENT, new ListSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("ЛДПР");
        qualifications.add("ГОС-ДУМА");
        fourthResume.addSection(QUALIFICATION, new ListSection(qualifications));
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(
                new OrganizationLink("President standUp", "http://ps.ru/")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2013, 10, 1)
                , NOW
                , "Автор проекта."
                , "Балатировался,балатируюсь и буду балатироваться"))));
        fourthResume.addSection(EXPERIENCE, new OrganizationSection(organizationList));
        List<Organization> studyList = new ArrayList<>();
        studyList.add(new Organization(new OrganizationLink("PresidentSchoolProduction", "https://www.PresidentSchoolProduction.com")
                , Collections.singletonList(new Organization.Stages(LocalDate.of(2013, 3, 1)
                , LocalDate.of(2013, 5, 1)
                , "\"Как стать президентом с пиленок.Школа мастерства\"", null))));
        fourthResume.addSection(EDUCATION, new OrganizationSection(studyList));
        return fourthResume;
       // return new Resume(UUID_4,"Владимир Жириновский");
    }
}