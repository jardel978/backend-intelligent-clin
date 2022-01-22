package br.com.intelligentclin.dtos.prontuarioDTO;

import br.com.intelligentclin.entity.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioModelDTO extends ProntuarioSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String planoTratamento;

    private String evolucaoTratamento;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ultimaAlteracao;

    private File file;


}
