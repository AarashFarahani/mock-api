package org.apache.mocklib.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldSchema {
    private String name;
    private Map<String, Object> schema;
}
