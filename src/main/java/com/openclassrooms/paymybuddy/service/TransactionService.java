package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.ExternalTransactionForm;
import com.openclassrooms.paymybuddy.commandobject.InternalTransactionForm;
import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.ExternalTransactionRepository;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PMBAccountRepository pmbAccountRepository;
    private final ExternalTransactionRepository externalTransactionRepository;
    private final PMBAccountService pmbAccountService;

    public TransactionService(UserRepository userRepository, BankAccountRepository bankAccountRepository, PMBAccountRepository pmbAccountRepository, ExternalTransactionRepository externalTransactionRepository, PMBAccountService pmbAccountService) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pmbAccountRepository = pmbAccountRepository;
        this.externalTransactionRepository = externalTransactionRepository;
        this.pmbAccountService = pmbAccountService;
    }

    @Transactional
    public void fundOrWithdrawPMBAccount(User user, InternalTransactionForm form) throws Exception {
        PMBAccount userPMBAccount = user.getPmbAccount();
        double amount = form.getAmount();
        String signe = form.getSigne();

        InternalTransaction trans = new InternalTransaction();
        trans.setDate(LocalDate.now());
        BankAccount bankAccount = new BankAccount();
        bankAccountRepository.save(bankAccount);
        trans.setBankAccount(bankAccount);//pour l'instant c'est un compte vide
        trans.setPmbAccount(userPMBAccount);
        trans.setAmount(amount);
        if (signe.equals("+")) {
            trans.setSigne(Signe.PLUS);
            pmbAccountService.fund(userPMBAccount, amount);
        } else {
            trans.setSigne(Signe.MINUS);
            pmbAccountService.withdraw(userPMBAccount, amount);
        }
        userPMBAccount.getIntDebitTransactions().add(trans);
        userRepository.save(user);
    }

    @Transactional
    public void createExternalTransaction(User user, ExternalTransactionForm form) throws Exception {
        User creditUser = userRepository.findByMail(form.getMailOfCrediter()).get();
        PMBAccount debitAccount = user.getPmbAccount();
        PMBAccount creditAccount = creditUser.getPmbAccount();

        ExternalTransaction extTrans = new ExternalTransaction();
        extTrans.setDebitAccount(debitAccount);
        extTrans.setCreditAccount(creditAccount);
        extTrans.setDate(LocalDate.now());
        extTrans.setDescription(form.getDescription());
        extTrans.setAmount(form.getAmount());

        pmbAccountService.withdraw(debitAccount, form.getAmount());
        pmbAccountService.fund(creditAccount, form.getAmount());

        externalTransactionRepository.save(extTrans);
    }
}
