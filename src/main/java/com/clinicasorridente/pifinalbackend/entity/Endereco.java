package com.clinicasorridente.pifinalbackend.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ENDERECO")
@SequenceGenerator(name = "endereco", sequenceName = "SQ_TB_ENDERECO", allocationSize = 1)
public class Endereco implements Serializable {

    @Id
    @Column(name = "endereco_id")
    @GeneratedValue(generator = "endereco", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "endereco_rua")
    private String rua;

    @Column(name = "endereco_numero")
    private String numero;

    @Column(name = "endereco_cidade")
    private String cidade;

    @Column(name = "endereco_estado")
    private String estado;

    @OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

}
