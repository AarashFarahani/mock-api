package org.apache.mocklib.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LongMockGenerator extends NumberGenerator<Long> {
    private final static List<String> ACCEPTED_NAMES = List.of(long.class.getName(), Long.class.getName());

    public LongMockGenerator() {
        super(ACCEPTED_NAMES);
    }

    @Override
    public Object generate(Map<String, Object> args) {
        var min = (long) super.min(args, 0L);
        var max = (long) super.max(args, 10000000L);
        return min + super.random.nextLong(max - min + 1);
    }

    public static long randomLong(long min, long max) {
        return min + random.nextLong(max - min + 1);
    }
}
