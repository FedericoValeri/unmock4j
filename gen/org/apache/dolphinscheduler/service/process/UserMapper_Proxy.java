package org.apache.dolphinscheduler.service.process;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;

public class UserMapper_Proxy extends UserMapper_EmptyProxy {

    private final UserMapper dependency;

    public UserMapper_Proxy(UserMapper dependency) {
        super(dependency);
        this.dependency = dependency;
    }

    @Override
    public User selectById(int id) {
        User result = dependency.selectById(id);
        assertEquals(123, result.getId());
        return result;
    }
}