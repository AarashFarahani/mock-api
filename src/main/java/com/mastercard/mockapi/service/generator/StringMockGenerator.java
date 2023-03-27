package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Map;

public class StringMockGenerator  extends MockGenerator {
    public final static List<String> ACCEPTED_NAMES = List.of("String");

    protected StringMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    private boolean isUpper(Map<String, Object> args) {
        return (boolean) args.getOrDefault("upper", false);
    }

    private boolean isLower(Map<String, Object> args) {
        return (boolean) args.getOrDefault("lower", false);
    }

    private int min(Map<String, Object> args) {
        return (int) args.getOrDefault("min", 10);
    }

    private int max(Map<String, Object> args) {
        return (int) args.getOrDefault("max", 10);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var result = RandomStringUtils.randomAlphabetic(this.min(args), this.max(args));
        if (this.isLower(args)) {
            result = result.toLowerCase();
        } else if (this.isUpper(args)) {
            result = result.toUpperCase();
        }
        return result;
    }
}
