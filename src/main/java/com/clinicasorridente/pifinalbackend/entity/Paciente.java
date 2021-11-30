package com.clinicasorridente.pifinalbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.validation.constraints.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PACIENTE")
@SequenceGenerator(name = "paciente", sequenceName = "SQ_TB_PACIENTE", allocationSize = 1)
public class Paciente implements Serializable {

    @Id
    @Column(name = "paciente_id")
    @GeneratedValue(generator = "paciente", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "paciente_nome")
    @NotNull(message = "O nome do paciente deve ser preenchido.")
    private String nome;

    @Column(name = "paciente_sobrenome")
    @NotNull(message = "Por gentileza, insira o sobrenome do paciente.")
    private String sobrenome;

    @Column(name = "paciente_cpf")
    @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", message = "Informe um CPF no formato: 000.000.000-00")
    @NotNull(message = "Informe o CPF do paciente.")
    private String cpf;

    @Column(name = "paciente_data_cadastro")
    @JsonFormat(pattern = "dd-MM-yyyy@HH:mm:ss:SSSZ")
    private Date dataCadastro = new Date(System.currentTimeMillis());

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", foreignKey = @ForeignKey(name = "fk_endereco"))
    private Endereco endereco;

//    @JsonManagedReference
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Consulta> consultas = new HashSet<>();
}
