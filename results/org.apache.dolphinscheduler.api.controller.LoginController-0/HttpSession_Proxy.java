package org.apache.dolphinscheduler.api.controller;

import static org.junit.jupiter.api.Assertions.assertNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSession_Proxy extends HttpSession_EmptyProxy {

    private final HttpSession dependency;
    private int setAttributeCounter = 0;

    public HttpSession_Proxy(HttpSession dependency) {
        super(dependency);
        this.dependency = dependency;
    }

    @Override
    public void setAttribute(String name, Object value) {
        setAttributeCounter++;
        dependency.setAttribute(name, value);
    }

    public int setAttribute_verify() {
        return setAttributeCounter;
    }

    @Override
    public Object getAttribute(String name) {
        Object result = dependency.getAttribute(name);
        return result;
    }
}