package com.spring.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ResourceController {

    @RequestMapping(method = RequestMethod.GET, path = "/private")
    @ResponseBody
    public String saySecuredHello() {
        return "Secure Hello!";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/public")
    @ResponseBody
    public String sayHello() {
        return "Hello User!";
    }

}
