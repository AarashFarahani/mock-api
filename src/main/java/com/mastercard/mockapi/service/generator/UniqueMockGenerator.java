package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;

import java.util.Map;
import java.util.UUID;

public class UniqueMockGenerator extends MockGenerator {
    public static final String NAME = "UUID";

    protected UniqueMockGenerator() {
        super(NAME);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return UUID.randomUUID().toString();
    }
}
