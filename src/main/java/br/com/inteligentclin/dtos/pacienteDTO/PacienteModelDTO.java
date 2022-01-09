package br.com.inteligentclin.dtos;

import br.com.inteligentclin.entity.Consulta;
import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.entity.Prontuario;
import br.com.inteligentclin.entity.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteModelDTO extends PessoaModelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate dataNascimento;

    private Endereco endereco;

    private Set<Consulta> consultas;

    private Prontuario prontuario;

    private Sexo sexo;


}
