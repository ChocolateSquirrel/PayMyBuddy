package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.commandobject.CreateUserForm;
import com.openclassrooms.paymybuddy.commandobject.ExternalTransactionForm;
import com.openclassrooms.paymybuddy.model.PMBAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ExternalTransactionRepository;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest()
public class ApplicationPMBTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExternalTransactionRepository externalTransactionRepository;

    @Autowired
    private PMBAccountRepository pmbAccountRepository;

    @Test
    public void createAccountTest() throws Exception {
        CreateUserForm form = new CreateUserForm();
        form.setFirstName("utilisateur");
        form.setLastName("UTILISATEUR");
        form.setMail("utilisateur@gmail.com");
        form.setPassword("mdp");
        form.setConfirm("mdp");
        int sizeRepo = userRepository.findAll().size();

        userService.createUser(form);

        assertThat(userRepository.findAll().size()).isEqualTo(sizeRepo+1);
    }

    @Test
    public void createExternalTransactionTest(){
        User user1 = new User();
        user1.setMail("utilisateur1@gmail.com");
        PMBAccount user1Account = new PMBAccount();
        user1Account.setBalance(2000);
        user1Account.setUser(user1);
        user1.setPmbAccount(user1Account);
        userRepository.save(user1);
        pmbAccountRepository.save(user1Account);

        User user2 = new User();
        user2.setMail("utilisateur2@gmail.com");
        PMBAccount user2Account = new PMBAccount();
        user2Account.setBalance(0);
        user2Account.setUser(user2);
        user2.setPmbAccount(user2Account);
        userRepository.save(user2);
        pmbAccountRepository.save(user2Account);

        ExternalTransactionForm form = new ExternalTransactionForm();
        form.setAmount(1000);
        form.setDescription("Anniversaire");
        form.setMailOfCrediter("utilisateur2@gmail.com");

        transactionService.createExternalTransaction(user1, form);

        PMBAccount account1 = userRepository.findByMail("utilisateur1@gmail.com").get().getPmbAccount();
        PMBAccount account2 = userRepository.findByMail("utilisateur2@gmail.com").get().getPmbAccount();
        //assertThat(externalTransactionRepository.findByCreditAccount(account2).size()).isEqualTo(1);
        //assertThat(externalTransactionRepository.findByPmbAccountDebit(account1).size()).isEqualTo(1);
    }
}
