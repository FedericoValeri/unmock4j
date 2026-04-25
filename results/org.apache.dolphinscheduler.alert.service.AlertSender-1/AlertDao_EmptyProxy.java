package org.apache.dolphinscheduler.alert.runner;

import org.apache.dolphinscheduler.dao.AlertDao;

public class AlertDao_EmptyProxy extends AlertDao {

    protected final AlertDao alertDao;

    public AlertDao_EmptyProxy(AlertDao alertDao) {
        this.alertDao = alertDao;
    }

}
