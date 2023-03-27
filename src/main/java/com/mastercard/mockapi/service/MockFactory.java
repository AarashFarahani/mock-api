package com.mastercard.mockapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MockFactory {
    private final List<MockGenerator> mockGenerators;

    public Optional<MockGenerator> create(String name) {
        return this.mockGenerators.stream()
                .filter(a-> a.accept(name))
                .findFirst();
    }
}
