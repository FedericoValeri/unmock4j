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

public class AlertDao_Proxy extends AlertDao_EmptyProxy {

    private int updateAlertCounter = 0;

    public AlertDao_Proxy(AlertDao alertDao) {
        super(alertDao);
    }

    @Override
    public List<AlertPluginInstance> listInstanceByAlertGroupId(int alertGroupId) {
        List<AlertPluginInstance> result = dependency.listInstanceByAlertGroupId(alertGroupId);
        return result;
    }

    @Override
    public int updateAlert(AlertStatus alertStatus, String log, int id) {
        updateAlertCounter++;
        int result = dependency.updateAlert(alertStatus, log, id);
        return result;
    }

    public int updateAlert_verify() {
        return updateAlertCounter;
    }
}