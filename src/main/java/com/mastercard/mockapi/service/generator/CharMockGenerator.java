package com.mastercard.mockapi.service.generator;

import com.mastercard.mockapi.service.MockGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Map;

public class CharMockGenerator extends MockGenerator {
    public final static List<String> ACCEPTED_NAMES = List.of("char", "Character");

    public CharMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return RandomStringUtils.randomAlphabetic(1).charAt(0);
    }
}
