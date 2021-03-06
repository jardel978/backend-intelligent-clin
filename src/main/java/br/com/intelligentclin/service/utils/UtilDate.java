package br.com.intelligentclin.service.utils;

import br.com.intelligentclin.entity.Idade;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Component
@NoArgsConstructor
public class UtilDate implements Serializable {

    private static final long serialVersionUID = 1L;

    public Idade gerarIdade(LocalDate dataInicial, LocalDate dataFinal) {

        Period period = Period.between(dataInicial, dataFinal);

        return Idade.builder()
                .dias(period.getDays())
                .meses(period.getMonths())
                .anos(period.getYears()).build();
    }

    public boolean verificarValidadeProntuario(LocalDate dataCriacao) {
        Period period = Period.between(dataCriacao, LocalDate.now());
        return period.getYears() >= 10;
    }

    public Boolean verificarSeDataAnterior(LocalDateTime dataTime) {
        return dataTime.isBefore(LocalDateTime.now());
    }

}
