package com.taobao.arthas.core.command.monitor200;

import com.taobao.arthas.core.advisor.AdviceListener;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.shell.cli.CliToken;
import com.taobao.arthas.core.shell.command.CommandProcess;
import com.taobao.arthas.core.shell.handlers.Handler;
import com.taobao.arthas.core.shell.session.Session;
import com.taobao.middleware.cli.CommandLine;
import java.lang.instrument.ClassFileTransformer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandProcess_EmptyProxy implements CommandProcess {

    protected final CommandProcess dependency;

    public CommandProcess_EmptyProxy(CommandProcess dependency) {
        this.dependency = dependency;
    }

    @Override
    public void end(int status) {
        dependency.end(status);
    }

    @Override
    public void end(int status, String message) {
        dependency.end(status, message);
    }

    @Override
    public void end() {
        dependency.end();
    }

    @Override
    public List<String> args() {
        return dependency.args();
    }

    @Override
    public void register(AdviceListener listener, ClassFileTransformer transformer) {
        dependency.register(listener, transformer);
    }

    @Override
    public CommandProcess write(String data) {
        return dependency.write(data);
    }

    @Override
    public void suspend() {
        dependency.suspend();
    }

    @Override
    public void resume() {
        dependency.resume();
    }

    @Override
    public Session session() {
        return dependency.session();
    }

    @Override
    public void unregister() {
        dependency.unregister();
    }

    @Override
    public boolean isRunning() {
        return dependency.isRunning();
    }

    @Override
    public AtomicInteger times() {
        return dependency.times();
    }

    @Override
    public CommandLine commandLine() {
        return dependency.commandLine();
    }

    @Override
    public CommandProcess interruptHandler(Handler<Void> handler) {
        return dependency.interruptHandler(handler);
    }

    @Override
    public CommandProcess suspendHandler(Handler<Void> handler) {
        return dependency.suspendHandler(handler);
    }

    @Override
    public CommandProcess foregroundHandler(Handler<Void> handler) {
        return dependency.foregroundHandler(handler);
    }

    @Override
    public void appendResult(ResultModel result) {
        dependency.appendResult(result);
    }

    @Override
    public CommandProcess resizehandler(Handler<Void> handler) {
        return dependency.resizehandler(handler);
    }

    @Override
    public CommandProcess stdinHandler(Handler<String> handler) {
        return dependency.stdinHandler(handler);
    }

    @Override
    public boolean isForeground() {
        return dependency.isForeground();
    }

    @Override
    public CommandProcess resumeHandler(Handler<Void> handler) {
        return dependency.resumeHandler(handler);
    }

    @Override
    public CommandProcess backgroundHandler(Handler<Void> handler) {
        return dependency.backgroundHandler(handler);
    }

    @Override
    public String cacheLocation() {
        return dependency.cacheLocation();
    }

    @Override
    public CommandProcess endHandler(Handler<Void> handler) {
        return dependency.endHandler(handler);
    }

    @Override
    public void echoTips(String tips) {
        dependency.echoTips(tips);
    }

    @Override
    public List<CliToken> argsTokens() {
        return dependency.argsTokens();
    }

    @Override
    public String type() {
        return dependency.type();
    }

    @Override
    public int width() {
        return dependency.width();
    }

    @Override
    public int height() {
        return dependency.height();
    }

}
