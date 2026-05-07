package org.apache.dolphinscheduler.api.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponse_EmptyProxy implements HttpServletResponse {

    protected final HttpServletResponse httpServletResponse;

    public HttpServletResponse_EmptyProxy(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public String getHeader(String arg0) {
        return httpServletResponse.getHeader(arg0);
    }

    @Override
    public Collection<String> getHeaders(String arg0) {
        return httpServletResponse.getHeaders(arg0);
    }

    @Override
    public void addHeader(String arg0, String arg1) {
        httpServletResponse.addHeader(arg0, arg1);
    }

    @Override
    public void setHeader(String arg0, String arg1) {
        httpServletResponse.setHeader(arg0, arg1);
    }

    @Override
    public Collection<String> getHeaderNames() {
        return httpServletResponse.getHeaderNames();
    }

    @Override
    public int getStatus() {
        return httpServletResponse.getStatus();
    }

    @Override
    public void addCookie(Cookie arg0) {
        httpServletResponse.addCookie(arg0);
    }

    @Override
    public void sendError(int arg0, String arg1) throws IOException {
        httpServletResponse.sendError(arg0, arg1);
    }

    @Override
    public void sendError(int arg0) throws IOException {
        httpServletResponse.sendError(arg0);
    }

    @Override
    public String encodeUrl(String arg0) {
        return httpServletResponse.encodeUrl(arg0);
    }

    @Override
    public String encodeURL(String arg0) {
        return httpServletResponse.encodeURL(arg0);
    }

    @Override
    public void setStatus(int arg0) {
        httpServletResponse.setStatus(arg0);
    }

    @Override
    public void setStatus(int arg0, String arg1) {
        httpServletResponse.setStatus(arg0, arg1);
    }

    @Override
    public void addIntHeader(String arg0, int arg1) {
        httpServletResponse.addIntHeader(arg0, arg1);
    }

    @Override
    public String encodeRedirectUrl(String arg0) {
        return httpServletResponse.encodeRedirectUrl(arg0);
    }

    @Override
    public boolean containsHeader(String arg0) {
        return httpServletResponse.containsHeader(arg0);
    }

    @Override
    public void sendRedirect(String arg0) throws IOException {
        httpServletResponse.sendRedirect(arg0);
    }

    @Override
    public void setDateHeader(String arg0, long arg1) {
        httpServletResponse.setDateHeader(arg0, arg1);
    }

    @Override
    public void addDateHeader(String arg0, long arg1) {
        httpServletResponse.addDateHeader(arg0, arg1);
    }

    @Override
    public String encodeRedirectURL(String arg0) {
        return httpServletResponse.encodeRedirectURL(arg0);
    }

    @Override
    public void setIntHeader(String arg0, int arg1) {
        httpServletResponse.setIntHeader(arg0, arg1);
    }

    @Override
    public void reset() {
        httpServletResponse.reset();
    }

    @Override
    public void flushBuffer() throws IOException {
        httpServletResponse.flushBuffer();
    }

    @Override
    public Locale getLocale() {
        return httpServletResponse.getLocale();
    }

    @Override
    public void setLocale(Locale arg0) {
        httpServletResponse.setLocale(arg0);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return httpServletResponse.getOutputStream();
    }

    @Override
    public String getContentType() {
        return httpServletResponse.getContentType();
    }

    @Override
    public void setContentType(String arg0) {
        httpServletResponse.setContentType(arg0);
    }

    @Override
    public void setContentLength(int arg0) {
        httpServletResponse.setContentLength(arg0);
    }

    @Override
    public void setCharacterEncoding(String arg0) {
        httpServletResponse.setCharacterEncoding(arg0);
    }

    @Override
    public String getCharacterEncoding() {
        return httpServletResponse.getCharacterEncoding();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return httpServletResponse.getWriter();
    }

    @Override
    public void setBufferSize(int arg0) {
        httpServletResponse.setBufferSize(arg0);
    }

    @Override
    public void setContentLengthLong(long arg0) {
        httpServletResponse.setContentLengthLong(arg0);
    }

    @Override
    public boolean isCommitted() {
        return httpServletResponse.isCommitted();
    }

    @Override
    public void resetBuffer() {
        httpServletResponse.resetBuffer();
    }

    @Override
    public int getBufferSize() {
        return httpServletResponse.getBufferSize();
    }

}
