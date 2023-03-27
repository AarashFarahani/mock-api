package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BooleanMockGenerator extends MockGenerator {
    public final static List<String> ACCEPTED_NAMES = List.of("bool", "boolean", "Boolean");

    public BooleanMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var random = new Random();
        return random.nextBoolean();
    }
}
