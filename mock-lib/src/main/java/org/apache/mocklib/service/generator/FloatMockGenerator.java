package org.apache.mocklib.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FloatMockGenerator extends NumberGenerator<Float> {
    private final static List<String> ACCEPTED_NAMES = List.of(float.class.getName(), Float.class.getName());

    public FloatMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = Float.valueOf(super.min(args, Float.MIN_VALUE).toString());
        var max = Float.valueOf(super.max(args, Float.MAX_VALUE).toString());
        return min + super.random.nextFloat(max - min + 1);
    }
}
