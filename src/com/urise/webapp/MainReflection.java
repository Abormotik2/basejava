package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = new Resume("Vasya");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        field.get(resume);
        System.out.println(resume);
        field.set(resume, "new_uuid");

        try {
          Method method = Object.class.getMethod("toString");
          Object toStringResume = method.invoke(resume);
            System.out.println(toStringResume);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //TODO : invoke r.toString via reflection
    }
}