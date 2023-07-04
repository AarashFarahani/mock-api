package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CharMockGenerator extends MockGenerator {
    private final static List<String> ACCEPTED_NAMES = List.of(char.class.getName(), Character.class.getName());

    public CharMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return RandomStringUtils.randomAlphabetic(1).charAt(0);
    }
}
