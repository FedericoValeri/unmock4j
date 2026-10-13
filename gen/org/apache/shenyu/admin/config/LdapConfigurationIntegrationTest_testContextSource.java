package org.apache.shenyu.admin.config;

import org.apache.shenyu.admin.config.properties.LdapProperties;
import org.junit.jupiter.api.Test;
import org.springframework.ldap.core.support.LdapContextSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final final class LdapConfigurationIntegrationTest_testContextSource {

    private LdapConfiguration ldapConfiguration = new LdapConfiguration();

    @Test
    public void testContextSource() {
        LdapProperties ldapProp_proxy = new LdapProperties_Proxy(<--LdapProperties_REAL_CONFIGURED_INSTANCE-->);
        String ldapUrl = "ldap://192.168.0.10:389";
        String user = "test";
        String pass = "123";
        ldapProp_proxy.getUrl();
        ldapProp_proxy.getBindDn();
        ldapProp_proxy.getPassword();
        ldapProp_proxy.getConnectTimeout();
        ldapProp_proxy.getReadTimeout();
        LdapContextSource ldapContextSource = ldapConfiguration.contextSource(ldapProp_proxy);
        assertNotNull(ldapContextSource);
        assertThat(ldapContextSource.getUrls().length, is(1));
        assertEquals(ldapContextSource.getUrls()[0], ldapUrl);
        assertEquals(ldapContextSource.getUserDn(), user);
        assertEquals(ldapContextSource.getPassword(), pass);
    }
}