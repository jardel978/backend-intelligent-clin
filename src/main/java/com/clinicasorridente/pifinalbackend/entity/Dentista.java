package com.clinicasorridente.pifinalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_DENTISTA")
@SequenceGenerator(name = "dentista", sequenceName = "SQ_TB_DENTISTA",allocationSize = 1)
public class Dentista implements Serializable {

    @Id
    @Column(name = "dentista_id")
    @GeneratedValue(generator = "dentista", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "O nome do dentista deve ser preenchido.")
    @Column(name = "dentista_nome")
    private String nome;

    @NotNull(message = "Por gentileza, informe o sobrenome do dentista.")
    @Column(name = "dentista_sobrenome")
    private String sobrenome;

    @NotNull(message = "O número de matrícula do dentista é obrigatório.")
    @Column(name = "dentista_matricula")
    private Integer matricula;

//    @JsonManagedReference
    @OneToMany(mappedBy = "dentista", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Consulta> consultas = new HashSet<>();

}
