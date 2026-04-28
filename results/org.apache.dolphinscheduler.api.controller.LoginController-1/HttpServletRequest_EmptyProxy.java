package org.apache.dolphinscheduler.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class HttpServletRequest_EmptyProxy implements HttpServletRequest {

    protected final HttpServletRequest httpServletRequest;

    public HttpServletRequest_EmptyProxy(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Override
    public String getHeader(String arg0) {
        return httpServletRequest.getHeader(arg0);
    }

    @Override
    public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
        return httpServletRequest.authenticate(arg0);
    }

    @Override
    public Enumeration<String> getHeaders(String arg0) {
        return httpServletRequest.getHeaders(arg0);
    }

    @Override
    public HttpSession getSession() {
        return httpServletRequest.getSession();
    }

    @Override
    public HttpSession getSession(boolean arg0) {
        return httpServletRequest.getSession(arg0);
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
        return httpServletRequest.upgrade(arg0);
    }

    @Override
    public void logout() throws ServletException {
        httpServletRequest.logout();
    }

    @Override
    public void login(String arg0, String arg1) throws ServletException {
        httpServletRequest.login(arg0, arg1);
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return httpServletRequest.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return httpServletRequest.isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return httpServletRequest.isRequestedSessionIdFromURL();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return httpServletRequest.getParts();
    }

    @Override
    public Cookie[] getCookies() {
        return httpServletRequest.getCookies();
    }

    @Override
    public Part getPart(String arg0) throws IOException, ServletException {
        return httpServletRequest.getPart(arg0);
    }

    @Override
    public String getAuthType() {
        return httpServletRequest.getAuthType();
    }

    @Override
    public String getRequestURI() {
        return httpServletRequest.getRequestURI();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return httpServletRequest.isRequestedSessionIdValid();
    }

    @Override
    public String getServletPath() {
        return httpServletRequest.getServletPath();
    }

    @Override
    public String changeSessionId() {
        return httpServletRequest.changeSessionId();
    }

    @Override
    public String getContextPath() {
        return httpServletRequest.getContextPath();
    }

    @Override
    public String getQueryString() {
        return httpServletRequest.getQueryString();
    }

    @Override
    public boolean isUserInRole(String arg0) {
        return httpServletRequest.isUserInRole(arg0);
    }

    @Override
    public Principal getUserPrincipal() {
        return httpServletRequest.getUserPrincipal();
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return httpServletRequest.getHeaderNames();
    }

    @Override
    public long getDateHeader(String arg0) {
        return httpServletRequest.getDateHeader(arg0);
    }

    @Override
    public String getRemoteUser() {
        return httpServletRequest.getRemoteUser();
    }

    @Override
    public int getIntHeader(String arg0) {
        return httpServletRequest.getIntHeader(arg0);
    }

    @Override
    public String getRequestedSessionId() {
        return httpServletRequest.getRequestedSessionId();
    }

    @Override
    public String getPathTranslated() {
        return httpServletRequest.getPathTranslated();
    }

    @Override
    public StringBuffer getRequestURL() {
        return httpServletRequest.getRequestURL();
    }

    @Override
    public String getPathInfo() {
        return httpServletRequest.getPathInfo();
    }

    @Override
    public String getScheme() {
        return httpServletRequest.getScheme();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    @Override
    public String getProtocol() {
        return httpServletRequest.getProtocol();
    }

    @Override
    public void setAttribute(String arg0, Object arg1) {
        httpServletRequest.setAttribute(arg0, arg1);
    }

    @Override
    public Object getAttribute(String arg0) {
        return httpServletRequest.getAttribute(arg0);
    }

    @Override
    public Locale getLocale() {
        return httpServletRequest.getLocale();
    }

    @Override
    public int getContentLength() {
        return httpServletRequest.getContentLength();
    }

    @Override
    public String getRealPath(String arg0) {
        return httpServletRequest.getRealPath(arg0);
    }

    @Override
    public int getLocalPort() {
        return httpServletRequest.getLocalPort();
    }

    @Override
    public long getContentLengthLong() {
        return httpServletRequest.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return httpServletRequest.getContentType();
    }

    @Override
    public void removeAttribute(String arg0) {
        httpServletRequest.removeAttribute(arg0);
    }

    @Override
    public String getParameter(String arg0) {
        return httpServletRequest.getParameter(arg0);
    }

    @Override
    public boolean isSecure() {
        return httpServletRequest.isSecure();
    }

    @Override
    public String getLocalName() {
        return httpServletRequest.getLocalName();
    }

    @Override
    public String getServerName() {
        return httpServletRequest.getServerName();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return httpServletRequest.getReader();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return httpServletRequest.getLocales();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return httpServletRequest.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
        return httpServletRequest.startAsync(arg0, arg1);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return httpServletRequest.getAttributeNames();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return httpServletRequest.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String arg0) {
        return httpServletRequest.getParameterValues(arg0);
    }

    @Override
    public int getServerPort() {
        return httpServletRequest.getServerPort();
    }

    @Override
    public String getRemoteAddr() {
        return httpServletRequest.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return httpServletRequest.getRemoteHost();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String arg0) {
        return httpServletRequest.getRequestDispatcher(arg0);
    }

    @Override
    public int getRemotePort() {
        return httpServletRequest.getRemotePort();
    }

    @Override
    public ServletContext getServletContext() {
        return httpServletRequest.getServletContext();
    }

    @Override
    public boolean isAsyncStarted() {
        return httpServletRequest.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return httpServletRequest.isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return httpServletRequest.getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return httpServletRequest.getDispatcherType();
    }

    @Override
    public String getCharacterEncoding() {
        return httpServletRequest.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
        httpServletRequest.setCharacterEncoding(arg0);
    }

    @Override
    public String getLocalAddr() {
        return httpServletRequest.getLocalAddr();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return httpServletRequest.getParameterMap();
    }

}
