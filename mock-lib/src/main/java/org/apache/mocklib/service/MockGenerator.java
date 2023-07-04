package org.apache.mocklib.service;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MockGenerator {
    private List<String> acceptedNames;
    protected static final Random random = new Random();

    protected MockGenerator(List<String> acceptedNames) {
        this.acceptedNames = acceptedNames;
    }

    protected boolean accept(String name) {
        return this.acceptedNames.contains(name);
    }

    public abstract Object generate(Map<String, Object> args);
}
