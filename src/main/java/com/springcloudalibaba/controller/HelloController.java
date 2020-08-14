package com.springcloudalibaba.controller;

import com.springcloudalibaba.model.UrlBlocker;
import com.springcloudalibaba.service.HelloService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private UrlBlocker urlBlocker;

    @GetMapping("/hello")
    public Object hello() {
        urlBlocker.setCode(0);
        urlBlocker.setMsg(helloService.doSomething("hello" + new Date()));
        return urlBlocker;
    }

    @GetMapping("/hello2")
    public Object hello2() {
        urlBlocker.setCode(0);
        urlBlocker.setMsg(helloService.doSomething2("hello" + new Date()));
        return urlBlocker;
    }


    @GetMapping("/hello3")
    @SentinelResource(value = "hotSpotLimit", blockHandler = "HotParamBlocked")
    public Object hello3(@RequestParam("uid") Long uid) {
        urlBlocker.setCode(0);
        urlBlocker.setMsg("热点限流" + uid);
        return urlBlocker;
    }

    public Object HotParamBlocked(@RequestParam("uid") Long uid, BlockException e) {
        urlBlocker.setCode(999);
        urlBlocker.setMsg("热点" + uid + "被限流了");
        return urlBlocker;
    }

}
