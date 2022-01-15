package br.com.inteligentclin.dtos.prontuarioDTO;

import br.com.inteligentclin.entity.File;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioModelDTO extends ProntuarioSummaryDTO implements Serializable {

    private static final long seriaVersionUID = 1L;

    private String planoTratamento;

    private String evolucaoTratamento;

    private File file;


}
