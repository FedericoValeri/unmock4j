package org.apache.dolphinscheduler.alert.runner;

import org.apache.dolphinscheduler.alert.api.AlertChannel;
import org.apache.dolphinscheduler.alert.api.AlertResult;
import org.apache.dolphinscheduler.alert.config.AlertConfig;
import org.apache.dolphinscheduler.alert.plugin.AlertPluginManager;
import org.apache.dolphinscheduler.common.enums.AlertStatus;
import org.apache.dolphinscheduler.dao.AlertDao;
import org.apache.dolphinscheduler.dao.PluginDao;
import org.apache.dolphinscheduler.dao.entity.AlertPluginInstance;

import java.util.List;
import java.util.Optional;

public class AlertPluginManager_Proxy extends AlertPluginManager_EmptyProxy {

    public AlertPluginManager_Proxy(AlertPluginManager alertPluginManager) {
        super(alertPluginManager);
    }

    @Override
    public Optional<AlertChannel> getAlertChannel(int id) {
        Optional<AlertChannel> result = dependency.getAlertChannel(id);
        return result;
    }
}