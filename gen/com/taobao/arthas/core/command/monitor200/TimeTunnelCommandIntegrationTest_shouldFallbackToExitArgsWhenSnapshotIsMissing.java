package com.taobao.arthas.core.command.monitor200;

import com.taobao.arthas.core.advisor.ArthasMethod;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.TimeFragmentVO;
import com.taobao.arthas.core.command.model.TimeTunnelModel;
import com.taobao.arthas.core.shell.command.CommandProcess;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.concurrent.atomic.AtomicInteger;

public class TimeTunnelCommandIntegrationTest_shouldFallbackToExitArgsWhenSnapshotIsMissing {

    public static class TestTarget {
        public String echo(String value) {
            return value;
        }
    }

    @Test
    public void shouldFallbackToExitArgsWhenSnapshotIsMissing() throws Throwable {
        TimeTunnelCommand command = new TimeTunnelCommand();
        CommandProcess_Proxy process_proxy = new CommandProcess_Proxy(<--CommandProcess_REAL_CONFIGURED_INSTANCE-->);
        AtomicInteger times = new AtomicInteger();
        process_proxy.times();

        TimeTunnelAdviceListener listener = new TimeTunnelAdviceListener(command, process_proxy, false);
        Object[] args = new Object[] { "input" };
        listener.afterReturning(TestTarget.class.getClassLoader(), TestTarget.class,
                new ArthasMethod(TestTarget.class, "echo", "(Ljava/lang/String;)Ljava/lang/String;"),
                new TestTarget(), args, "ok");

        ArgumentCaptor<ResultModel> resultCaptor = ArgumentCaptor.forClass(ResultModel.class);
        Assert.assertEquals(1, process_proxy.appendResult_verify());

        TimeTunnelModel timeTunnelModel = (TimeTunnelModel) resultCaptor.getValue();
        Assert.assertNotNull(timeTunnelModel.getTimeFragmentList());
        Assert.assertEquals(1, timeTunnelModel.getTimeFragmentList().size());

        TimeFragmentVO timeFragmentVO = timeTunnelModel.getTimeFragmentList().get(0);
        Assert.assertNotNull(timeFragmentVO.getParams());
        Assert.assertEquals(1, timeFragmentVO.getParams().length);
        Assert.assertEquals("input", timeFragmentVO.getParams()[0].getObject());
    }
}