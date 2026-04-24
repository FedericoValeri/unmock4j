package org.apache.dolphinscheduler.alert.runner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.dolphinscheduler.alert.api.AlertChannel;
import org.apache.dolphinscheduler.alert.api.AlertResult;
import org.apache.dolphinscheduler.alert.config.AlertConfig;
import org.apache.dolphinscheduler.alert.plugin.AlertPluginManager;
import org.apache.dolphinscheduler.alert.service.AlertSender;
import org.apache.dolphinscheduler.common.enums.AlertStatus;
import org.apache.dolphinscheduler.common.enums.AlertType;
import org.apache.dolphinscheduler.common.enums.WarningType;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.AlertDao;
import org.apache.dolphinscheduler.dao.PluginDao;
import org.apache.dolphinscheduler.dao.entity.Alert;
import org.apache.dolphinscheduler.dao.entity.AlertPluginInstance;
import org.apache.dolphinscheduler.extract.alert.request.AlertSendResponse;
import org.apache.dolphinscheduler.spi.params.PluginParamsTransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class AlertSenderIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(AlertSenderIntegrationTest.class);

    @Mock
    private AlertDao alertDao;
    @Mock
    private PluginDao pluginDao;
    @Mock
    private AlertPluginManager alertPluginManager;
    @Mock
    private AlertConfig alertConfig;

    private AlertDao_Proxy alertDao_proxy = new AlertDao_Proxy(alertDao);
    private PluginDao_Proxy pluginDao_proxy = new PluginDao_Proxy(pluginDao);
    private AlertPluginManager_Proxy alertPluginManager_proxy = new AlertPluginManager_Proxy(alertPluginManager);
    private AlertConfig_Proxy alertConfig_proxy = new AlertConfig_Proxy(alertConfig);

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
        when(alertChannelMock.process(Mockito.any())).thenReturn(alertChannelMock_proxy.process(Mockito.any()));
        alertSender.sendEvent(alert);
        verify(alertDao).updateAlert(eq(AlertStatus.EXECUTION_SUCCESS), anyString(), anyInt());

        AlertPluginInstance otherAlertPluginInstance = new AlertPluginInstance(
                PLUGIN_DEFINE_ID + 1, PLUGIN_INSTANCE_PARAMS, PLUGIN_INSTANCE_NAME);
        otherAlertPluginInstance.setId(otherAlertPluginInstance.getPluginDefineId());
        alertInstanceList.clear();
        alertInstanceList.add(otherAlertPluginInstance);

        AlertChannel otherAlertChannelMock = mock(AlertChannel.class);
        when(alertPluginManager.getAlertChannel(PLUGIN_DEFINE_ID + 1)).thenReturn(alertPluginManager_proxy.getAlertChannel(PLUGIN_DEFINE_ID + 1));
        AlertResult alertFailedResult =
                AlertResult.fail(String.format("Alert Plugin %s send failed", PLUGIN_INSTANCE_NAME));
        when(otherAlertChannelMock.process(Mockito.any())).thenReturn(otherAlertChannelMock_proxy.process(Mockito.any()));
        alertSender.sendEvent(alert);
        verify(alertDao).updateAlert(eq(AlertStatus.EXECUTION_FAILURE), anyString(), anyInt());

        alertInstanceList.clear();
        alertInstanceList.add(alertPluginInstance);
        alertInstanceList.add(otherAlertPluginInstance);
        alertSender.sendEvent(alert);
        verify(alertDao).updateAlert(eq(AlertStatus.EXECUTION_PARTIAL_SUCCESS), anyString(), anyInt());

    }
}