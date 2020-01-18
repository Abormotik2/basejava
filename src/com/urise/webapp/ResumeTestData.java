package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;

import java.util.Map;

import static com.urise.webapp.model.ContactType.*;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        Map<ContactType, String> contactInfo = resume.getContacts();
        contactInfo.put(MOBILE, "+7(921) 855-0482");
        contactInfo.put(SKYPE,  "grigory.kislin");
        contactInfo.put(MAIL, "gkislin@yandex.ru");
        contactInfo.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contactInfo.put(GITHUB,"https://github.com/gkislin");
        contactInfo.put(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contactInfo.put(HOME_PAGE, "http://gkislin.ru/");
        List<>
        System.out.println(resume);
    }
}
