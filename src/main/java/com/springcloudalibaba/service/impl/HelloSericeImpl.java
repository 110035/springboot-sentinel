package com.springcloudalibaba.service.impl;

import com.springcloudalibaba.service.HelloService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * TODO
 *
 * @author Ace Lee
 * @version 1.0
 * @date 2019/9/23 14:54
 **/
@Slf4j
@Service
public class HelloSericeImpl implements HelloService {


    @SentinelResource(value = "doSomething", blockHandler = "exceptionHandler")
    @Override
    public String doSomething(String s) {

        return "限流测试:"+s;
    }


    @SentinelResource(value = "doSomething2", fallback = "fallbackHandler")
    @Override
    public String doSomething2(String s) {
        Random r1 = new Random();
        int seed = r1.nextInt(10);
        if(seed < 5){
            throw new RuntimeException("发生异常");

        }
        return "熔断测试:"+s;
    }

    // 限流与阻塞处理
    public String exceptionHandler(String str, BlockException ex) {
        log.error("blockHandler：" + str, ex);
        return "被限流了";
    }

    // 熔断与降级处理
    public String fallbackHandler(String str) {

        log.error("fallbackHandler：" + str);
        return "被熔断了";

    }
}
