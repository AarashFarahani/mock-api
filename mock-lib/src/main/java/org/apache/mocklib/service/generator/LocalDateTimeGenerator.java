package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class LocalDateTimeGenerator extends MockGenerator {
    private static final List<String> ACCEPTED_NAMES = List.of(LocalDateTime.class.getName());
    private static final String DEFAULT_FORMAT = "yyyy/mm/dd HH:mm:ss";
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public LocalDateTimeGenerator() {
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
        return null;
    }

    public static LocalDateTime randomDateTime(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        var from = fromLocalDateTime.toInstant(ZONE_OFFSET).toEpochMilli();
        var to = toLocalDateTime.toInstant(ZONE_OFFSET).toEpochMilli();

        long randomMillisSinceEpoch = LongMockGenerator.randomLong(from, to);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(randomMillisSinceEpoch), ZONE_OFFSET);
    }
}
