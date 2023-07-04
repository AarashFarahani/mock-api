package org.apache.mocklib;

import org.apache.mocklib.service.MockGenerator;
import org.reflections.Reflections;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        var reflections = new Reflections("com.mastercard.mocklib.service.generator");
        var list = List.of();
        var mockGenerators = reflections.getSubTypesOf(MockGenerator.class);
        for (var mockGenerator : mockGenerators) {
            var generator = mockGenerator.getConstructor().newInstance();
            list.add(generator);
        }

        System.out.printf("Hi");
    }
}
