package org.apache.mocklib.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class MockFactory {
    private static MockFactory instance;
    private static List<MockGenerator> mockGenerators;
    private static final String GENERATORS_PACKAGE = "org.apache.mocklib.service.generator";

    public static synchronized MockFactory getInstance() {
        if (instance == null) {
            var reflection = new Reflections(GENERATORS_PACKAGE);
            var generators = reflection.getSubTypesOf(MockGenerator.class);
            mockGenerators = new ArrayList<>();

            for (var generator : generators) {
                try {
                    if (!Modifier.isAbstract(generator.getModifiers())) {
                        mockGenerators.add(generator.getConstructor().newInstance());
                    }
                } catch (Exception exception) {
                    log.error("Create Instance for {} failed {}", generator.toString(), exception.getMessage());
                }
            }

            instance = new MockFactory();
        }

        return instance;
    }

    public Optional<MockGenerator> create(String name) {
        return this.mockGenerators.stream()
                .filter(a-> a.accept(name))
                .findFirst();
    }
}
