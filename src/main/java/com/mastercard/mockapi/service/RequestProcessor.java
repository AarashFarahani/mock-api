package com.mastercard.mockapi.service;

import com.mastercard.mockapi.model.Person;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequestProcessor {
    public Object process(Object request) {
        return new Person(UUID.randomUUID().toString(), "", "");
    }
}
