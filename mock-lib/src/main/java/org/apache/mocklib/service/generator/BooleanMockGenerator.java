package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BooleanMockGenerator extends MockGenerator {
    private final static List<String> ACCEPTED_NAMES = List.of(boolean.class.getName(), Boolean.class.getName());

    public BooleanMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return randomBoolean();
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }
}
