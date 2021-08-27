package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.commandobject.AddConnectionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.commandobject.CreateUserForm;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

 /*   @GetMapping("/homePage")
    public ModelAndView viewHomePage(){
        String viewName = "homePage";
        return new ModelAndView(viewName);
    }*/

    @GetMapping("/home")
    public String showHome(){
        return "navBarMenu";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/sign_up")
    public ModelAndView showCreateUserPage() {
        String viewName = "createUserPage";
        return new ModelAndView(viewName, "createUserForm", new CreateUserForm());
    }

    @PostMapping("/sign_up")
    public ModelAndView submitCreateUser(@ModelAttribute CreateUserForm creationForm) {
        userService.createUser(creationForm);
        return new ModelAndView("/profilePage");
    }

    @GetMapping("/connection")
    public ModelAndView showAddConnectionPage() {
        String viewName = "addConnectionPage";
        return new ModelAndView(viewName, "addConnectionForm", new AddConnectionForm());
    }

    @PostMapping("/connection")
    public ModelAndView addContact(@ModelAttribute AddConnectionForm addConnectionForm){
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        userService.connect2Users(connectedUser.get(), addConnectionForm);
        return new ModelAndView("connectionAddedPage");
    }


}
