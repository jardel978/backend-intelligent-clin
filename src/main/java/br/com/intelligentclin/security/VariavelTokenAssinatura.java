package br.com.intelligentclin.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class VariavelTokenAssinatura {

    @Value("${app.token.assinatura}")
    private String tokenAssinatura;

    public String getTokenAssinatura() {
        return tokenAssinatura;
    }
}
