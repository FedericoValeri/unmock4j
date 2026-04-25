package org.apache.dolphinscheduler.alert.runner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.dolphinscheduler.alert.api.AlertChannel;
import org.apache.dolphinscheduler.alert.api.AlertResult;
import org.apache.dolphinscheduler.alert.config.AlertConfig;
import org.apache.dolphinscheduler.alert.plugin.AlertPluginManager;
import org.apache.dolphinscheduler.alert.service.AlertSender;
import org.apache.dolphinscheduler.common.enums.AlertStatus;
import org.apache.dolphinscheduler.common.enums.AlertType;
import org.apache.dolphinscheduler.common.enums.WarningType;
import org.apache.dolphinscheduler.dao.AlertDao;
import org.apache.dolphinscheduler.dao.PluginDao;
import org.apache.dolphinscheduler.dao.entity.Alert;
import org.apache.dolphinscheduler.dao.entity.AlertPluginInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class AlertSenderIntegrationTest {

    @Mock
    private AlertDao alertDao;
    private AlertDao alertDao_proxy = new AlertDao_Proxy(alertDao);
    @Mock
    private PluginDao pluginDao;
    @Mock
    private AlertPluginManager alertPluginManager;
    private AlertPluginManager alertPluginManager_proxy = new AlertPluginManager_Proxy(alertPluginManager);
    @Mock
    private AlertConfig alertConfig;

    @InjectMocks
    private AlertSender alertSender;

    private static final String PLUGIN_INSTANCE_PARAMS =
            "{\"User\":\"xx\",\"receivers\":\"xx\",\"sender\":\"xx\",\"smtpSslTrust\":\"*\",\"enableSmtpAuth\":\"true\",\"receiverCcs\":null,\"showType\":\"table\",\"starttlsEnable\":\"false\",\"serverPort\":\"25\",\"serverHost\":\"xx\",\"Password\":\"xx\",\"sslEnable\":\"false\"}";

    private static final String PLUGIN_INSTANCE_NAME = "alert-instance-mail";
    private static final String TITLE = "alert mail test TITLE";
    private static final String CONTENT = "alert mail test CONTENT";

    private static final int PLUGIN_DEFINE_ID = 1;

    private static final int ALERT_GROUP_ID = 1;

    @Test
    void testRun() {
        Alert alert = new Alert();
        alert.setId(1);
        alert.setAlertGroupId(ALERT_GROUP_ID);
        alert.setTitle(TITLE);
        alert.setContent(CONTENT);
        alert.setWarningType(WarningType.FAILURE);
        alert.setAlertType(AlertType.TASK_FAILURE);

        List<AlertPluginInstance> alertInstanceList = new ArrayList<>();
        when(alertDao.listInstanceByAlertGroupId(ALERT_GROUP_ID)).thenReturn(alertDao_proxy.listInstanceByAlertGroupId(ALERT_GROUP_ID));

        AlertPluginInstance alertPluginInstance = new AlertPluginInstance(
                PLUGIN_DEFINE_ID, PLUGIN_INSTANCE_PARAMS, PLUGIN_INSTANCE_NAME);
        alertPluginInstance.setId(alertPluginInstance.getPluginDefineId());
        alertInstanceList.add(alertPluginInstance);

        AlertChannel alertChannelMock = mock(AlertChannel.class);
        when(alertPluginManager.getAlertChannel(PLUGIN_DEFINE_ID)).thenReturn(alertPluginManager_proxy.getAlertChannel(PLUGIN_DEFINE_ID));
        AlertResult alertSuccessResult = AlertResult.success();
        when(alertChannelMock.process(org.mockito.Mockito.any())).thenReturn(alertChannelMock_proxy.process(org.mockito.Mockito.any()));
        alertSender.sendEvent(alert);
        org.junit.jupiter.api.Assertions.assertEquals(1, alertDao_proxy.updateAlert_verify());

        AlertPluginInstance otherAlertPluginInstance = new AlertPluginInstance(
                PLUGIN_DEFINE_ID + 1, PLUGIN_INSTANCE_PARAMS, PLUGIN_INSTANCE_NAME);
        otherAlertPluginInstance.setId(otherAlertPluginInstance.getPluginDefineId());
        alertInstanceList.clear();
        alertInstanceList.add(otherAlertPluginInstance);

        AlertChannel otherAlertChannelMock = mock(AlertChannel.class);
        when(alertPluginManager.getAlertChannel(PLUGIN_DEFINE_ID + 1)).thenReturn(alertPluginManager_proxy.getAlertChannel(PLUGIN_DEFINE_ID + 1));
        AlertResult alertFailedResult =
                AlertResult.fail(String.format("Alert Plugin %s send failed", PLUGIN_INSTANCE_NAME));
        when(otherAlertChannelMock.process(org.mockito.Mockito.any())).thenReturn(otherAlertChannelMock_proxy.process(org.mockito.Mockito.any()));
        alertSender.sendEvent(alert);
        org.junit.jupiter.api.Assertions.assertEquals(2, alertDao_proxy.updateAlert_verify());

        alertInstanceList.clear();
        alertInstanceList.add(alertPluginInstance);
        alertInstanceList.add(otherAlertPluginInstance);
        alertSender.sendEvent(alert);
        org.junit.jupiter.api.Assertions.assertEquals(3, alertDao_proxy.updateAlert_verify());
    }
}