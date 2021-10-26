package com.emily.emilyweb.controller;


import com.emily.emilyservice.service.HomeService;
import com.emily.emilyservice.service.RedisOperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private HomeService homeService;

    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    @Autowired
    private RedisOperService redisOperService;

    @ResponseBody
    @RequestMapping("/home")
    public String home(){
        logger.info("show home start...");
        return homeService.home();
    }

    @ResponseBody
    @RequestMapping("/getRedis")
    public String getRedis(){
        return redisOperService.getString();
    }
}
