package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) throws Exception {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException();
        }
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            throw new Exception();
        }
        return accountRepository.save(account);
    }

    public Account logIn(String username, String password) throws Exception{
        Optional<Account> optAccount = accountRepository.findByUsername(username);
        if(optAccount.isEmpty()){
            throw new IllegalArgumentException();
        }

        Account account = optAccount.get();

        if(!(account.getPassword().equals(password))){
            throw new IllegalArgumentException();
        }
        return account;
    }
}