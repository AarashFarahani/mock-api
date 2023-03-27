package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class FloatMockGenerator extends MockGenerator {
    public final static List<String> ACCEPTED_NAMES = List.of("float", "Float");

    public FloatMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    private int min(Map<String, Object> args) {
        return (int) args.getOrDefault("min", -1000);
    }

    private int max(Map<String, Object> args) {
        return (int) args.getOrDefault("max", 1000);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = this.min(args);
        var max = this.max(args);
        var random = new Random();
        var result = min + random.nextFloat(max - min + 1);
        return result;
    }
}
