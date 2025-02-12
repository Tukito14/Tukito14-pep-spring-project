package com.example.controller;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        try {
            Account newAccount = accountService.registerAccount(account);
            return ResponseEntity.status(200).body(newAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogIn(@RequestBody Account account) throws Exception{
        try {
            Account logInAttempt = accountService.logIn(account.getUsername(),account.getPassword());
            return ResponseEntity.status(200).body(logInAttempt);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<?> submitNewPost(@RequestBody Message message) throws Exception{
        try {
            Message newPost = messageService.submitPost(message);
            return ResponseEntity.status(200).body(newPost);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return ResponseEntity.status(200).body(messages);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId){
        try{
            Optional<Message> messageFromId = messageService.getMessageFromId(messageId);
            return ResponseEntity.status(200).body(messageFromId.get());
        } catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId){
        try {
            messageService.deleteMessageFromId(messageId);
            return ResponseEntity.status(200).body(1);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(200).body("");
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message){
        try {
            messageService.updateMessageFromId(messageId, message.getMessageText());
            return ResponseEntity.status(200).body(1);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("");
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByAccount(@PathVariable Integer accountId){
        try {
            List<Message> messages = messageService.getMessagesFromAccountId(accountId);
            return ResponseEntity.status(200).body(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(200).body("");
        }
    }
}
