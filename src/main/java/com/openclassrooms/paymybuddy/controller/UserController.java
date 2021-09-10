package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.commandobject.AddConnectionForm;
import com.openclassrooms.paymybuddy.commandobject.ExternalTransactionForm;
import com.openclassrooms.paymybuddy.commandobject.InternalTransactionForm;
import com.openclassrooms.paymybuddy.service.TransactionService;
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

    @GetMapping("/sign_up")
    public ModelAndView showCreateUserPage() {
        String viewName = "createUserPage";
        return new ModelAndView(viewName, "createUserForm", new CreateUserForm());
    }

    @PostMapping("/sign_up")
    public ModelAndView submitCreateUser(@ModelAttribute CreateUserForm creationForm) {
        userService.createUser(creationForm);
        return new ModelAndView("/home");
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
        Optional<User> connectedUser = userService.getConnectedUser();
        model.addAttribute("currentUser", connectedUser.get());
        transactionService.fundOrWithdrawPMBAccount(connectedUser.get(), form);
        return new ModelAndView("home", "internTransForm", new InternalTransactionForm());
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

    @GetMapping("/transfer")
    public ModelAndView showTransferPage(Model model){
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        return new ModelAndView("transfer", "externTransForm", new ExternalTransactionForm());
    }

    @PostMapping("/transfer")
    public ModelAndView payExternalTransaction(@ModelAttribute ExternalTransactionForm form, Model model){
        Optional<User> connectedUser = userService.getConnectedUser();
        if (!connectedUser.isPresent()) throw new IllegalArgumentException("No user connected");
        model.addAttribute("currentUser", connectedUser.get());
        transactionService.createExternalTransaction(connectedUser.get(), form);
        return new ModelAndView("transfer", "externTransForm", new ExternalTransactionForm());
    }


}
