package com.urise.webapp.web;

import com.urise.webapp.model.Resume;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        // response.setHeader("Content-Type", "text/html; charset=UTF-8");
        //String name = request.getParameter("name");
        //response.getWriter().write(name == null ? "Hello resumes!" : "Hello " + name + '!');
        Writer writer = response.getWriter();
        Resume resume = new Resume("Gretta Tunberg");
        writer.write(
                "<html>" +
                        "<head>" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                        "<link rel=\"stylesheet\" href=\"css/style.css\">" +
                        "<title>Table of resume</title>" +
                        "</head>" +
                        "<body>" +
                        "<section>" +
                        "<table border=\"1\" >" +
                        "<tr>" +
                        "<td> uuid</td> " +
                        "<td> full_name</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>" + resume.getUuid() + "</td> " +
                        "<td>" + resume.getFullName() + "</td> </tr>\n");
    }
}