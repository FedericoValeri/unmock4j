package org.apache.dolphinscheduler.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        HttpSession result = dependency.getSession();
        assertNotNull(result);
        return result;
    }
}