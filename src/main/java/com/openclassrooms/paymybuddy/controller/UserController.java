package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.commandobject.AddConnectionForm;
import com.openclassrooms.paymybuddy.commandobject.ExternalTransactionForm;
import com.openclassrooms.paymybuddy.commandobject.InternalTransactionForm;
import com.openclassrooms.paymybuddy.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.commandobject.CreateUserForm;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

import java.util.Optional;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;

    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/homePage")
    public String showHomePage(){
        return "homePage";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/logoff")
    public String viewLogOff() {
        SecurityContextHolder.clearContext();
        return "homePage";
    }

    @GetMapping("/sign_up")
    public ModelAndView showCreateUserPage() {
        String viewName = "createUserPage";
        return new ModelAndView(viewName, "createUserForm", new CreateUserForm());
    }

    @PostMapping("/sign_up")
    public ModelAndView submitCreateUser(@ModelAttribute CreateUserForm creationForm, Model model) {
        String errorMessage = new String();
        try {
            userService.createUser(creationForm);
        } catch (Exception e) {
            log.error(e.getMessage());
            errorMessage = e.getMessage();
            model.addAttribute("error", errorMessage);
            return new ModelAndView("createUserPage", "createUserForm", creationForm);
        }
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());

        return new ModelAndView("/home", "internTransForm", new InternalTransactionForm());
    }

    @GetMapping("/home")
    public ModelAndView showHome(Model model){
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        return new ModelAndView("home", "internTransForm", new InternalTransactionForm());
    }

    @PostMapping("/home")
    public ModelAndView submitHome(@ModelAttribute InternalTransactionForm form, Model model){
        log.info("Request: POST /home");
        Optional<User> connectedUser = userService.getConnectedUser();
        String errorMessage = new String();
        model.addAttribute("currentUser", connectedUser.get());
        try {
            transactionService.fundOrWithdrawPMBAccount(connectedUser.get(), form);
        } catch (Exception e) {
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }
        model.addAttribute("error", errorMessage);
        log.info("Response: " + form.getSigne() + form.getAmount() + " on " + connectedUser.get().getFirstName() + connectedUser.get().getLastName() + " PMBAccount");
        return new ModelAndView("home", "internTransForm", new InternalTransactionForm());
    }


    @GetMapping("/connection")
    public ModelAndView showAddConnectionPage(Model model) {
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        String viewName = "contact";
        model.addAttribute("currentUser", connectedUser.get());
        return new ModelAndView(viewName, "addConnectionForm", new AddConnectionForm());
    }

    @PostMapping("/connection")
    public ModelAndView addContact(@ModelAttribute AddConnectionForm addConnectionForm, Model model){
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        userService.connect2Users(connectedUser.get(), addConnectionForm);
        return new ModelAndView("contact", "addConnectionForm", new AddConnectionForm());
    }

    @GetMapping("/transfer")
    public ModelAndView showTransferPage(Model model){
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        return new ModelAndView("transfer", "externTransForm", new ExternalTransactionForm());
    }

    @PostMapping("/transfer")
    public ModelAndView payExternalTransaction(@ModelAttribute ExternalTransactionForm form, Model model){
        log.info("Request: POST /transfer");
        String errorMessage = new String();
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        try {
            transactionService.createExternalTransaction(connectedUser.get(), form);
        } catch (Exception e) {
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }
        model.addAttribute("error", errorMessage);
        return new ModelAndView("transfer", "externTransForm", new ExternalTransactionForm());
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model){
        log.info("Request: GET /profile");
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        return "profile";
    }


}
