package org.apache.shenyu.admin.config;

import org.apache.shenyu.admin.config.properties.LdapProperties;

public class LdapProperties_EmptyProxy extends LdapProperties {

    protected final LdapProperties dependency;

    public LdapProperties_EmptyProxy(LdapProperties dependency) {
        this.dependency = dependency;
    }

}
