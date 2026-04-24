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



class AlertDao_Proxy extends AlertDao_EmptyProxy {
    private int updateAlertCounter = 0;

    AlertDao_Proxy(AlertDao dependency) {
        super(dependency);
    }

    @Override
    public int updateAlert(org.apache.dolphinscheduler.common.enums.AlertStatus alertStatus, String log, int id) {
        updateAlertCounter++;
        int result = dependency.updateAlert(alertStatus, log, id);
        assertNotNull(result);
        return result;
    }

    public int updateAlert_verify() {
        return updateAlertCounter;
    }

    @Override
    public List<AlertPluginInstance> listInstanceByAlertGroupId(int alertGroupId) {
        List<AlertPluginInstance> result = dependency.listInstanceByAlertGroupId(alertGroupId);
        assertNotNull(result);
        return result;
    }
}