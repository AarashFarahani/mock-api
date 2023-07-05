package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

@Component
public class StringMockGenerator  extends MockGenerator {
    private final static List<String> ACCEPTED_NAMES = List.of(String.class.getName(), CharSequence.class.getName());
    private static final String DEFAULT_FORMAT = "yyyy/MM/dd";

    private static final Map<String, Function<Map<String, Object>, Object>> functionsMap;

    static {
        var simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);

        functionsMap = new HashMap<>();
        functionsMap.put("number", a-> RandomStringUtils.randomAlphabetic(min(a), max(a)));
        functionsMap.put("date", a-> {
            var date = DateMockGenerator.randomDate(a);
            return simpleDateFormat.format(date);
        });
        functionsMap.put("boolean", a-> BooleanMockGenerator.randomBoolean());
        functionsMap.put("alphabetic", a-> randomAlphabetic(min(a), max(a)));
        functionsMap.put("lower", a-> randomAlphabetic(min(a), max(a)).toLowerCase());
        functionsMap.put("upper", a-> randomAlphabetic(min(a), max(a)).toUpperCase());
        functionsMap.put("alphanumeric", a-> randomAlphanumeric(min(a), max(a)));
        functionsMap.put("numeric", a-> randomNumeric(min(a), max(a)));
        functionsMap.put("uuid", a-> UUID.randomUUID().toString());
        functionsMap.put("any", a-> randomString(min(a), max(a)));
    }

    public StringMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    private static String type(Map<String, Object> args) {
        return args.getOrDefault("type", "any").toString();
    }

    private static int min(Map<String, Object> args) {
        return (int) args.getOrDefault("min", 10);
    }

    private static int max(Map<String, Object> args) {
        return (int) args.getOrDefault("max", 10);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var type = type(args);
        return functionsMap.getOrDefault(type, a-> randomString(min(a), max(a))).apply(args);
    }

    public static String randomString(int minLength, int maxLength) {
        return RandomStringUtils.randomAscii(randomMaxLength(minLength, maxLength));
    }

    public static String randomAlphabetic(int minLength, int maxLength) {
        return RandomStringUtils.randomAlphabetic(randomMaxLength(minLength, maxLength));
    }

    public static String randomAlphanumeric(int minLength, int maxLength) {
        return RandomStringUtils.randomAlphanumeric(randomMaxLength(minLength, maxLength));
    }

    public static String randomNumeric(int minLength, int maxLength) {
        return RandomStringUtils.randomNumeric(randomMaxLength(minLength, maxLength));
    }

    private static int randomMaxLength(int minLength, int maxLength) {
        return IntegerMockGenerator.randomInteger(minLength, maxLength);
    }
}
