package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UniqueMockGenerator extends MockGenerator {
    public final static List<String> ACCEPTED_NAMES = List.of("UUID");

    protected UniqueMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return UUID.randomUUID().toString();
    }
}
