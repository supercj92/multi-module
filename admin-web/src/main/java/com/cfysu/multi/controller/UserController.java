package com.cfysu.multi.controller;

import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import com.cfysu.multi.manager.UserManager;
import com.cfysu.multi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping("/listUser")
    public ModelAndView listUser(Page<User> page){
        Page<User> userPage = userManager.queryForPage(new UserQuery(), page);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("page", userPage);
        return modelAndView;
    }
}
