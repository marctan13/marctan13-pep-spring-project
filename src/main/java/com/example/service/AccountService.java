package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //register
    public ResponseEntity<Account> register(Account account){

        //username validation
        if(account.getUsername() == null || account.getUsername().isBlank()){
            return ResponseEntity.status(400).build();
        }

        //password validation
        if(account.getPassword() == null || account.getPassword().length() < 4){
            return ResponseEntity.status(400).build();
        }

        //existing user
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if(existingAccount != null){
            return ResponseEntity.status(409).build();
        }

        Account newAccount = accountRepository.save(account);
        return ResponseEntity.status(200).body(newAccount);
    }

    //login
    public ResponseEntity<Account> login(String username, String password){
        //validate input
        if(username == null || username.isBlank() || password == null || password.isBlank()){
            return ResponseEntity.status(401).build();
        }

        Account account = accountRepository.findByUsernameAndPassword(username, password);

        if(account == null){
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.status(200).body(account);
    }
}
