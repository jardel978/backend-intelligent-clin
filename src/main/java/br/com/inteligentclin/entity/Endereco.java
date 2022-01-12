package br.com.inteligentclin.entity;

import br.com.inteligentclin.entity.enums.Especialidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ENDERECO")
@SequenceGenerator(name = "endereco", sequenceName = "SQ_TB_ENDERECO", allocationSize = 1)
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "endereco_id")
    @GeneratedValue(generator = "endereco", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String rua;

    private String numero;

    private String bairro;

    private String cep;

    private String cidade;

    private String estado;

    private String complemento;

    @OneToMany(mappedBy = "endereco")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

}
