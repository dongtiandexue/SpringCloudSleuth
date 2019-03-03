package com.dtdx.sleuth.userserver.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

/**
 * @ClassName UserController
 * @Description 用户查看视频服务
 * @Author huawei
 * @Date 2019/3/3 15:47
 * @Version 1.0
 **/
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("views")
    @HystrixCommand(fallbackMethod = "viewsFail")
    public Object views(@RequestParam("name") String name){

        log.info("user views video name:{}",name);

        String result = restTemplate.getForObject("http://video-server/video-search/searchByName?name=" + name, String.class);
        return result;
    }

    public Object viewsFail(String name){
        return "no such video";
    }
}
