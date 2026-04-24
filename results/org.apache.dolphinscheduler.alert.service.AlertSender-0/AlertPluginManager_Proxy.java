package org.apache.dolphinscheduler.alert.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.dolphinscheduler.alert.api.AlertChannel;
import org.apache.dolphinscheduler.alert.api.AlertResult;
import org.apache.dolphinscheduler.alert.config.AlertConfig;
import org.apache.dolphinscheduler.alert.plugin.AlertPluginManager;
import org.apache.dolphinscheduler.dao.AlertDao;
import org.apache.dolphinscheduler.dao.PluginDao;
import org.apache.dolphinscheduler.dao.entity.Alert;
import org.apache.dolphinscheduler.dao.entity.AlertPluginInstance;

import java.util.List;
import java.util.Optional;



class AlertPluginManager_Proxy extends AlertPluginManager_EmptyProxy {
    AlertPluginManager_Proxy(AlertPluginManager dependency) {
        super(dependency);
    }

    @Override
    public Optional<AlertChannel> getAlertChannel(int id) {
        Optional<AlertChannel> result = dependency.getAlertChannel(id);
        assertNotNull(result);
        return result;
    }
}