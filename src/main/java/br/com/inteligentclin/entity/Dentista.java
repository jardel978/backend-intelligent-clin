package com.clinicasorridente.pifinalbackend.entity;

import com.clinicasorridente.pifinalbackend.entity.enums.Especialidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
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

    @NotNull(message = "O número de matrícula do dentista é obrigatório.")
    private String matricula;

//    @JsonManagedReference
    @OneToMany(mappedBy = "dentista")
    @JsonIgnore
    private Set<Consulta> consultas = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Especialidade especialidade;

    @OneToMany(mappedBy = "dentista")
    @JsonIgnore
    private Set<Prontuario> prontuarios = new HashSet<>();

}
