package com.example.project3.Service;

import com.example.project3.API.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Integer id){
        Account account=accountRepository.findAccountById(id);
        if(account==null){
            throw new ApiException("Account not found");
        }
        return account;

    }

    public void addAccount(Integer customerId, Account account){
        Customer customer=customerRepository.findCustomerById(customerId);
        if(customer == null){
            throw  new ApiException("Customer not found");
        }
        account.setCustomer(customer);
        account.setIsActive(false); // By default false
        accountRepository.save(account);
    }

    public void updateAccount(Integer id, Account updatedAccount){
        Account account = accountRepository.findAccountById(id);
        if(account == null){
            throw  new ApiException("Account not found");
        }
        account.setAccountNumber(updatedAccount.getAccountNumber());
        account.setBalance(updatedAccount.getBalance());
        account.setIsActive(updatedAccount.getIsActive());
        accountRepository.save(account);
    }

    public void deleteAccount(Integer id){
        Account account = accountRepository.findAccountById(id);
        if(account == null){
            throw  new ApiException("Account not found");
        }
        accountRepository.delete(account);
    }

    public void createAccount(Integer userId, Account account) {
        User user = authRepository.findUserById(userId);
        if (user == null || !user.getRole().equals("CUSTOMER")) {
            throw new ApiException("Only customers can create accounts");
        }

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) {
            throw new ApiException("Customer profile not found");
        }
        account.setCustomer(customer);
        account.setIsActive(false);//defaultValue
        accountRepository.save(account);
    }

    // Activate an account
    public void activateAccount(Integer userId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found");
        }
//نتحقق ان الحساب يعود للمستخدمuserId
        if (!account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("NO permission to activate this account");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    public Account getAccountDetails(Integer userId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found");
        }
//نتحقق اذا الحساب مو ملك المستخدمuserId
        if (!account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("No permission to view this account");
        }
        return account;
    }

    //List users "coustomer" account
    public List<Account> getAccountsByUserId(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null || !user.getRole().equals("CUSTOMER")) {
            throw new ApiException("Only customers can view their accounts");
        }

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) {
            throw new ApiException("Customer profile not found");
        }

        return accountRepository.findAllByCustomer(customer);
    }

    //Deposit
    public void deposit(Integer userId, Integer accountId, double amount) {
        if (amount <= 0) {
            throw new ApiException("Deposit amount must be positive");
        }
        Account account = accountRepository.findAccountById(accountId);
        if (account == null || !account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("Account not found or no permission");
        }
        if (!account.getIsActive()) {
            throw new ApiException("Can not deposit to inactive account");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    // Withdraw money
    public void withdraw(Integer userId, Integer accountId, double amount) {
        if (amount <= 0) {
            throw new ApiException("Withdraw amount must be positive");
        }

        Account account = accountRepository.findAccountById(accountId);
        if (account == null || !account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("Account not found or no permission");
        }

        if (!account.getIsActive()) {
            throw new ApiException("Can not withdraw from inactive account");
        }
        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer(Integer userId, Integer fromAccountId, Integer toAccountId, double amount) {
        if (amount <= 0) {
            throw new ApiException("Transfer amount must be positive");
        }
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (fromAccount == null || !fromAccount.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException(" account not found or no have permission");
        }
        if (toAccount == null) {
            throw new ApiException(" account not found");
        }
        if (!fromAccount.getIsActive() || !toAccount.getIsActive()) {
            throw new ApiException("accounts must be active");
        }
        if (fromAccount.getBalance() < amount) {
            throw new ApiException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

//Block Account
    public void blockAccount(Integer userId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null || !account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("Account not found or no permission");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }




}


