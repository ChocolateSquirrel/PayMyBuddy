package com.openclassrooms.paymybuddy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//	@GetMapping("/home")
//	public String home (Model model) {
//		model.addAttribute("home", "home");
//		return "home";
//	}

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
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
