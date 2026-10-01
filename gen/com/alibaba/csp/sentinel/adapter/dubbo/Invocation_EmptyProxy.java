package com.alibaba.csp.sentinel.adapter.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import java.util.Map;

public class Invocation_EmptyProxy implements Invocation {

    protected final Invocation dependency;

    public Invocation_EmptyProxy(Invocation dependency) {
        this.dependency = dependency;
    }

    @Override
    public Object get(Object arg0) {
        return dependency.get(arg0);
    }

    @Override
    public Object put(Object arg0, Object arg1) {
        return dependency.put(arg0, arg1);
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return dependency.getParameterTypes();
    }

    @Override
    public Map<Object, Object> getAttributes() {
        return dependency.getAttributes();
    }

    @Override
    public String getMethodName() {
        return dependency.getMethodName();
    }

    @Override
    public Object[] getArguments() {
        return dependency.getArguments();
    }

    @Override
    public String getAttachment(String arg0, String arg1) {
        return dependency.getAttachment(arg0, arg1);
    }

    @Override
    public String getAttachment(String arg0) {
        return dependency.getAttachment(arg0);
    }

    @Override
    public Invoker<?> getInvoker() {
        return dependency.getInvoker();
    }

    @Override
    public Map<String, String> getAttachments() {
        return dependency.getAttachments();
    }

}
