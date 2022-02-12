package br.com.intelligentclin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static br.com.intelligentclin.security.JWTFilterAutenticacao.APLICATION_JSON_VALUE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("error", e.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> data = new HashMap<>();
        data.put("exception", e.getMessage());
        if (e.getMessage().contains("Token has expired"))
            data.put("message", "token expirado");
        else
            data.put("message", "acesso negado");
        data.put("timestamp", new Date());
        response.setContentType(APLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}