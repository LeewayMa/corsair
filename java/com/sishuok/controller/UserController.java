package com.sishuok.controller;  
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sishuok.entity.User;
import com.sishuok.service.UserService;
  
/** 
 * <p>User: Zhang Kaitao 
 * <p>Date: 13-12-22 
 * <p>Version: 1.0 
 */  
//@EnableAutoConfiguration  
//@RestController
@RequestMapping("/user")  
public class UserController {  
//	private static Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
    @RequestMapping("/{id}")  
    public User view(@PathVariable("id") Long id) {  
        User user = new User();  
        user.setId(id);  
        user.setName("zhang"); 
        
//        LOG.info(" LOG.info zhang :" + id);
//        LOG.warn("LOG.warn zhang :" + id);
        return user;  
    }  

    @RequestMapping("/save")  
    public User save(String name,Integer age) {  
        User user = new User();  
//        user.setId(id);  
//        user.setName("zhang"); 
        userService.create(name, age);
        user = this.userService.get((long)userService.getAllUsers());
//        LOG.info(" LOG.info zhang :" + id);
//        LOG.warn("LOG.warn zhang :" + id);
        return user;  
    }  
    
    @RequestMapping("/getAllUsers")  
    public Integer getAllUsers() {  
        
        return userService.getAllUsers();  
    }  
    
    //public static void main(String[] args) {  
    //    SpringApplication.run(UserController.class);  
    //}  
  
}  