package com.mastercard.mockapi.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.mockapi.model.HttpResponse;
import com.mastercard.mockapi.service.ObjectGenerator;
import com.mastercard.mockapi.utils.ConsoleUtils;
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
    private final ObjectGenerator objectGenerator;
    private final ConsoleUtils consoleUtils;
    private final ObjectMapper objectMapper;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var path = req.getRequestURI().replace("/mock", "");
        var method = req.getMethod();
        Object response = null;
        try {
            response = this.objectGenerator.generate(path, method);
        } catch (Exception e) {
            log.error(e.getMessage());
            response = "Error";
//            resp.setStatus();
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
// Get the printwriter object from response to write the required json object to the output stream
        var out = resp.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object

        var jsonString = this.objectMapper.writeValueAsString(response);

        out.print(jsonString);

        var httpResponse = new HttpResponse(path, method, this.getBody(req), jsonString);
        jsonString = this.objectMapper.writeValueAsString(httpResponse);
        this.consoleUtils.handleMessage(jsonString);

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
