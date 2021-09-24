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

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PMBAccountRepository pmbAccountRepository;
    private final ExternalTransactionRepository externalTransactionRepository;
    private final PMBAccountService pmbAccountService;
    private final BankAccountService bankAccountService;

    public TransactionService(UserRepository userRepository, BankAccountRepository bankAccountRepository, PMBAccountRepository pmbAccountRepository, ExternalTransactionRepository externalTransactionRepository, PMBAccountService pmbAccountService, BankAccountService bankAccountService) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pmbAccountRepository = pmbAccountRepository;
        this.externalTransactionRepository = externalTransactionRepository;
        this.pmbAccountService = pmbAccountService;
        this.bankAccountService = bankAccountService;
    }

    /**
     * Make money transfer between PMBAccount and BankAccount (ie internal transaction)
     * @param user : user owning accounts (PMB and Bank).
     *             PMBAccount is recover from the user
     *             BankAccount is recover from the form
     * @param form
     * @throws Exception
     */
    @Transactional
    public void fundOrWithdrawPMBAccount(User user, InternalTransactionForm form) throws Exception {
        // Recover parameters
        PMBAccount userPMBAccount = user.getPmbAccount();
        String iban = form.getBankAccountIban();
        BankAccount userBankAccount = bankAccountService.findByIban(iban);
        double amount = form.getAmount();
        String signe = form.getSigne();

        // Create internal transaction
        InternalTransaction trans = new InternalTransaction();
        trans.setDate(LocalDate.now());
        trans.setBankAccount(userBankAccount);
        trans.setPmbAccount(userPMBAccount);
        trans.setAmount(amount);
        // Fund money (+PMBAccount, -bankAccount)
        if (signe.equals("+")) {
            trans.setSigne(Signe.PLUS);
            pmbAccountService.fund(userPMBAccount, amount);
            bankAccountService.withdraw(userBankAccount, amount);
        }
        // Withdraw money (-PMBAccount, +bankAccount)
        else {
            trans.setSigne(Signe.MINUS);
            pmbAccountService.withdraw(userPMBAccount, amount);
            bankAccountService.fund(userBankAccount, amount);
        }

        // Save
        userPMBAccount.getIntTransactions().add(trans);
        userRepository.save(user);
    }

    /**
     * Make money transfer between two PMBAccounts (ie external transaction)
     * @param user : user who send money: debit
     * @param form : contains parameters of the transaction (date, description...) and especially the PMBAccount which receive money: credit
     * @throws Exception
     */
    @Transactional
    public void createExternalTransaction(User user, ExternalTransactionForm form) throws Exception {
        // Make sure form is full
        if (form.getMailOfCrediter().equals("")){
            throw new Exception("You need to select a connection");
        }
        if (form.getAmount() <= 0.0){
            throw new Exception("You cannot transfer 0 or less money");
        }

        // Recover parameters
        User creditUser = userRepository.findByMail(form.getMailOfCrediter()).get();
        PMBAccount debitAccount = user.getPmbAccount();
        PMBAccount creditAccount = creditUser.getPmbAccount();

        // Create external transaction
        ExternalTransaction extTrans = new ExternalTransaction();
        extTrans.setDebitAccount(debitAccount);
        extTrans.setCreditAccount(creditAccount);
        extTrans.setDate(LocalDate.now());
        extTrans.setDescription(form.getDescription());
        extTrans.setAmount(form.getAmount());
        extTrans.setCommission((double) Math.round(form.getAmount()*0.05*100)/100);

        // Transfer money
        pmbAccountService.withdrawWithCommission(debitAccount, form.getAmount());
        pmbAccountService.fund(creditAccount, form.getAmount());

        // Save
        externalTransactionRepository.save(extTrans);
    }
}
