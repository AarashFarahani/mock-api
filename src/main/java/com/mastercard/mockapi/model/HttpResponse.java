package com.mastercard.mockapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpResponse {
    private String path;
    private String method;
    private String requestBody;
    private String responseBody;
}
