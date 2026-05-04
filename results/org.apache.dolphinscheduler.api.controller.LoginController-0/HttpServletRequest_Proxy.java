package org.apache.dolphinscheduler.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpServletRequest_Proxy extends HttpServletRequest_EmptyProxy {

    private final HttpServletRequest dependency;

    public HttpServletRequest_Proxy(HttpServletRequest dependency) {
        super(dependency);
        this.dependency = dependency;
    }

    @Override
    public HttpSession getSession() {
        return dependency.getSession();
    }
}