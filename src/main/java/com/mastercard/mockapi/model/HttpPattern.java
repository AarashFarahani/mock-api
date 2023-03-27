package com.mastercard.mockapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpPattern {
    private String path;
    private String method;
    private String requestType;
    private String responseType;
    private List<FieldSchema> fieldSchemas;
}
