package com.clinicasorridente.pifinalbackend.entity;

import com.clinicasorridente.pifinalbackend.entity.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PACIENTE")
@SequenceGenerator(name = "paciente", sequenceName = "SQ_TB_PACIENTE", allocationSize = 1)
public class Paciente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "paciente_id")
    @GeneratedValue(generator = "paciente", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_nascimento")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dataNascimento = new Date(System.currentTimeMillis());

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", foreignKey = @ForeignKey(name = "fk_endereco"))
    private Endereco endereco;

//    @JsonManagedReference
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Consulta> consultas = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "prontuario_id", foreignKey = @ForeignKey(name = "fk_prontuario_paciente"))
    private Prontuario prontuario;

    @Enumerated(value = EnumType.STRING)
    private Sexo sexo;
}
