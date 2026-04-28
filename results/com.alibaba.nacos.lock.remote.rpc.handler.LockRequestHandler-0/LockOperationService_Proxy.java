package com.alibaba.nacos.lock.remote.rpc.handler;

import com.alibaba.nacos.api.lock.model.LockInstance;
import com.alibaba.nacos.lock.service.LockOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LockOperationService_Proxy implements LockOperationService {

    private final LockOperationService dependency;

    public LockOperationService_Proxy(LockOperationService dependency) {
        this.dependency = dependency;
    }

    @Override
    public Boolean lock(LockInstance lockInstance) {
        Boolean result = dependency.lock(lockInstance);
        Assertions.assertTrue(result);
        return result;
    }

    @Override
    public Boolean unLock(LockInstance lockInstance) {
        Boolean result = dependency.unLock(lockInstance);
        return result;
    }
}