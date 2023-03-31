package com.mastercard.mockapi.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ByteMockGenerator extends NumberGenerator<Byte> {
    private final static List<String> ACCEPTED_NAMES = List.of(byte.class.getName(), Byte.class.getName());

    public ByteMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = (byte) super.min(args, Byte.MIN_VALUE);
        var max = (byte) super.max(args, Byte.MAX_VALUE);
        return min + super.random.nextInt(max - min + 1);
    }
}
