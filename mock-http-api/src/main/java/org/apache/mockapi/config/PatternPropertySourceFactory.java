package org.apache.mockapi.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class PatternPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
            throws IOException {
        var factory = new YamlPropertiesFactoryBean();
        var uri = encodedResource.getResource().getURI();
        var files = new File(uri).listFiles();

        var resources = new FileUrlResource[files.length];
        for (int i = 0; i < files.length; i++) {
            resources[i] = new FileUrlResource(files[i].getPath());
        }
        factory.setResources(resources);
//        properties.putAll(factory.getObject());
//        var properties = new Properties();
//        for (var file : files) {
//            factory.setResources(new FileUrlResource(file.toURI().toURL()));
//            properties.putAll(factory.getObject());
//        }
//        factory.setResources(files);
//        Arrays.stream(files).forEach(a-> factory.setResources(new FileUrlResource(a.toURI().toURL())));
//        while (resources.hasMoreElements()) {
//            var resource = resources.nextElement();
////            var properties = new Properties();
////            properties.load(resource.openStream());
//            factory.setResources(new FileUrlResource(resource));
////            factory.setResources(encodedResource.getResource());
//
//        }
        var properties = factory.getObject();

        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}
