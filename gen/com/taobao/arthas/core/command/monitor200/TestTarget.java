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

public class TestTarget {
        public String echo(String value) {
            return value;
        }
    }