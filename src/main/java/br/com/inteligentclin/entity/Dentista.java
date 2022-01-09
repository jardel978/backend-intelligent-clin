package br.com.inteligentclin.entity;

import br.com.inteligentclin.entity.enums.Especialidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_DENTISTA")
@SequenceGenerator(name = "dentista", sequenceName = "SQ_TB_DENTISTA", allocationSize = 1)
public class Dentista extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dentista_id")
    @GeneratedValue(generator = "dentista", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String matricula;

//    @JsonManagedReference
//    @JsonIgnore
    @OneToMany(mappedBy = "dentista")
    private Set<Consulta> consultas = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Especialidade.class)
    @Column(name = "dentista_especialidades")
    @JoinTable(name = "dentista_especialidades")
    @Enumerated(value = EnumType.STRING)
    private List<Especialidade> especialidades;
//    private Especialidade especialidades;

//    @JsonIgnore
    @OneToMany(mappedBy = "dentista")
    private Set<Prontuario> prontuarios = new HashSet<>();

}
