package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class NumberGenerator<T> extends MockGenerator {
    protected final Random random;

    protected NumberGenerator(List<String> acceptedNames) {
        super(acceptedNames);
        this.random = new Random();
    }

    protected Object min(Map<String, Object> args, T defaultValue) {
        return args.getOrDefault("min", defaultValue);
    }

    protected Object max(Map<String, Object> args, T defaultValue) {
        return args.getOrDefault("max", defaultValue);
    }
}
