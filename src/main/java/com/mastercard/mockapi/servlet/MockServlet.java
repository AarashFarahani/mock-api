package com.mastercard.mockapi.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Just for test");
        log.info(req.getServletPath());

        var response = this.requestProcessor.process(req);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
// Get the printwriter object from response to write the required json object to the output stream
        var out = resp.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object

        var objectMapper= new ObjectMapper();
        var jsonString = objectMapper.writeValueAsString(response);
        out.print(jsonString);
        out.flush();
    }
}
