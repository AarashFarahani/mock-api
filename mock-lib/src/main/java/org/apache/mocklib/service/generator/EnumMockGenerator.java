package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class EnumMockGenerator extends MockGenerator {
    public final static String NAME = "enum";
    private final static List<String> ACCEPTED_NAMES = List.of(NAME);

    public EnumMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    private Class getEnumType(Map<String, Object> args) {
        return (Class) args.get("type");
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var random = new Random();
        var enumType = this.getEnumType(args);
        var enumValues = enumType.getEnumConstants();
        return enumValues[random.nextInt(enumValues.length)];
    }
}