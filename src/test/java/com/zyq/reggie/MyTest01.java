package com.zyq.reggie;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MyTest01 {
//    @Autowired
//    private RedisTemplate redisTemplate;
//    这样取值为null，
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//    这样可以正常取值


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test01(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
//        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        System.out.println(111);
        System.out.println(222);

    }
}
