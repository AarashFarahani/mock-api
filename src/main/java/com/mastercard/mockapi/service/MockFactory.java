package com.mastercard.mockapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MockFactory {
    private final List<MockGenerator> mockGenerators;

    public MockGenerator create(String name) {
        return this.mockGenerators.stream().filter(a-> a.getName().equalsIgnoreCase(name))
                .findFirst().orElseThrow();
    }
}
