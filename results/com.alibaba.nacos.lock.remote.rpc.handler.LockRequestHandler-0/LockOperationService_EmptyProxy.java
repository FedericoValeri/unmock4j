package com.alibaba.nacos.lock.remote.rpc.handler;

import com.alibaba.nacos.api.lock.model.LockInstance;
import com.alibaba.nacos.lock.service.LockOperationService;

public class LockOperationService_EmptyProxy implements LockOperationService {

    protected final LockOperationService lockOperationService;

    public LockOperationService_EmptyProxy(LockOperationService lockOperationService) {
        this.lockOperationService = lockOperationService;
    }

    @Override
    public Boolean lock(LockInstance lockInstance) {
        return lockOperationService.lock(lockInstance);
    }

    @Override
    public Boolean unLock(LockInstance lockInstance) {
        return lockOperationService.unLock(lockInstance);
    }

}
