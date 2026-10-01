package com.taobao.arthas.core.command.monitor200;

import com.taobao.arthas.core.shell.handlers.Handler;
import com.taobao.arthas.core.shell.term.Tty;

public class Tty_EmptyProxy implements Tty {

    protected final Tty dependency;

    public Tty_EmptyProxy(Tty dependency) {
        this.dependency = dependency;
    }

    @Override
    public String type() {
        return dependency.type();
    }

    @Override
    public Tty write(String data) {
        return dependency.write(data);
    }

    @Override
    public int width() {
        return dependency.width();
    }

    @Override
    public int height() {
        return dependency.height();
    }

    @Override
    public Tty resizehandler(Handler<Void> handler) {
        return dependency.resizehandler(handler);
    }

    @Override
    public Tty stdinHandler(Handler<String> handler) {
        return dependency.stdinHandler(handler);
    }

}
