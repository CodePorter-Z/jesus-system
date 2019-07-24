package com.jesus.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ComputeController {

    @Autowired
    EurekaRegistration eurekaRegistration;

    @RequestMapping(value = "/add/{a}/{b}" ,method = RequestMethod.GET)
    public Integer add(@PathVariable Integer a, @PathVariable Integer b) {
        Integer r = a + b;
        log.info("/add, host:" + eurekaRegistration.getHost()+ ", service_id:" + eurekaRegistration.getServiceId() + ", result:" + r);
        return r;
    }
}
