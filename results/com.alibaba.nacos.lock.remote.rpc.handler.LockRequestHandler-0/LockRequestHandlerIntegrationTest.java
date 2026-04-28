package com.alibaba.nacos.lock.remote.rpc.handler;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.lock.common.LockConstants;
import com.alibaba.nacos.api.lock.model.LockInstance;
import com.alibaba.nacos.api.lock.remote.LockOperationEnum;
import com.alibaba.nacos.api.lock.remote.request.LockOperationRequest;
import com.alibaba.nacos.api.lock.remote.response.LockOperationResponse;
import com.alibaba.nacos.lock.service.LockOperationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LockRequestHandlerIntegrationTest {

    @Mock
    private LockOperationService lockOperationService;
    private LockOperationService lockOperationService_proxy = new LockOperationService_Proxy(lockOperationService);

    private LockRequestHandler lockRequestHandler;

    @Test
    public void testAcquireHandler() throws NacosException {
        lockRequestHandler = new LockRequestHandler(lockOperationService);

        LockInstance lockInstance = new LockInstance("key", 1L, LockConstants.NACOS_LOCK_TYPE);
        LockOperationRequest request = new LockOperationRequest();
        request.setLockInstance(lockInstance);
        request.setLockOperationEnum(LockOperationEnum.ACQUIRE);
        Mockito.when(lockOperationService.lock(lockInstance)).thenReturn(lockOperationService_proxy.lock(lockInstance));
        LockOperationResponse response = lockRequestHandler.handle(request, null);
        assertTrue((Boolean) response.getResult());
    }
}