package com.emily.emilyservice.service.impl;

import com.emily.emilyservice.service.RedisOperService;
import com.emily.emilyservice.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisOperServiceImpl implements RedisOperService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getString() {
        return redisUtil.getString("test");
    }
}
