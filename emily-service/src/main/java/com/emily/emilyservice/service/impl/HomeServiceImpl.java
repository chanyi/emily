package com.emily.emilyservice.service.impl;

import com.emily.emilyservice.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    public String home() {
        return "hello i am emily";
    }
}
