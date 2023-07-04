package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateMockGenerator extends MockGenerator {
    private static final List<String> ACCEPTED_NAMES = List.of(Date.class.getName());

    public DateMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return randomDate(args);
    }

    public static Date randomDate(Map<String, Object> args) {
        var localDate = LocalDateMockGenerator.randomDate(args);
        return localDate != null
                ? Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC))
                : null;
    }
}
