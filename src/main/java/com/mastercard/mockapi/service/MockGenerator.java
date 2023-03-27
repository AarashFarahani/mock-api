package com.mastercard.mockapi.service;

import java.util.List;
import java.util.Map;

public abstract class MockGenerator {
    private List<String> acceptedNames;

    protected MockGenerator(List<String> acceptedNames) {
        this.acceptedNames = acceptedNames;
    }

    protected boolean accept(String name) {
        return this.acceptedNames.contains(name);
    }

    public abstract Object generate(Map<String, Object> args);
}
