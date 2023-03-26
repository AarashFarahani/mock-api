package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;

import java.util.Map;
import java.util.Random;

public class IntegerMockGenerator extends MockGenerator {
    public final static String NAME = "int";

    public IntegerMockGenerator(String name) {
        super(NAME);
    }

    private int min(Map<String, Object> args) {
        return (int) args.getOrDefault("min", 10);
    }

    private int max(Map<String, Object> args) {
        return (int) args.getOrDefault("max", 10);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = this.min(args);
        var max = this.max(args);
        var random = new Random();
        var result = min + random.nextInt(max - min + 1);
        return result;
    }
}
