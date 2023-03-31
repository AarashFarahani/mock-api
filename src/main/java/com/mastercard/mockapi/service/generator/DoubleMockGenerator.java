package com.mastercard.mockapi.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DoubleMockGenerator extends NumberGenerator<Double> {
    private final static List<String> ACCEPTED_NAMES = List.of(double.class.getName(), Double.class.getName());

    public DoubleMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = Double.valueOf(super.min(args, Double.MIN_VALUE).toString());
        var max = Double.valueOf(super.max(args, Double.MAX_VALUE).toString());
        return min + super.random.nextDouble(max - min + 1);
    }
}
