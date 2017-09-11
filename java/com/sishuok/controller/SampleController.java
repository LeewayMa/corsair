package com.sishuok.controller;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
 
//@RestController
@EnableAutoConfiguration
public class SampleController {
 
    @RequestMapping("/home")
    String home() {
        return "Hello World!";
    }
 
}