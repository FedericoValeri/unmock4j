package org.apache.dolphinscheduler.alert.runner;

import org.apache.dolphinscheduler.alert.plugin.AlertPluginManager;

public class AlertPluginManager_EmptyProxy extends AlertPluginManager {

    protected final AlertPluginManager alertPluginManager;

    public AlertPluginManager_EmptyProxy(AlertPluginManager alertPluginManager) {
        this.alertPluginManager = alertPluginManager;
    }

}
