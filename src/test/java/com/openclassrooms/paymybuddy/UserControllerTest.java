package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.PMBAccountService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = PayMyBuddyApplicationTests.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private UserRepository userRepository;
    private PMBAccountRepository pmbAccountRepository;
    private BankAccountRepository bankAccountRepository;
    private PasswordEncoder passwordEncoder;
    private PMBAccountService pmbAccountService;


}
