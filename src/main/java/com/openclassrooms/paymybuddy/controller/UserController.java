package com.openclassrooms.paymybuddy.controller;

import java.util.HashMap;
import java.util.Map;

import com.openclassrooms.paymybuddy.commandobject.LogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.paymybuddy.commandobject.CreationForm;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView submitLogin(@ModelAttribute LogForm logForm) {
        RedirectView redirect = new RedirectView();
        User userLog = null;
        try {
            userLog = userService.logUser(logForm);
            redirect.setUrl("/userCreated");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            redirect.setUrl("/home");
        }
        return new ModelAndView(redirect);
    }

    @GetMapping("/sign_up")
    public ModelAndView showCreateUser() {
        String viewName = "createUser";
        return new ModelAndView(viewName, "creationForm", new CreationForm());
    }

    @PostMapping("/sign_up")
    public ModelAndView submitCreateUser(@ModelAttribute CreationForm creationForm) {
        userService.createUser(creationForm);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/userCreated");
        return new ModelAndView(redirect);
    }


}
