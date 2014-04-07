package com.custardcoding.skipbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Custard
 */
@Controller
public class MvcController {
    
    @RequestMapping(value = "/home")
    public String startGame() {
        return "home";
    }
}
