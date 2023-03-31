package com.mastercard.mockapi.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ShortMockGenerator extends NumberGenerator<Short> {
    private final static List<String> ACCEPTED_NAMES = List.of(short.class.getName(), Short.class.getName());

    public ShortMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = (short) super.min(args, Short.MIN_VALUE);
        var max = (short) super.max(args, Short.MAX_VALUE);
        return min + super.random.nextInt(max - min + 1);
    }
}