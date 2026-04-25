package org.apache.dolphinscheduler.alert.runner;

import org.apache.dolphinscheduler.alert.api.AlertChannel;

public class AlertChannel_EmptyProxy implements AlertChannel {

    protected final AlertChannel alertChannel;

    public AlertChannel_EmptyProxy(AlertChannel alertChannel) {
        this.alertChannel = alertChannel;
    }

    @Override
    public AlertResult process(AlertInfo arg0) {
        return alertChannel.process(arg0);
    }

}
