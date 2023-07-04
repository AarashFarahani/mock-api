package org.apache.mocklib.service.generator;

import org.apache.mocklib.service.MockGenerator;

import java.util.List;
import java.util.Map;

public abstract class NumberGenerator<T> extends MockGenerator {
    protected NumberGenerator(List<String> acceptedNames) {
        super(acceptedNames);
    }

    protected Object min(Map<String, Object> args, T defaultValue) {
        return args.getOrDefault("min", defaultValue);
    }

    protected Object max(Map<String, Object> args, T defaultValue) {
        return args.getOrDefault("max", defaultValue);
    }
}
