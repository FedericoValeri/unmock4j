package com.taobao.arthas.core.command.monitor200;

import com.taobao.arthas.core.advisor.Advice;
import com.taobao.arthas.core.advisor.ArthasMethod;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.TimeFragmentVO;
import com.taobao.arthas.core.command.model.TimeTunnelModel;
import com.taobao.arthas.core.shell.command.CommandProcess;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandProcess_Proxy extends CommandProcess_EmptyProxy {

    private int appendResultCounter = 0;

    public CommandProcess_Proxy(CommandProcess dependency) {
        super(dependency);
    }

    @Override
    public AtomicInteger times() {
        AtomicInteger result = dependency.times();
        Assert.assertNotNull(result);
        return result;
    }

    @Override
    public void appendResult(ResultModel result) {
        appendResultCounter++;
        dependency.appendResult(result);
    }

    public int appendResult_verify() {
        return appendResultCounter;
    }
}