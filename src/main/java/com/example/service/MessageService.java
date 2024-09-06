package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.azul.crs.client.Response;
import com.example.entity.Message;
import com.example.repository.AccountRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    //create message
    public ResponseEntity<Message> createMessage(Message message){
        if(message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            return ResponseEntity.status(400).build();
        }

        if(message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())){
            return ResponseEntity.status(400).build();
        }

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(200).body(message);
    }

    //get all messages
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.status(200).body(messages);
    }

    //get message by id
    public ResponseEntity<Optional<Message>> getMessageById(int messageId){
        Optional<Message> foundMessage = messageRepository.findById(messageId);
        if(foundMessage.isPresent()){
            return ResponseEntity.status(200).body(foundMessage);
        }
        return ResponseEntity.status(200).build();
    }

    //delete message by id
    public ResponseEntity<Integer> deleteMessageById(int messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return ResponseEntity.status(200).body(1);
        } else{
            return ResponseEntity.status(200).build();
        }
    }

    //update message by id

    public ResponseEntity<Integer> updateMessage(int messageId, String newMessageText){
        if(newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255){
            return ResponseEntity.status(400).build();
        }

        Optional<Message> foundMessage = messageRepository.findById(messageId);
        if(foundMessage.isPresent()){
            Message messageToUpdate = foundMessage.get();
            messageToUpdate.setMessageText(newMessageText);
            messageRepository.save(messageToUpdate);
            return ResponseEntity.status(200).body(1);
        } else{
            return ResponseEntity.status(400).build();
        }
    }

    //get all message from account id
    public ResponseEntity<List<Message>> getMessagesByAccountId(int account_id){
        List<Message> messages = messageRepository.findByPostedBy(account_id);
            
            return ResponseEntity.status(200).body(messages);
    }
}
