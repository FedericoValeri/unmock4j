package org.apache.shenyu.admin.config;

import org.apache.shenyu.admin.config.properties.LdapProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LdapProperties_Proxy extends LdapProperties_EmptyProxy {

    public LdapProperties_Proxy(final LdapProperties ldapProperties) {
        super(ldapProperties);
    }

    @Override
    public String getUrl() {
        String result = dependency.getUrl();
        assertEquals("ldap://192.168.0.10:389", result);
        return result;
    }

    @Override
    public String getBindDn() {
        String result = dependency.getBindDn();
        assertEquals("test", result);
        return result;
    }

    @Override
    public String getPassword() {
        String result = dependency.getPassword();
        assertEquals("123", result);
        return result;
    }

    @Override
    public Integer getConnectTimeout() {
        Integer result = dependency.getConnectTimeout();
        assertEquals(5000, result);
        return result;
    }

    @Override
    public Integer getReadTimeout() {
        Integer result = dependency.getReadTimeout();
        assertEquals(10000, result);
        return result;
    }
}