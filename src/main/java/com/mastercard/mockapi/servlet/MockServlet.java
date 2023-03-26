package com.mastercard.mockapi.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.mockapi.service.MonitoringService;
import com.mastercard.mockapi.service.RequestProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class MockServlet extends HttpServlet {
    private final RequestProcessor requestProcessor;
    private final MonitoringService monitoringService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(req.getRequestURI());

        var response = this.requestProcessor.process(req);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
// Get the printwriter object from response to write the required json object to the output stream
        var out = resp.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object

        var objectMapper= new ObjectMapper();
        var jsonString = objectMapper.writeValueAsString(response);

        out.print(jsonString);

        var sb = new StringBuilder();
        sb.append("Path: ").append(req.getRequestURI()).append("\n");
        sb.append("Method: ").append(req.getMethod()).append("\n");
        sb.append(this.getBody(req)).append("\n");
        sb.append(jsonString);
        this.monitoringService.handleMessage(sb.toString());

        out.flush();
    }

    private String getBody(HttpServletRequest req) throws IOException {
        var reader = req.getReader();
        var sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            reader.close();
            return sb.toString();
        }
    }
}
