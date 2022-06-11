package com.emily.emilyweb.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/listener")
public class ListenerController {

    @ResponseBody
    @RequestMapping("getServletContextCache")
    public String getServletContextCache(HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        String result = (String) servletContext.getAttribute("");
        return result;
    }

}
