package com.mastercard.mockapi.service;

import java.util.Map;

public abstract class MockGenerator {
    private String name;

    protected MockGenerator(String name) {
        this.name = name;
    }

    protected String getName() {
        return this.name;
    }

    public abstract Object generate(Map<String, Object> args);
}
