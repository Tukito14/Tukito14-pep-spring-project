package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message submitPost(Message message) throws IllegalArgumentException{
        if(message.getMessageText() == null || message.getMessageText().isBlank()){
            throw new IllegalArgumentException();
        }
        if(message.getMessageText().length() > 255){
            throw new IllegalArgumentException();
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException();
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageFromId(Integer messageId){
        return messageRepository.findById(messageId);
    }

    public void deleteMessageFromId(Integer messageId) throws IllegalArgumentException{
        Optional<Message> copy = messageRepository.findById(messageId);
        if(copy.isEmpty()){
            throw new IllegalArgumentException();
        }
        messageRepository.deleteById(messageId);
    }

    public void updateMessageFromId(Integer messageId, String messageText) throws IllegalArgumentException{
        Optional<Message> copy = messageRepository.findById(messageId);
        if(copy.isEmpty() || messageText.length() > 255 || messageText.isEmpty()){
            throw new IllegalArgumentException();
        }
        Message message = copy.get();
        message.setMessageText(messageText);
        messageRepository.save(message);
    }

    public List<Message> getMessagesFromAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
