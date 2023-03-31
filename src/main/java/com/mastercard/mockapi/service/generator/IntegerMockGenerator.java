package com.mastercard.mockapi.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IntegerMockGenerator extends NumberGenerator<Integer> {
    private final static List<String> ACCEPTED_NAMES = List.of(int.class.getName(), Integer.class.getName());

    public IntegerMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = (int) super.min(args, 0);
        var max = (int) super.max(args, 10);
        return min + super.random.nextInt(max - min + 1);
    }
}
