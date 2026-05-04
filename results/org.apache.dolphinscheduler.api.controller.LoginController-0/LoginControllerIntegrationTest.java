package org.apache.dolphinscheduler.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.dolphinscheduler.api.configuration.ApiConfig;
import org.apache.dolphinscheduler.api.configuration.OAuth2Configuration;
import org.apache.dolphinscheduler.api.configuration.OAuth2Configuration.OAuth2ClientProperties;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.security.Authenticator;
import org.apache.dolphinscheduler.api.security.impl.AbstractSsoAuthenticator;
import org.apache.dolphinscheduler.api.service.SessionService;
import org.apache.dolphinscheduler.api.service.UsersService;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.common.enums.UserType;
import org.apache.dolphinscheduler.common.model.OkHttpResponse;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.common.utils.OkHttpUtils;
import org.apache.dolphinscheduler.dao.entity.Session;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.repository.SessionDao;

import org.apache.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class LoginControllerIntegrationTest extends AbstractControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginControllerIntegrationTest.class);

    @Autowired
    private SessionDao sessionDao;

    private static class DummySsoAuthenticator extends AbstractSsoAuthenticator {

        @Override
        public User login(@org.springframework.lang.NonNull String state, String code) {
            return null;
        }

        @Override
        public String getSignInUrl(String state) {
            return "http://sso.example.com/auth?state=" + state;
        }
    }

    @Test
    public void testSsoLogin_WithAbstractSsoAuthenticator_ReturnsSignInUrl() {
        DummySsoAuthenticator dummy = new DummySsoAuthenticator();
        SessionService sessionService = mock(SessionService.class);
        SessionService sessionService_proxy = new SessionService_Proxy(<--SessionService_REAL_CONFIGURED_INSTANCE-->);
        UsersService usersService = mock(UsersService.class);
        UsersService usersService_proxy = new UsersService_Proxy(<--UsersService_REAL_CONFIGURED_INSTANCE-->);
        ApiConfig apiConfig = mock(ApiConfig.class);
        ApiConfig apiConfig_proxy = new ApiConfig_Proxy(<--ApiConfig_REAL_CONFIGURED_INSTANCE-->);
        LoginController controller =
                new LoginController(sessionService_proxy, dummy, usersService_proxy, Optional.empty(), Optional.empty(),
                        apiConfig_proxy);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletRequest_Proxy request_proxy = new HttpServletRequest_Proxy(request);
        HttpSession session = mock(HttpSession.class);
        HttpSession_Proxy session_proxy = new HttpSession_Proxy(session);
        request_proxy.getSession();
        session_proxy.getAttribute(Constants.SSO_LOGIN_USER_STATE);

        Result result = controller.ssoLogin(request_proxy);
        Assertions.assertEquals(Status.SUCCESS.getCode(), result.getCode());
        Assertions.assertNotNull(result.getData());
        assertEquals(1, session_proxy.setAttribute_verify());
    }
}