package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class LocalDateMockGenerator extends MockGenerator {
    private static final List<String> ACCEPTED_NAMES = List.of(LocalDate.class.getName());
    private static final String DEFAULT_FORMAT = "yyyy/MM/dd";

    public LocalDateMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    private static DateTimeFormatter dateFormat(Map<String, Object> args) {
        var format = args.getOrDefault("format", DEFAULT_FORMAT).toString();
        return DateTimeFormatter.ofPattern(format);
    }

    private static LocalDate from(Map<String, Object> args) {
        var from = (String) args.get("from");
        return StringUtils.isBlank(from)
                ? LocalDate.now().plus(-10, ChronoUnit.DAYS)
                : LocalDate.parse(from, dateFormat(args));
    }

    private static LocalDate to(Map<String, Object> args) {
        var from = (String) args.get("to");
        return StringUtils.isBlank(from)
                ? LocalDate.now().plus(10, ChronoUnit.DAYS)
                : LocalDate.parse(from, dateFormat(args));
    }

    @Override
    public Object generate(Map<String, Object> args) {
        return randomDate(args);
    }

    public static LocalDate randomDate(Map<String, Object> args) {
        return LocalDateTimeGenerator
                .randomDateTime(from(args).atTime(LocalTime.NOON), to(args).atTime(LocalTime.NOON))
                .toLocalDate();
    }
}
