package com.urise.webapp.web;

import com.urise.webapp.ConfigHeroku;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;


public class ResumeServletHeroku extends ResumeServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = ConfigHeroku.get().getStorage();
    }
}