package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }


    //Account Controller
    //register
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        return accountService.register(account);
        // return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    //login
    @PostMapping("login")
        public ResponseEntity<Account> login(@RequestBody Account account){
            return accountService.login(account.getUsername(), account.getPassword());
            // return ResponseEntity.status(201).body(account);
        }

    //Message Controller
    //create message
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        return messageService.createMessage(message);
    }

    //get all messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return messageService.getAllMessages();
    }

    //get message by id
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable int messageId){
        return messageService.getMessageById(messageId);
    }

    //delete message by id
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        return messageService.deleteMessageById(messageId);
    }

    //update message by id
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message newMessage){
        return messageService.updateMessage(messageId, newMessage.getMessageText());
    }

    //get all message by account id
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int account_id){
        return messageService.getMessagesByAccountId(account_id);
    }
}
