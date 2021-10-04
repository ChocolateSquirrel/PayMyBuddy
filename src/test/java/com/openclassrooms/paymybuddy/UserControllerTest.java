package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.model.PMBAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.PMBAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = PayMyBuddyApplicationTests.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Mock
    private UserRepository userRepository;

    @Autowired
    @Mock
    private PMBAccountRepository pmbAccountRepository;


    //Tests for sign_up
    @Test
    public void createAccountTest(){
        User userToCreate = new User();
        userToCreate.setFirstName("utilisateur1");
        userToCreate.setLastName("UTILISATEUR1");
        userToCreate.setMail("utilisateur1@gmail.com");
        userToCreate.setPassword("mdp");
        userToCreate.setPmbAccount(new PMBAccount());
        when(userRepository.findAll().size()).thenReturn(1);

        mockMvc.perform(post("/sign_up"));

        assertEquals(2, userRepository.findAll().size());
    }
}
