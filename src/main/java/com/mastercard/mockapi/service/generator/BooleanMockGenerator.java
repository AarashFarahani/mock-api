package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class BooleanMockGenerator extends MockGenerator {
    private final static List<String> ACCEPTED_NAMES = List.of(boolean.class.getName(), Boolean.class.getName());
    private final Random random;

    public BooleanMockGenerator() {
        super(ACCEPTED_NAMES);
        this.random = new Random();
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return this.random.nextBoolean();
    }
}
